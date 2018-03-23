package com.example.you.lsmisclient.fragment.photopicker;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.you.lsmisclient.R;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import static android.app.Activity.RESULT_OK;

/**
 * Created by chendian on 2018/1/24.
 */
public class PhotoPickerFragment extends Fragment {
    private final static int MAX_PHOTO_VIEW = 3;
    private final String APP_PATH = "/labSafety/";

    private static final int REQUEST_PERMISSION_CODE = 1;

    private static  String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private final static int INDEX_TAKE_PICTURE = 0x00;
    private final static int INDEX_CAPTURE_VIDEO = 0x01;
    private final static int INDEX_OPEN_ALBUM = 0x02;
    private String[] mItems = {"拍照", "录像","从相册选择"};
    private AlertDialog menuDlg = null;
    private AlertDialog repeteAddpicDlg = null;
    private AlertDialog deleteConfirmDlg = null;

    private String TAG = "PhotoPicker";
    private int option = INDEX_TAKE_PICTURE;
    private String mCurrentPhotoPath;
    private PicItemView deleteTempView = null;

    //Scroll View
    private View rootView = null;

    //选中的图片路径
    private ArrayList<String> mPicsPath ;
    private LinearLayout picLay;
    private int addpicBtnWidth;
    private int addpicBtnHeight;

//    @BindView(R.id.addPictureBtn)
    ImageButton mImageButtion;



    private boolean isPermissionRequested = false;
    public ArrayList<String> getPickedPicsPath() {
        return mPicsPath;
    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {
        super.onCreateView(inflater, container, saveInstanceState);

        View view = inflater.inflate(R.layout.activity_photopicker, container, false);

//        ButterKnife.bind(this, view);
        mImageButtion =  (ImageButton) view.findViewById(R.id.addPictureBtn);
        mImageButtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addpicBtnHeight ==0 ||addpicBtnWidth == 0){
                    addpicBtnHeight = mImageButtion.getHeight();
                    addpicBtnWidth = mImageButtion.getWidth();
                }
                menuDlg.show();
            }
        });

        //初始化点击添加照片弹出菜单栏
        menuDlg = new AlertDialog.Builder(getActivity())
                .setSingleChoiceItems(mItems, INDEX_TAKE_PICTURE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "choose menu clicked " + mItems[which]);
                        option = which;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //下一步操作
                        Log.d(TAG, "onClick: next step ,open abulm or take picture");
                        getPicture(option);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        //初始化重复添加图片提示框
        repeteAddpicDlg = new AlertDialog.Builder(getActivity())
                .setTitle("警告!")
                .setPositiveButton("我按错了~", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: catch user add same picture,interrupted! ");
                        return ;
                    }
                })
                .setMessage("请勿重复添加同一张图片").create();

        //初始化删除提示框
        deleteConfirmDlg = new AlertDialog.Builder(getActivity()).setTitle("是否删除选中图片？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (deleteTempView == null){
                            Log.e(TAG, "onClick: delete tempview is null");
                            return;
                        }
                        String picPath = deleteTempView.getmPicFilepath();
                        if(mPicsPath.contains(picPath)){
                            mPicsPath.remove(picPath);
                            picLay.removeView(deleteTempView);
                            deleteTempView = null;
                        }else {
                            Log.e(TAG, "onClick: pics arrays dont contains tempview's path");
                            return;
                        }
                    }
                })
                .setNegativeButton("让我再看看", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return ;
                    }
                }).create();

        mPicsPath = new ArrayList<String>();
        rootView = view;
        picLay = (LinearLayout) ((ViewGroup) rootView).getChildAt(0);
        checkPermissions();
        return view;
    }

    /**
     * 首先判断权限，有权限再下一步操作
     * 打开下拉框，选择拍照或者相册
     * 将缩略图显示到界面
     */
    private void getPicture(int which) {
        if(!isPermissionRequested) {
            Toast.makeText(getActivity(),"未获取到拍照权限，请在应用设置中添加 [拍照]、[读写外部文件] 权限",Toast.LENGTH_LONG).show();
            return;
        }
        Uri content = null;
        switch (which) {
            //选图片
            case INDEX_OPEN_ALBUM: {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, content);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(albumIntent, INDEX_OPEN_ALBUM);
                break;
            }
            //拍照
            case INDEX_TAKE_PICTURE: {
                takePicture();
                break;
            }
            //录像
            case INDEX_CAPTURE_VIDEO :{
                Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(videoIntent,INDEX_CAPTURE_VIDEO);
            }
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult (int requestCode ,int responseCode, Intent intent ) {
        super.onActivityResult(requestCode,responseCode,intent);
        switch (requestCode){
            //获取图片
            case INDEX_OPEN_ALBUM:{
                if(responseCode == RESULT_OK) {
                    mCurrentPhotoPath = MediaUtil.getRealPathFromURI(getActivity(),intent.getData());
                    Log.d(TAG, "onActivityResult: get pic from album ----> " + mCurrentPhotoPath);
                    updatePicks();
                }
                break;
            }
            //拍照
            case INDEX_TAKE_PICTURE:{
                if(responseCode == RESULT_OK){
                    Log.d(TAG, "onActivityResult: get pic from camera ----> " + mCurrentPhotoPath);
                    new MediaUtil().compressPhoto(getContext(),mCurrentPhotoPath);
                    updatePicks();
                }
                break;
            }
            case INDEX_CAPTURE_VIDEO:{
                if(responseCode == RESULT_OK) {
                    mCurrentPhotoPath = MediaUtil.getRealPathFromURI(getActivity(), intent.getData());
                    Log.d(TAG, "onActivityResult: capture video path " + mCurrentPhotoPath);
                    updatePicks();
                }
                break;
            }
        }
    }

    /**
     *  生成图片文件
     * 跟新mCurrentPhotoPath
     * **/
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" ;
        File storageDir =new File( Environment.getExternalStorageDirectory() + APP_PATH);
        if(!storageDir.exists())
            storageDir.mkdirs();
//        File storageDir = new File("/sdcard/");
        //getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //调用拍照
    private void takePicture()  {
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        File photoFile = null;
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoUri = Uri.fromFile(photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, INDEX_TAKE_PICTURE);
            }
        }
    }


    /**
     * 添加照片是 更新界面与图片位置信息
     * 判断图片是否存在
     */
    private void updatePicks(){
        PicItemView tempView  = new PicItemView(getActivity(),mCurrentPhotoPath,addpicBtnWidth,addpicBtnHeight);
        tempView.setFather(this);
        if(mPicsPath.contains(mCurrentPhotoPath)){
            repeteAddpicDlg.show();
            return ;
        }
        mPicsPath.add(mCurrentPhotoPath);
        picLay.addView(tempView);
    }


    public void deletePicks(PicItemView view){
        deleteTempView = view;
        deleteConfirmDlg.show();
    }



    //每次打开界面判断权限
    private void checkPermissions (){
        Log.d(TAG, "checkPermissions: current sdk version " + Build.VERSION.SDK_INT);
        int read_permission = ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE);
        int write_permission= ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int camera_permission = ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA);
        if(read_permission != PackageManager.PERMISSION_GRANTED
                || write_permission != PackageManager.PERMISSION_GRANTED
                || camera_permission != PackageManager.PERMISSION_GRANTED){
            Log.w(TAG, "checkPermissions: check permissions ---> read write denied! ");
            Toast.makeText(getActivity(),"需要获取拍照权限",Toast.LENGTH_LONG).show();
            //获取权限
            ActivityCompat.requestPermissions(getActivity(),PERMISSIONS,REQUEST_PERMISSION_CODE);
            return ;
        }
        Log.d(TAG, "checkPermissions: camera and externale file permission is granted! ");
        isPermissionRequested = true;
    }

    public void setPermissionRequested(boolean permissionRequested) {
        isPermissionRequested = permissionRequested;
    }

}


