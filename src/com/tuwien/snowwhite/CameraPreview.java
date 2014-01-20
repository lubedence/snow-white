/*******************************************************************************
 * Copyright (c) 2014 Lukas Furlan (furlan.lukas@gmail.com)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Lukas Furlan - initial API and implementation
 ******************************************************************************/
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
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
	private final static String TAG = "CameraPreview";
	
    private SurfaceHolder mHolder;
    private Camera mCamera;		//camera object for the preview
    private int viewWidth;		//display width
    private boolean previewIsRunning = false; //true as soon as preview is running

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        
        //get display width
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

    //called by the CamActivity on camera switch (front/back)
    public void setCamera(Camera c){
    	if(mCamera != null){
    		mCamera.release();
    		mCamera = null;
    	}
    	mCamera = c;
    }
    
    //start preview
    public void startPreview(){
    	try {
        	Parameters p=mCamera.getParameters(); 
        	//p.setRotation(270); //rotate preview to portrait mode
        	
        	//get the optimal preview size
        	List<Size> sizes = p.getSupportedPreviewSizes();
        	Size optimalSize = getOptimalPreviewSize(sizes, this.getWidth(), this.getHeight());
        	p.setPreviewSize(optimalSize.width, optimalSize.height);

        	//set the layout height to match the preview ratio
        	this.setLayoutParams(new FrameLayout.LayoutParams(viewWidth,Math.round(viewWidth*((float)optimalSize.width/optimalSize.height))));
        	mCamera.setParameters(p);	
        	mCamera.setDisplayOrientation(90); //rotate preview to portrait mode
            mCamera.setPreviewDisplay(mHolder);
            
            //callback for the first displayed frame, so we know when the preview has finished initializing
            mCamera.setOneShotPreviewCallback(new PreviewCallback() {
				@Override
				public void onPreviewFrame(byte[] data, Camera camera) {
					previewIsRunning = true;
				}
			});
            
            //start preview
            mCamera.startPreview();
            
            
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }
    
    //has preview finished initializing and is running?
    public boolean isPreviewRunning(){
    	return previewIsRunning;
    }
  
    //start preview as soon as surface is created
    public void surfaceCreated(SurfaceHolder holder) {
    	startPreview();     
    }
    
    //releasing the camera preview
    public void surfaceDestroyed(SurfaceHolder holder) {
    	if(mCamera != null){
    		previewIsRunning = false;
    		mCamera.release();
    		mCamera = null;
    	}
    }
    

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    	//preview CANT change, so no need for this
    }
    
    //get the optimal preview size, if available
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
