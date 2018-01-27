package com.example.you.lsmisclient.fragment.photopicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.ThumbnailUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import com.example.you.lsmisclient.R;

import java.io.File;

/**
 * Created by chendian on 2018/1/25.
 */
public class PicItemView extends android.support.v7.widget.AppCompatImageView {

    //右上角叉的宽度
    private static final int CANCEL_ICON_WIDHT = 50;
    private int _WIDTH ;
    private int _HEIGHT;
    private final static String TAG = "PicItemView";
    private PhotoPickerFragment father;

    public String getmPicFilepath() {
        return mPicFilepath;
    }

    private String mPicFilepath;
    private int pos = -1;

    public void setFather(PhotoPickerFragment father) {
        this.father = father;
    }

    public PicItemView(Context context, String picFilePath, int width, int height) {
        super(context);
        Log.d(TAG, "PicItemView: ctx");
        _WIDTH = width; _HEIGHT = height;

        setmPicFilepath(picFilePath);
        init();
    }


    public PicItemView(Context ctx, AttributeSet set){
        super(ctx,set);
        Log.d(TAG, "PicItemView: ctx,set");
    }

    public void setmPicFilepath(String mPicFilepath) {
        this.mPicFilepath = mPicFilepath;
    }

    //初始化图片
    private void init(){
//        setMaxHeight(MAX_WIDTH);
//        setMaxWidth(MAX_WIDTH);

        if(mPicFilepath == null){
            setImgInvalid();
            return;
        }
        File picFile = new File(mPicFilepath);
        if(!picFile.exists()){
            setImgInvalid();
            return;
        }else {
            setImageBitmap(getTumbaImg());
        }
    }

    private Bitmap getTumbaImg(){
        Bitmap rawPic = BitmapFactory.decodeFile(mPicFilepath);
        Bitmap thumbPic =  ThumbnailUtils.extractThumbnail(rawPic,_WIDTH,_HEIGHT);
        Bitmap cancelMark = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(getResources(), R.drawable.ico_cancel),
                CANCEL_ICON_WIDHT,CANCEL_ICON_WIDHT);
        Canvas canvas = new Canvas(thumbPic);
        canvas.drawBitmap(cancelMark,_HEIGHT - CANCEL_ICON_WIDHT ,0,null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        return thumbPic;
    }

    private void setImgInvalid(){
        Log.d(TAG, "setImgInvalid: picpath is invalid or cant open pic file ");
        setImageResource(R.drawable.openfile_errpr);
    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
//        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
//        _WIDTH = getMeasuredWidth();
//        _HEIGHT = getMeasuredHeight();
//        init();
//    }
//
    /**
     * 重写TouchEvent,删除图片
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){

        float x = event.getX();
        float y = event.getY();
        Log.d(TAG, "onTouchEvent: x = " + x + "||  y = " + y);
        if(y<CANCEL_ICON_WIDHT * 2 && _WIDTH - x <CANCEL_ICON_WIDHT * 2){
            father.deletePicks(this);
        }
        return true;
    }

}

