package com.example.you.lsmisclient.contacts;

import android.content.Context;
import android.graphics.*;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;

import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.bean.UserInfoBean;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chendian on 2018/1/28.
 * 显示用户头像 ，与性别图标
 */
public class AvatarView extends android.support.v7.widget.AppCompatImageView {

    private final static int MIN_HEIGHT = 250; //头像宽度
    private final static int MIN_SEX_ICOHEIGHT = 75; //性别图标宽度
    private final String TAG = "UserAvatarView";


    private Bitmap mAvatarbmp;


    public AvatarView(Context context) {
        super(context);


    }

    //XML 加载需要
    public AvatarView(Context ctx, AttributeSet set) {
        super(ctx, set);
        setMinimumHeight(MIN_HEIGHT);
        setMinimumWidth(MIN_HEIGHT + MIN_SEX_ICOHEIGHT);
    }

    //初始化界面
    public void updateAvatarView(UserInfoBean bean) {
        if (bean.getPhoto() != null) {
            //异步获取网络图片
            new AsynDownloadAvatar().execute(bean.getPhoto());

        } else {
            setImageBitmap(genErrorAvatarbmp());
        }

    }


    //当获取不到网络头像，生成error头像
    private Bitmap genErrorAvatarbmp() {
        Bitmap rawbmp = BitmapFactory.decodeResource(getResources(), R.drawable.openfile_errpr);
        Bitmap boundAvatar = ThumbnailUtils.extractThumbnail(rawbmp, MIN_HEIGHT, MIN_HEIGHT);
        return getCircularBitmap(boundAvatar);
    }

    //获取圆形bmp
    private Bitmap getCircularBitmap(Bitmap raw) {
        int height = raw.getHeight();
        int width = raw.getWidth();
        if (height < width) {
            raw = ThumbnailUtils.extractThumbnail(raw, height, height);
        } else {
            raw = ThumbnailUtils.extractThumbnail(raw, width, width);
        }
        height = raw.getHeight();
        Bitmap output = Bitmap.createBitmap(height, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, height, height);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(height / 2, height / 2,
                height / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(raw, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }


    //异步下载头像
    private class AsynDownloadAvatar extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String imageURL = strings[0];
            Bitmap bitmap = null;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(imageURL)
                    .header("keep-alive","true")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
//                    byte[] imgdata = response.body().bytes();
                    bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "run: download internet data error " + e.getMessage());
            }
            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bmp) {
            //+++++++
            if (bmp != null) {
                mAvatarbmp = getCircularBitmap(bmp);
                setImageBitmap(mAvatarbmp);
            } else {
                setImageBitmap(genErrorAvatarbmp());
            }
        }
    }
}

