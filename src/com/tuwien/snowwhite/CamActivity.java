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
  private final static int RESULT_LOAD_IMAGE = 1;
  
  private Camera camera = null;
  private int cameraFrontId = -1; 		//front camera id, if available
  private int cameraBackId = -1; 		//back camera id, if available
  private int cameraUsedId = -1; 		//current used camera id
  private boolean frontCamUsed = true; 	//if front camera used true, if back camera used, false
  private boolean flashUsed = false;	//if flash enabled true, else false
  private boolean hasFlash = false;		//true if camera has flash, else false
  private CameraPreview mPreview = null;//camera preview SurfaceView
  

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cam);
    
    cameraSetUp(); //camera initialization
  }
  
  
  private void cameraSetUp(){
	  
	  //device without camera
	  if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
		  Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG).show();
	  } else { //camera(s) available
		  
		  //get camera id(s) and information about flashlight
		  if (!findCameras()) {
			  Toast.makeText(this, "No camera found.",Toast.LENGTH_LONG).show();
		  } else {
			  
			  //set flash button if flashlight on current camera is present
			  ImageButton btn=(ImageButton)findViewById(R.id.button_flash);
			  if(!hasFlash) btn.setVisibility(View.INVISIBLE);
			  else btn.setVisibility(View.VISIBLE);
			  
			  //create or update/restart camera preview-view and add it on the layout 
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
	  //if take photo button is clicked before preview is ready
	  if(!mPreview.isPreviewRunning()){
		  Toast.makeText(this, "Preview is loading...", Toast.LENGTH_SHORT).show();
		  return;
	  }
	  
	  //take picture
	  if(camera!=null)
  		camera.takePicture(null, null, new PhotoHandler(getApplicationContext(), this));
	  else
		  Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG).show();
	}
  
  //swich-camera button handler
  public void swichCamera(View view) {
	  Toast.makeText(this, getString(R.string.button_main_start_loading), Toast.LENGTH_SHORT).show();
	  releaseCam();
	  frontCamUsed = !frontCamUsed;
	  cameraSetUp();
	  }
  
  //returns true if front camera is used, otherwise (back camera) false
  public boolean isFrontCamera(){
	  return frontCamUsed;
  }
  
  //flash button handler
  public void useFlash(View view){
	  
	  if(!hasFlash)
		  return;
	  
	  Parameters p = camera.getParameters();
	  
	  //flash off
	  if(p.getFlashMode().contentEquals(Parameters.FLASH_MODE_ON)){
		  p.setFlashMode(Parameters.FLASH_MODE_OFF);
		  flashUsed = false;
	  }
	  //flash on
	  else{
		  p.setFlashMode(Parameters.FLASH_MODE_ON);
		  flashUsed = true;	  
	  }
	  
	  //set flash icon
	  setFlashIcon(flashUsed);
	  
	  //change flash-mode
	  camera.setParameters(p);
  }
  
  //set the icon of the flash button (on/off sign)
  private void setFlashIcon(boolean flashOn){
	  final ImageButton button = (ImageButton) findViewById(R.id.button_flash);
	  if(flashOn)
		  button.setImageDrawable(this.getResources().getDrawable( R.drawable.button_flash_on_selector ));
	  else
		  button.setImageDrawable(this.getResources().getDrawable( R.drawable.button_flash_off_selector ));
  }
  
  //back to start activity
  public void backToMain(View view){
	  finish();
  }
  
  //open gallery button handler
  public void chooseStoredImg(View view){
	  //get path of chosen image
	  Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	  //after user selected image from gallery (or aborted)
	  startActivityForResult(i, RESULT_LOAD_IMAGE);
  }
  
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);

      if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
          //get path to chosen gallery-image
    	  Uri selectedImage = data.getData();
          String[] filePathColumn = { MediaStore.Images.Media.DATA };
          Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
          cursor.moveToFirst();
          int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
          String picturePath = cursor.getString(columnIndex);
          cursor.close();
          
         
          //if picture is from snowwhite-folder, no need to create an adjusted image
          if(picturePath.contains(PhotoHandler.getPictureDirectory())){
        	  afterImgSaved(picturePath);
        	  return;
          }
          
          //adjust this gallery image and save a copy in the snowwhite folder
          String newPicturePath = PhotoHandler.CreateFileName();
          try {
        	//get width from settings
        	int witdh = MyStoredData.getInstance().getSharedPreferences().getInt(getString(R.string.settings_size_value), 600);
			//save adjusted image
        	PhotoHandler.saveAdjustedImg(newPicturePath, PhotoHandler.getResizedBitmap(picturePath, witdh));
		  } catch (Exception e) {
	          Toast.makeText(this, getString(R.string.image_not_saved),Toast.LENGTH_LONG).show();
	          return;
		  }

          //prepare for next activity (facial feature calculation)
          afterImgSaved(newPicturePath);
      }
  }
  
  //called by PhotoHandler(take picture with camera) or onActivityResult(load photo from gallery)
  public void afterImgSaved(String imgPath){
	  //close camera preview
	  FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
	  preview.removeView(mPreview);
	  mPreview = null;

	  //close camera
	  releaseCam();
	  
	  //start next activity (facial feature detection) 
      Intent intent = new Intent(this, FacialFeatureActivity.class);
      intent.putExtra("imgFile", imgPath);
      startActivity(intent); 
  }
  
  //current used camera id
  public int getUsedCamId(){
	  return cameraUsedId;
  }

  //search back and/or front camera
  private boolean findCameras() {
    int numberOfCameras = Camera.getNumberOfCameras();
    for (int i = 0; i < numberOfCameras; i++) {
	      CameraInfo info = new CameraInfo();
	      Camera.getCameraInfo(i, info);
	      //front camera
	      if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
	    	  Log.d(DEBUG_TAG, "Camera front facing found");
	    	  cameraFrontId = i;
	      }
	      //back camera
	      else if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
	    	  Log.d(DEBUG_TAG, "Camera back facing found");
	    	  cameraBackId = i;
	      }
    }
    
    if (cameraFrontId < 0 && cameraBackId < 0) {
		  return false;
	  } else {
		  	  //use front camera if existing
			  if(cameraFrontId >= 0 && frontCamUsed){
				  camera = Camera.open(cameraFrontId);
				  cameraUsedId = cameraFrontId;
			  }
			  //otherwise use back camera
			  else{
				  camera = Camera.open(cameraBackId);
				  cameraUsedId = cameraBackId;
			  }
			  
			  //has current used camera a flash?
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