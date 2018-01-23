package com.example.you.lsmisclient.qr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;
import com.example.you.lsmisclient.R;
import com.example.you.lsmisclient.qr.barcodeutil.BarcodeFactory;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class CaptureActivity extends AppCompatActivity {

    private CameraSource cameraSource = null;
    private VideoView scan_preview = null;
    private BarcodeDetector scan_detector = null;
    private BarcodeFactory barcodeFactory = null;
    public static final String BarcdeObject="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_capture);

        scan_preview = (VideoView) findViewById(R.id.scan_preview);
        scan_detector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE | Barcode.TEXT | Barcode.DATA_MATRIX).build();
        cameraSource = new CameraSource.Builder(this, scan_detector)
                .setAutoFocusEnabled(true)
                .setRequestedFps(20)
                .build();
        barcodeFactory = new BarcodeFactory();
        barcodeFactory.setActivity(this);

        scan_detector.setProcessor(new MultiProcessor.Builder<>(barcodeFactory).build());


        //新开一个线程，延迟打开摄像头
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        cameraSource.start(scan_preview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
    }

    /**
     * 释放资源
     */
    @Override protected void onStop(){
        super.onStop();
        cameraSource.stop();
        cameraSource.release();
        cameraSource = null;
        scan_detector = null;
    }
}
