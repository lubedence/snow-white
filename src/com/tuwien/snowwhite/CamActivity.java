package com.tuwien.snowwhite;

import org.opencv.android.OpenCVLoader;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tuwien.snowwhite.R;

public class CamActivity extends Activity {
  private final static String DEBUG_TAG = "MakePhotoActivity";
  private Camera camera;
  private int cameraId = 0;
  private CameraPreview mPreview = null;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cam);

    cameraSetUp();
  }
  
  
  private void cameraSetUp(){
	    if (!getPackageManager()
	        .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
	      Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG).show();
	    } else {
	      cameraId = findFrontFacingCamera();
	      if (cameraId < 0) {
	        Toast.makeText(this, "No front facing camera found.",
	            Toast.LENGTH_LONG).show();
	      } else {
	          camera = Camera.open(cameraId);
	          if(mPreview == null){
		    	  mPreview = new CameraPreview(this, camera);
		          FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		          preview.addView(mPreview);
		      }
	          else{
	        	  mPreview.setCamera(camera);
	          }
	      }
	    }
  }
  
  
  public void takePhoto(View view) {
	    // Do something in response to button
	  	// get an image from the camera
	  if(camera!=null)
  		camera.takePicture(null, null, new PhotoHandler(getApplicationContext(), this, cameraId));
	  else
		  Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG).show();
	}
  
  //called by PhotoHandler
  public void afterImgSaved(String imgPath){
      Intent intent = new Intent(this, MainActivity.class);
      intent.putExtra("imgFile", imgPath);
      startActivity(intent); 
  }

  private int findFrontFacingCamera() {
    int cameraId = -1;
    // Search for the front facing camera
    int numberOfCameras = Camera.getNumberOfCameras();
    for (int i = 0; i < numberOfCameras; i++) {
      CameraInfo info = new CameraInfo();
      Camera.getCameraInfo(i, info);
      if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
        Log.d(DEBUG_TAG, "Camera found");
        cameraId = i;
        break;
      }
    }
    return cameraId;
  }

  @Override
  protected void onPause() {
    if (camera != null) {
      camera.release();
      camera = null;
    }
    super.onPause();
  }
  
  @Override
  public void onResume() {
      super.onResume();
     if(camera==null)
    	 cameraSetUp();
  }

} 