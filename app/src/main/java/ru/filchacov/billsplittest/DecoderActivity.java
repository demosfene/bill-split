package ru.filchacov.billsplittest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView.OnQRCodeReadListener;
import com.google.android.material.snackbar.Snackbar;

import ru.filchacov.billsplittest.qrReader.PointsOverlayView;


public class DecoderActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback, OnQRCodeReadListener, ShowUpButton{

    private static final int MY_PERMISSION_REQUEST_CAMERA = 0;

    private ViewGroup mainLayout;

    private QRCodeReaderView qrCodeReaderView;
    private PointsOverlayView pointsOverlayView;
    private int flag = 0;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_decoder);

        mainLayout = findViewById(R.id.main_layout);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            initQRCodeReaderView();
        } else {
            requestCameraPermission();
        }
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showUpButton(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle(R.string.bill_qr_scaner);
        if (qrCodeReaderView != null) {
            qrCodeReaderView.startCamera();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (qrCodeReaderView != null) {
            qrCodeReaderView.stopCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSION_REQUEST_CAMERA) {
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(mainLayout, "Camera permission was granted.", Snackbar.LENGTH_SHORT).show();
            initQRCodeReaderView();
        } else {
            Snackbar.make(mainLayout, "Camera permission request was denied.", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        pointsOverlayView.setPoints(points);
        if (flag == 0) {
            Intent intent = new Intent(this, BillActivity.class);
            intent.putExtra("QRInfo", text);
            startActivity(intent);
            flag++;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        flag = 0;
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Snackbar.make(mainLayout, "Camera access is required to display the camera preview.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> ActivityCompat.requestPermissions(DecoderActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, MY_PERMISSION_REQUEST_CAMERA)).show();
        } else {
            Snackbar.make(mainLayout, "Permission is not available. Requesting camera permission.",
                    Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            }, MY_PERMISSION_REQUEST_CAMERA);
        }
    }

    private void initQRCodeReaderView() {
        View content = getLayoutInflater().inflate(R.layout.content_decoder, mainLayout, true);

        qrCodeReaderView = content.findViewById(R.id.qrdecoderview);
        CheckBox flashlightCheckBox = content.findViewById(R.id.flashlight_checkbox);
//        CheckBox enableDecodingCheckBox = content.findViewById(R.id.enable_decoding_checkbox);
        pointsOverlayView = content.findViewById(R.id.points_overlay_view);

        qrCodeReaderView.setAutofocusInterval(2000L);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setBackCamera();
        flashlightCheckBox.setOnCheckedChangeListener((compoundButton, isChecked) -> qrCodeReaderView.setTorchEnabled(isChecked));
//        enableDecodingCheckBox.setOnCheckedChangeListener((compoundButton, isChecked) -> qrCodeReaderView.setQRDecodingEnabled(isChecked));
        qrCodeReaderView.startCamera();
    }

    @Override
    public void showUpButton(boolean b) {
        if (getSupportActionBar() != null) {
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
            toolbar.setNavigationOnClickListener(v -> onSupportNavigateUp());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}