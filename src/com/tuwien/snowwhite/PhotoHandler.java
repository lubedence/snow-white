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
  private int camId = 0;

  public PhotoHandler(Context context, CamActivity act, int camId) {
    this.context = context;
    this.act = act;
    this.camId = camId;
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
    	
    	Bitmap realImage = BitmapFactory.decodeByteArray(data, 0, data.length);
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(camId, info);
        Bitmap bitmap = rotate(realImage, info.orientation);
      
      FileOutputStream fos = new FileOutputStream(pictureFile);
      //fos.write(data);
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
      fos.close();
      Toast.makeText(context, "New Image saved:" + photoFile, Toast.LENGTH_LONG).show();
      
      
     act.afterImgSaved(filename);
      
      
    } catch (Exception error) {
     // Log.d(CamActivity.DEBUG_TAG, "File" + filename + "not saved: " + error.getMessage());
      Toast.makeText(context, "Image could not be saved.",
          Toast.LENGTH_LONG).show();
    }
  }
  
  private Bitmap rotate(Bitmap bitmap, int degree) {
	    int w = bitmap.getWidth();
	    int h = bitmap.getHeight();

	    Matrix mtx = new Matrix();
	    mtx.postRotate(degree);

	    return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
	}

  private File getDir() {
    File sdDir = Environment
      .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    return new File(sdDir, "CameraAPIDemo");
  }
} 