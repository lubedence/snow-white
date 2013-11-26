package com.tuwien.snowwhite;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.FaceDetectionListener;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, FaceDetectionListener {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private String TAG = "CameraPreview";

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void setCamera(Camera c){
    	if(mCamera != null){
    		mCamera.release();
    		mCamera = null;
    	}
    	mCamera = c;
    }
    
    public void startPreview(){
    	try {
        	Parameters p=mCamera.getParameters(); 
        	p.setRotation(270);
        	mCamera.setParameters(p);	
        	//mCamera.setFaceDetectionListener(this);
        	mCamera.setDisplayOrientation(90);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
           // mCamera.startFaceDetection();
            
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    
    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
    	startPreview();     
    }
    
    public void onFaceDetection(Face[] faces, Camera camera) {  
    	Log.e("FACEDETECTION", "COUNT: "+faces.length);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    	if(mCamera != null){
    		mCamera.release();
    		mCamera = null;
    	}
    }
    

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
          // preview surface does not exist
          return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
          // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
}