package com.example.flashlight;
import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private CameraManager cameraManager;
    private String cameraId;
    ImageView toggleButton;
    boolean flashOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggleButton = findViewById(R.id.toggleButton);

        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
            try{
                cameraId = cameraManager.getCameraIdList()[0];
            }
            catch (CameraAccessException e){
                e.getStackTrace();
            }
        }
        else{
            Toast.makeText(this, "Device has not FlashLight", Toast.LENGTH_SHORT).show();
        }

        toggleButton.setOnClickListener(view -> {
            if(flashOn){
                flashOn = false;
                turnOffFlash();
            }
            else {
                flashOn = true;
                trunOnFlash();

            }
        });

    }


    public  void resizeImage(int size){
        int sizeInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, getResources().getDisplayMetrics());
        toggleButton.getLayoutParams().width = sizeInPixels;
        toggleButton.getLayoutParams().height = sizeInPixels;
        toggleButton.requestLayout();
    }
    public void trunOnFlash(){
        try{
            cameraManager.setTorchMode(cameraId, true);
            resizeImage(110);
            flashOn = true;
        }
        catch (CameraAccessException e){
            e.getStackTrace();
        }

    }

    public void turnOffFlash(){
        try{
            cameraManager.setTorchMode(cameraId, false);
            resizeImage(130);
            flashOn = false;
        }
        catch (CameraAccessException e ){
            e.getStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        trunOnFlash();
    }

    @Override
    protected void onPause() {
        super.onPause();
        turnOffFlash();
    }
}
