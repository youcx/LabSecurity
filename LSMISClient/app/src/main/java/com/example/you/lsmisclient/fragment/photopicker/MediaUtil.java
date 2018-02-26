package com.example.you.lsmisclient.fragment.photopicker;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import java.io.File;
import java.io.InputStream;

/**
 * Created by chendian on 2018/1/24.
 */
public class MediaUtil {

    private static final String TAG = "MediaUtil";
    /**
     从Uri获取content的文件路径
     **/
    public static String getRealPathFromURI(Context ctx,Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(ctx, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    /**
     * 图片压缩
     */
    public void  compressPhoto(Context ctx, final String photoPath){
        Luban.with(ctx)
                .load(photoPath)
                .ignoreBy(100)
                .setTargetDir("/sdcard/labSafety")
                .setCompressListener(new CusCompressListenner(photoPath)).launch();
    }

    /**
     * photo compressor listenner
     */
    private class CusCompressListenner implements OnCompressListener {
        private Bitmap targetBmp= null;
        private String soucePath = null;
        CusCompressListenner(String photoPath){
            if(photoPath != null)
                soucePath = photoPath;
        }

        @Override
        public void onStart() {
            Log.d(TAG, "onStart: start compress photo, file path ---> " + soucePath);
        }

        @Override
        public void onSuccess(File file) {
            Log.d(TAG, "onSuccess: compress photo succ");
            targetBmp = BitmapFactory.decodeFile(file.getAbsolutePath());
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }
    }
}