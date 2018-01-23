package com.example.you.lsmisclient.qr;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

/**
 * Created by chendian on 2018/1/23.
 */
public class QrScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private static final String TAG = "QrScannerActivity";
    public final static String CODE_RESULT = "value";


    /**
     * 处理获取的二维码
     * @param rawResult
     */
    @Override
    public void handleResult(Result rawResult) {
        final String result = rawResult.getText();
        final BarcodeFormat format = rawResult.getBarcodeFormat();
        Log.d("QRCodeScanner", rawResult.getText());
        Log.d("QRCodeScanner",format.toString());
        if(format == BarcodeFormat.QR_CODE){
            Intent intent = new Intent();
            intent.putExtra(CODE_RESULT,result);
            this.setResult(RESULT_OK, intent);
            this.finish();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("扫描结果非二维码！");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mScannerView.resumeCameraPreview(QrScannerActivity.this);
            }
        });

        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
    }

    @Override
    protected void onCreate (Bundle saveInstatnceState){
        super.onCreate(saveInstatnceState);
        Log.d(TAG, "onCreate: scanner preview create");
        mScannerView = new ZXingScannerView(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(mScannerView);
        int currebtapiVersion = Build.VERSION.SDK_INT;

           if( ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA ) ==
                   PackageManager.PERMISSION_GRANTED){
               Log.d(TAG, "onCreate: permision granted ");
            } else {
                Toast.makeText(this, "permision denied, try to request!",Toast.LENGTH_SHORT);
               ActivityCompat.requestPermissions(this,new String[]{CAMERA},REQUEST_CAMERA);
            }

    }

    private boolean checkPermission(){
        return ( ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA )
                ==  PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionResult (int requestCode , String permissions[] , int [] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccept = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccept) {
                        Toast.makeText(getApplicationContext(), "camera permission granted !", Toast.LENGTH_SHORT);
                    } else {
                        Toast.makeText(getApplicationContext(), "permission denied! cant open camera", Toast.LENGTH_SHORT);
                        if (shouldShowRequestPermissionRationale(CAMERA)) {
                            showMessageOKCancel("You need to allow access to both the permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{CAMERA},
                                                        REQUEST_CAMERA);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel (String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onResume(){
        super.onResume();
            if (checkPermission()) {
                if(mScannerView == null) {
                    mScannerView = new ZXingScannerView(this);
                    setContentView(mScannerView);
                }
                mScannerView.setResultHandler(this);
                mScannerView.startCamera();
            } else {
                requestPermission();
            }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mScannerView.stopCamera();
    }
}


