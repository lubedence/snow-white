package com.tuwien.snowwhite;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.tuwien.snowwhite.R;

public class CamActivity extends Activity {
  private final static String DEBUG_TAG = "MakePhotoActivity";
  private Camera camera = null;
  private int cameraFrontId = -1;
  private int cameraBackId = -1;
  private int cameraUsedId = -1;
  private boolean frontCamUsed = true;
  private boolean flashUsed = false;
  private boolean hasFlash = false;
  private CameraPreview mPreview = null;
  private static int RESULT_LOAD_IMAGE = 1;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cam);
    cameraSetUp();
  }
  
  
  private void cameraSetUp(){
	  if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
		  Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG).show();
	  } else {
		  
		  if (!findCameras()) {
			  Toast.makeText(this, "No camera found.",Toast.LENGTH_LONG).show();
		  } else {
			  
			  ImageButton btn=(ImageButton)findViewById(R.id.button_flash);
			  if(!hasFlash) btn.setVisibility(View.INVISIBLE);
			  else btn.setVisibility(View.VISIBLE);
			  
			  if(mPreview == null){
				  mPreview = new CameraPreview(this, camera);
				  FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
				  preview.addView(mPreview);
			  }
			  else{
				  mPreview.setCamera(camera);
				  mPreview.startPreview();
			  }
		  }
	  }
  }
  
  public void takePhoto(View view) {
	  if(!mPreview.isPreviewRunning()){
		  Toast.makeText(this, "Preview is loading...", Toast.LENGTH_SHORT).show();
		  return;
	  }
		  
	  if(camera!=null)
  		camera.takePicture(null, null, new PhotoHandler(getApplicationContext(), this));
	  else
		  Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG).show();
	}
  
  public void swichCamera(View view) {
	  Toast.makeText(this, getString(R.string.button_main_start_loading), Toast.LENGTH_SHORT).show();
	  releaseCam();
	  frontCamUsed = !frontCamUsed;
	  cameraSetUp();
	  }
  
  public boolean isFrontCamera(){
	  return frontCamUsed;
  }
  
  public void useFlash(View view){
	  
	  if(!hasFlash)
		  return;
	  
	  Parameters p = camera.getParameters();
	  
	  if(p.getFlashMode().contentEquals(Parameters.FLASH_MODE_ON)){
		  p.setFlashMode(Parameters.FLASH_MODE_OFF);
		  flashUsed = false;
	  }
	  else{
		  p.setFlashMode(Parameters.FLASH_MODE_ON);
		  flashUsed = true;	  
	  }
	  setFlashIcon(flashUsed);
	  camera.setParameters(p);
  }
  
  private void setFlashIcon(boolean flashOn){
	  final ImageButton button = (ImageButton) findViewById(R.id.button_flash);
	  if(flashOn)
		  button.setImageDrawable(this.getResources().getDrawable( R.drawable.button_flash_on_selector ));
	  else
		  button.setImageDrawable(this.getResources().getDrawable( R.drawable.button_flash_off_selector ));
  }
  
  public void backToMain(View view){
	  finish();
  }
  
  public void chooseStoredImg(View view){
	  Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	  startActivityForResult(i, RESULT_LOAD_IMAGE);
  }
  
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);

      if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
          Uri selectedImage = data.getData();
          String[] filePathColumn = { MediaStore.Images.Media.DATA };
  
          Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
          cursor.moveToFirst();
  
          int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
          String picturePath = cursor.getString(columnIndex);
          cursor.close();
          
         
          //if picture is from snowwhite-folder no need to create an adjusted image
          if(picturePath.contains(PhotoHandler.getPictureDirectory())){
        	  afterImgSaved(picturePath);
        	  return;
          }
          
          //adjust this gallery image and save a copy in the snowwhite folder
          String newPicturePath = PhotoHandler.CreateFileName();
          try {
			PhotoHandler.saveAdjustedImg(newPicturePath, PhotoHandler.getResizedBitmap(picturePath, 600));
		  } catch (Exception e) {
			  Log.d("CamActivity:PhotoHandler:onPictureTaken", "File" + newPicturePath + "not saved: " + e.getMessage());
	          Toast.makeText(this, "Image from gallery could not be processed.",Toast.LENGTH_LONG).show();
	          return;
		  }

          afterImgSaved(newPicturePath);
      }
  }
  
  //called by PhotoHandler(take photo with cam) & onActivityResult(load photo from gallery)
  public void afterImgSaved(String imgPath){
	  FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
	  preview.removeView(mPreview);
	  mPreview = null;

	  releaseCam();
	  
      Intent intent = new Intent(this, FacialFeatureActivity.class);
      intent.putExtra("imgFile", imgPath);
      Log.d("CamActivity","START NEXT ACTIVITY");
      startActivity(intent); 
  }
  
  public int getUsedCamId(){
	  return cameraUsedId;
  }

  private boolean findCameras() {
    int numberOfCameras = Camera.getNumberOfCameras();
    for (int i = 0; i < numberOfCameras; i++) {
      CameraInfo info = new CameraInfo();
      Camera.getCameraInfo(i, info);
      if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
    	  Log.d(DEBUG_TAG, "Camera front facing found");
    	  cameraFrontId = i;
      }
      else if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
    	  Log.d(DEBUG_TAG, "Camera back facing found");
    	  cameraBackId = i;
      }
    }
    
    if (cameraFrontId < 0 && cameraBackId < 0) {
		  return false;
	  } else {
		  if(cameraFrontId >= 0 && frontCamUsed){
			  camera = Camera.open(cameraFrontId);
			  cameraUsedId = cameraFrontId;
		  }
		  else{
			  camera = Camera.open(cameraBackId);
			  cameraUsedId = cameraBackId;
		  }
		  
		  Parameters p = camera.getParameters();
		  if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH) || p.getFlashMode() == null)
			  hasFlash = false;
		  else
			  hasFlash = true;
		  
		  setFlashIcon(false);
	  }
    return true;
  }
  
  public void releaseCam(){
	  if (camera != null) {
	      camera.release();
	      camera = null;
	    }
  }

  @Override
  protected void onPause() {
	releaseCam();
    super.onPause();
  }
  
  @Override
  public void onResume() {
      super.onResume();
     if(camera==null){    	 
    	 cameraSetUp();
     }
  }

} 