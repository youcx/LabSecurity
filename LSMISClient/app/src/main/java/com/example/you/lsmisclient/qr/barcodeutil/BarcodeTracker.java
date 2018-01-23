package com.example.you.lsmisclient.qr.barcodeutil;


import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by chendian on 2018/1/21.
 */

public class BarcodeTracker  extends Tracker<Barcode> {
    private BarcodeFactory factory = null;

    private int CONFIRM_SCAN_TIMES = 10;
    private Barcode content ;
    public void setFactory(BarcodeFactory factory) {
        this.factory = factory;
    }
    @Override
    public void onUpdate(Detector.Detections<Barcode> results,Barcode barcode){
        SparseArray<Barcode> items = results.getDetectedItems();
        content = items.valueAt(0);
        //Log.d(TAG, "detections size = " + items.size() +"\n barcode value \t" + content);
        factory.confirmBarcode(content);
    }
}

