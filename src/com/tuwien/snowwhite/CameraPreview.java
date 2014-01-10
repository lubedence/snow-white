package com.tuwien.snowwhite;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.FaceDetectionListener;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private String TAG = "CameraPreview";
    private int viewWidth = 640;
    private boolean previewIsRunning = false;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        
        Point outSize = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(outSize);
        viewWidth = outSize.x;
        
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
        	
        	List<Size> sizes = p.getSupportedPreviewSizes();
        	Size optimalSize = getOptimalPreviewSize(sizes, this.getWidth(), this.getHeight());
        	p.setPreviewSize(optimalSize.width, optimalSize.height);
        	        	
        	mCamera.setParameters(p);	
        	mCamera.setDisplayOrientation(90);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.setOneShotPreviewCallback(new PreviewCallback() {
				@Override
				public void onPreviewFrame(byte[] data, Camera camera) {
					previewIsRunning = true;
				}
			});
            mCamera.startPreview();
            
            
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }
    
    public boolean isPreviewRunning(){
    	return previewIsRunning;
    }
  
    
    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            (this).layout(0, 0, viewWidth, viewWidth*4/3);
        }
    }

    
    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
    	//TODO: set flag to enable "take picture"-button
    	startPreview();     
    }
    
    public void onFaceDetection(Face[] faces, Camera camera) {  
    	Log.e("FACEDETECTION", "COUNT: "+faces.length);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    	if(mCamera != null){
    		previewIsRunning = false;
    		mCamera.release();
    		mCamera = null;
    	}
    }
    

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
    	
    	//TODO: preview CANT change, so no need for the following stuff???

       /* if (mHolder.getSurface() == null){
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
        }*/
    }
    
    
    
    
    
    private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }
    
    
    
    
    
    
    
    
    
    
}