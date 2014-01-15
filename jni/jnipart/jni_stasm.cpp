#include <jni.h>
#include "opencv2/opencv.hpp"
#include <android/log.h>
#include "../stasm/stasm_lib.h"

extern "C" {
	JNIEXPORT jintArray JNICALL Java_com_tuwien_snowwhite_FacialFeatureActivity_FindFaceLandmarks(
		JNIEnv* env, jobject, jstring jpath)
	{
		clock_t StartTime = clock();
		jintArray arr = env->NewIntArray(2*stasm_NLANDMARKS);
		jint *out = env->GetIntArrayElements(arr, NULL);

		static const char * path;
		path = env->GetStringUTFChars( jpath , NULL ) ;
		cv::Mat_<unsigned char> img(cv::imread(path, CV_LOAD_IMAGE_GRAYSCALE));

    	if (!img.data) 
    	{
    		__android_log_print(ANDROID_LOG_ERROR, "FacialFeatureActivity_FindFaceLandmarks", "Cannot load image file %s", path);
    		out[0] = -1;
    		out[1] = -1;
    		img.release();
			env->ReleaseIntArrayElements(arr, out, 0);
			return arr;
    	}
    
    	int foundface;
    	float landmarks[2 * stasm_NLANDMARKS]; // x,y coords

    	if (!stasm_search_single(&foundface, landmarks, (const char*)img.data, img.cols, img.rows, path, 
    		"/data/data/com.tuwien.snowwhite/app_stasmdata/"))
    	{
    		__android_log_print(ANDROID_LOG_ERROR, "FacialFeatureActivity_FindFaceLandmarks", "Error in stasm_search_single %s", stasm_lasterr());
    		out[0] = -2;
    		out[1] = -2;
    		img.release();
			env->ReleaseIntArrayElements(arr, out, 0);
			return arr;
    	}

   		if (!foundface) 
   		{
   			__android_log_print(ANDROID_LOG_WARN, "FacialFeatureActivity_FindFaceLandmarks", "no face found");
   			out[0] = -3;
    		out[1] = -3;
    		img.release();
			env->ReleaseIntArrayElements(arr, out, 0);
			return arr;
   		} else {
			for (int i = 0; i < stasm_NLANDMARKS; i++)
			{		
				out[2*i]   = cvRound(landmarks[2*i]);
				out[2*i+1] = cvRound(landmarks[2*i+1]);
			}
		}	
		double TotalAsmTime = double(clock() - StartTime) / CLOCKS_PER_SEC;
    	__android_log_print(ANDROID_LOG_INFO, "FacialFeatureActivity_FindFaceLandmarks", "Stasm Ver:%s Img:%dx%d ---> Time:%.3f secs.", stasm_VERSION, img.cols, img.rows, TotalAsmTime);
    	
    	img.release();
		env->ReleaseIntArrayElements(arr, out, 0);
		return arr;
	}
}
