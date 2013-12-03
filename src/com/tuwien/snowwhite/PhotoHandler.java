package com.tuwien.snowwhite;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class PhotoHandler implements PictureCallback {

  private final Context context;
  private CamActivity act = null;

  public PhotoHandler(Context context, CamActivity act) {
    this.context = context;
    this.act = act;
  }

  @Override
  public void onPictureTaken(byte[] data, Camera camera) {

    File pictureFileDir = getDir();

    if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

     // Log.d(CamActivity.DEBUG_TAG, "Can't create directory to save image.");
      Toast.makeText(context, "Can't create directory to save image.",
          Toast.LENGTH_LONG).show();
      return;

    }
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
    String date = dateFormat.format(new Date());
    String photoFile = "Picture_" + date + ".jpg";

    String filename = pictureFileDir.getPath() + File.separator + photoFile;

    File pictureFile = new File(filename);

    try {
    	BitmapFactory.Options options=new BitmapFactory.Options();
    	options.inJustDecodeBounds=true;
    	BitmapFactory.decodeByteArray(data, 0, data.length, options);
    	options.inSampleSize=getScale(options.outWidth, options.outHeight,1200);
    	options.inJustDecodeBounds=false;
    	
    	Bitmap realImage = BitmapFactory.decodeByteArray(data, 0, data.length,options);
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(act.getUsedCamId(), info);
        Bitmap bitmap = rotate(realImage, info.orientation);
        
        
      
      FileOutputStream fos = new FileOutputStream(pictureFile);
      //fos.write(data);
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
      fos.close();
      Toast.makeText(context, "New Image saved:" + photoFile, Toast.LENGTH_LONG).show();
      
      realImage.recycle();
      bitmap.recycle();
      
     act.afterImgSaved(filename);
      
      
    } catch (Exception error) {
     // Log.d(CamActivity.DEBUG_TAG, "File" + filename + "not saved: " + error.getMessage());
      Toast.makeText(context, "Image could not be saved.",
          Toast.LENGTH_LONG).show();
    }
  }
  
  private int getScale(int w, int h, int maxWidth){
	  if(w > maxWidth || h > maxWidth){
		    float f = 0;
		    if(w>h)
		    	f = (float)w/maxWidth;
		    else
		    	f = (float)h/maxWidth;
		    
		    return Math.round(f);
	    }
	  else
		  return 1;
	  
  }
  
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

  private File getDir() {
    File sdDir = Environment
      .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    return new File(sdDir, "CameraAPIDemo");
  }
} 