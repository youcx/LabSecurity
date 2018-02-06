package com.example.you.lsmisclient.contacts;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.UserInfoBean;
import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 安全人员界面
 * Created by chendian on 2018/2/3.
 */


interface dataHandler{
    public void resolveJson(JsonObject job);
}
public class UserActivity extends AppCompatActivity implements  dataHandler{
    private static final String TAG = "UserActivity";

    private static final String FLAG_ROLE = "role";
    private static final String FLAG_DEPARTMENT = "department";
    private static final String FLAG_USERINFO = "userinfo"; //第一页
    private static final String FLAG_PAGE_USERINFO = "infoPageData"; //翻页
    private static final String FIELD_MSGTYPE = "msgType";
    private static final String FILED_DEPARTMENT_NAME = "departmentName";
    private static final String FILED_DEPARTMENT_ID = "departmentId";
    private static final String FIELD_ROLEID = "roleId";
    private static final String FIELD_ROLENAME = "stationTitle";

    private static final String PARAM_ROLEID = "roleId";
    private static final String PARAM_DEPARTMENTID = "departmentId";
    private static final String PARAM_PAGE = "pageNumb";

    private String URL_DEPARTMENT_REQUEST = null;
    private String URL_ROLE_REQUEST = null;
    private String URL_USERINFO_PREFIX = null;
    private SearchView searchView = null;
    private Spinner collegeSpinner = null;
    private Spinner roleSpinner = null;
    private Map<String,Integer> departmentMap = null;
    private Map<String,Integer> roleMap = null;

    private LinearLayout userlay = null;

    //TODO 初始化控件数据时会触发，是否添加FLAG
    private boolean collegeFlag = false; //第一次初始化
    private boolean roleFlag = false;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_user);
        URL_DEPARTMENT_REQUEST =  getResources().getString(R.string.user_department_req);
        URL_ROLE_REQUEST = getResources().getString(R.string.user_role_req);
        URL_USERINFO_PREFIX = getResources().getString(R.string.user_info_req);
        initViews();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: submit");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.d(TAG, "onQueryTextChange: changed");
                return false;
            }
        });

    }
    //初始化控件
    private void initViews(){
        searchView = (SearchView) findViewById(R.id.user_search_box);
        searchView.setIconified(false);
        searchView.setQueryHint("搜索姓名");
        collegeSpinner = (Spinner) findViewById(R.id.collegeSpinner);
        collegeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!collegeFlag){
                    collegeFlag = true;
                    return;
                }
                requestUserData();
                Log.d(TAG, "onItemSelected: " + ((TextView)view).getText());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        roleSpinner = (Spinner) findViewById(R.id.roleSpinner);
        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!roleFlag) {
                    roleFlag = true;
                    return;
                }
                Log.d(TAG, "onItemSelected: " + ((TextView)view).getText());
                requestUserData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //初始化学院下拉菜单
        Map<String,String> requestDepartmentMap = new HashMap<>();
        requestDepartmentMap.put("url", URL_DEPARTMENT_REQUEST);
        Map<String,String> msgFlagMap = new HashMap<>();
        msgFlagMap.put(FIELD_MSGTYPE,FLAG_DEPARTMENT);
        new AsynRequest().execute(requestDepartmentMap,msgFlagMap);

        //初始化角色下拉菜单
        Map<String,String> requestRoleMap = new HashMap<>();
        requestRoleMap.put("url", URL_ROLE_REQUEST);
        Map<String,String> rmsgFlagMap = new HashMap<>();
        rmsgFlagMap.put(FIELD_MSGTYPE,FLAG_ROLE);
        new AsynRequest().execute(requestRoleMap,rmsgFlagMap);

        userlay = (LinearLayout) findViewById(R.id.userRootLay);
    }



    /** 处理获取到的数据*/
    @Override
    public void resolveJson(JsonObject job) {
        if(job == null){
            Log.w(TAG, "resolveJson: resolve json is null");
            return;
        }

        String msgType = job.get(FIELD_MSGTYPE).getAsString();
        if(msgType == null) {
            Log.w(TAG, "resolveJson: json type is null" );
            return;
        }
        Log.d(TAG, "resolveJson: handler json msg type ---> " + msgType);
       if(FLAG_DEPARTMENT.equals(msgType)) {  //学院
            JsonArray departmentArray = (JsonArray)job.get("data");
            updateDepartmentSpinner(departmentArray);
       }else if(msgType.equals(FLAG_ROLE)){ //职位
            JsonArray roleArray = job.get("data").getAsJsonArray();
            updateRoleSpinner(roleArray);
       }else if(msgType.equals(FLAG_USERINFO)){ //用户信息
            JsonArray userArray = job.getAsJsonArray("data");
            updateUserView(userArray);
       }else {
           Log.d(TAG, "resolveJson: unknown json msg" + msgType);
           return;
       }
    }

    //跟新学院下拉列表
    private void updateDepartmentSpinner(JsonArray departmentArray){
        List<String> dataArray = new ArrayList<>();
        departmentMap = new HashMap<>();
        for (JsonElement element: departmentArray) {
            if (element instanceof  JsonObject){
                String departmentName = ((JsonObject) element).get(FILED_DEPARTMENT_NAME).getAsString();
                Integer departmentID = ((JsonObject) element).get(FILED_DEPARTMENT_ID).getAsInt();
                if(!departmentName.equals("")){
                    dataArray.add(departmentName);
                    departmentMap.put(departmentName,departmentID);
                }else Log.w(TAG, "updateDepartmentSpinner: department name is missing");
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.user_spinner_item,dataArray);
        adapter.setDropDownViewResource(R.layout.user_spinner_item);
        collegeSpinner.setAdapter(adapter);

    }

    //跟新职位下拉列表
    private void updateRoleSpinner(JsonArray roleArray){
        List<String> items = new ArrayList<>();
        roleMap = new HashMap<>();
        for (JsonElement element : roleArray){
            if(element instanceof JsonObject){
                String roleName = ((JsonObject) element).get(FIELD_ROLENAME).getAsString();
                int roleId = ((JsonObject) element).get(FIELD_ROLEID).getAsInt();
                if(!roleName.equals("")){
                    items.add(roleName);
                    roleMap.put(roleName,roleId);
                }
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.user_spinner_item,items);
        adapter.setDropDownViewResource(R.layout.user_spinner_item);
        roleSpinner.setAdapter(adapter);
    }

    //刷新用户信息列表
    private void updateUserView(JsonArray userArray){
        userlay.removeAllViews();
        for (JsonElement element: userArray) {
            String jstr = element.toString();
            UserInfoBean bean = new Gson().fromJson(jstr,UserInfoBean.class);
            UserInfoView uv = new UserInfoView(this);
            uv.setmUserInfo(bean);
            userlay.addView(uv);
        }
    }

    //请求用户数据
    private void requestUserData(){
        String department = collegeSpinner.getSelectedItem().toString();
        String role = roleSpinner.getSelectedItem().toString();
        String roleId = roleMap.get(role).toString();
        String departmentId = departmentMap.get(department).toString();
        Map<String,String> reqMap = new HashMap<>();
        reqMap.put("url", URL_USERINFO_PREFIX);
        reqMap.put(PARAM_ROLEID,roleId);
        reqMap.put(PARAM_DEPARTMENTID,departmentId);
        reqMap.put(PARAM_PAGE,"0");
        //TODO 请求添加姓名参数
        Map<String,String> typeMap = new HashMap<>();
        typeMap.put(FIELD_MSGTYPE,FLAG_USERINFO);
        new AsynRequest().execute(reqMap,typeMap);

    }

    private class AsynRequest extends AsyncTask<Map<String, String>, Void, JsonObject> {
        @Override
        protected JsonObject doInBackground(Map<String, String>... maps) {
            Log.d(TAG, "doInBackground: asynTask maps szie ---> " + maps.length );
            if (maps.length < 2) return null;
            String url = null;
            String prefix = "";
            for (Map.Entry<String,String> entry: maps[0].entrySet()) {
                if(entry.getKey().equals("url")) url = entry.getValue();
                else prefix += entry.getKey() + "=" + entry.getValue() + "&";
            }
            url += prefix;
            Log.d(TAG, "doInBackground: request data url ---> " + url);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            try{
                Response response = client.newCall(request).execute();
                if(response.isSuccessful()){
//                    Log.d(TAG, "doInBackground: " + response.body().string());
                    JsonObject resultJob = (JsonObject) new JsonParser().parse(response.body().string());
                    resultJob.addProperty(FIELD_MSGTYPE,maps[1].get(FIELD_MSGTYPE));
                    return resultJob;
                }
            }catch (Exception e ){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JsonObject job){
            if(job != null){
                resolveJson(job);
            }else
                Log.w(TAG, "onPostExecute: response json object is null");
        }
    }
}
