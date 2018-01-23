package com.example.you.lsmisclient.qr.barcodeutil;

import android.content.Intent;
import android.util.Log;
import com.example.you.lsmisclient.qr.CaptureActivity;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

import static android.content.ContentValues.TAG;

/**
 * Created by chendian on 2018/1/21.
 */
interface CusCallback{
    void confirmBarcode(Barcode content);
}

public class BarcodeFactory implements MultiProcessor.Factory<Barcode> ,CusCallback {

    private int CONFIRM_SCAN_TIMES = 10;
    private String qr_content = "";
    private CaptureActivity activity = null;

    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        BarcodeTracker bt = new BarcodeTracker();
        bt.setFactory(this);
        return bt;
    }

    /**
     * 确认10次
     * @param content
     */
    @Override
    public void confirmBarcode(Barcode content) {
        String value =content.displayValue;
        if (qr_content.equals(""))
            qr_content = value;
        else {
            if (value.equals(qr_content))
                --CONFIRM_SCAN_TIMES;
            else {
                CONFIRM_SCAN_TIMES = 10;
                qr_content = value;
            }
        }
        if (CONFIRM_SCAN_TIMES <= 0) {
            CONFIRM_SCAN_TIMES = 10;
//            getBarcodeCallback();
            Log.d(TAG, "getBarcode: " + qr_content);
            Intent data = new Intent();
            data.putExtra(activity.BarcdeObject, content);
            activity.setResult(CommonStatusCodes.SUCCESS, data);
            activity.finish();
        }
    }

    public void setActivity(CaptureActivity activity) {
        this.activity = activity;
    }
}

