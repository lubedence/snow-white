package com.tuwien.snowwhite;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class PhotoHandler implements PictureCallback {

  private final Context context;
  private CamActivity act = null;
  private final int IMG_SIZE; //image size (width) 

  public PhotoHandler(Context context, CamActivity act) {
    this.context = context;
    this.act = act;
    
    //get the width for saving the picture.
    IMG_SIZE = MyStoredData.getInstance().getSharedPreferences().getInt(context.getString(R.string.settings_size_value), 600);
  }
  
  @Override
  public void onPictureTaken(byte[] data, Camera camera) {

    	//resize taken picture
    	Bitmap realImage = getResizedBitmap(data,IMG_SIZE);
    	data = null; //free memory
        
    	//rotate (and mirror if front cam) picture to portrait
    	android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(act.getUsedCamId(), info);
        Bitmap bitmap = rotate(realImage, info.orientation);
        realImage.recycle(); //free memory
        realImage = null; //free memory
        
        //get path and name for storing the image
        String filename = CreateFileName();
       
        //save adjusted image
        try {
	        saveAdjustedImg(filename, bitmap);
        } catch (Exception error) {
             Log.d("PhotoHandler:onPictureTaken", "File" + filename + "not saved: " + error.getMessage());
             Toast.makeText(context, "Image could not be saved.",Toast.LENGTH_LONG).show();
             return;
        }
        finally{
        	bitmap.recycle(); //free memory
        	bitmap = null; //free memory
        }
        
        //proceed and go to next activity
        act.afterImgSaved(filename);
      
      
    
  }
  
  //returns path to image folder, or null on failure while creating directory
  public static String getPictureDirectory(){
	  File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	  File pictureFileDir = new File(sdDir, "Snowwhite");

	  if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
	    Log.d("PhotoHandler:CreateFileName", "Can't create directory to save image.");
	    return null;
	    }
	  
	  return pictureFileDir.getPath();
  }
  
  //returns image-path or null on error
  public static String CreateFileName(){
	  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
	  String date = dateFormat.format(new Date());
	  String photoFile = "Picture_" + date + ".jpg";
	  
	  String dir = getPictureDirectory();
	  if(dir == null)
		  return null;
	  
	  return getPictureDirectory() + File.separator + photoFile;
  }
  
  //save image source to path
  public static void saveAdjustedImg(String path, Bitmap source) throws Exception{
	  File pictureFile = new File(path);
	  
      FileOutputStream fos = new FileOutputStream(pictureFile);
      source.compress(Bitmap.CompressFormat.JPEG, 100, fos);
      fos.close();      
  }
  
  //resize image. returned bitmap.width<=maxWidth
  public static Bitmap getResizedBitmap(byte[] data, int maxWidth){
	BitmapFactory.Options options=new BitmapFactory.Options();
  	options.inJustDecodeBounds=true; //just read image dimensions without allocating memory
  	BitmapFactory.decodeByteArray(data, 0, data.length, options);
  	options.inSampleSize=getScale(options.outWidth, options.outHeight,maxWidth); //scale
  	options.inJustDecodeBounds=false; //decoder will return the image, allocates memory
  	options.inPreferredConfig=Bitmap.Config.RGB_565; //memory usage = 1/2 but some quality loss
  	
  	return BitmapFactory.decodeByteArray(data, 0, data.length,options);
  }
  
//resize image. returned bitmap.width<=maxWidth
  public static Bitmap getResizedBitmap(String path, int maxWidth){
		BitmapFactory.Options options=new BitmapFactory.Options();
	  	options.inJustDecodeBounds=true; //just read image dimensions without allocating memory
	  	BitmapFactory.decodeFile(path, options);
	  	options.inSampleSize=getScale(options.outWidth, options.outHeight,maxWidth); //scale
	  	options.inJustDecodeBounds=false; //decoder will return the image, allocates memory
	  	
	  	return BitmapFactory.decodeFile(path, options);
	  }
  
  
  //returns the correct height for the wanted width
  private static int getScale(int w, int h, int maxWidth){
	  if(w > maxWidth || h > maxWidth){
		    float f = 0;
		    if(w>h)
		    	f = (float)w/maxWidth;
		    else
		    	f = (float)h/maxWidth;
		    
		    return Math.round(f);
	    }
	  //if image width is already small enough
	  else
		  return 1;
	  
  }
  
  //rotate (and mirror if front-camera) image
  private Bitmap rotate(Bitmap bitmap, int degree) {
	    int w = bitmap.getWidth();
	    int h = bitmap.getHeight();
	    
	    Matrix mtx = new Matrix();
	    
	    if(w>h){
	    	mtx.postRotate(degree);
	    	if(act.isFrontCamera())	mtx.preScale(1, -1);
	    }
	    else if(act.isFrontCamera()) mtx.preScale(-1, 1);

	    return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
	}

} 