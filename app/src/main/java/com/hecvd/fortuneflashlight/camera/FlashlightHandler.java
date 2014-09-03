package com.hecvd.fortuneflashlight.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class FlashlightHandler {

	private static final String TAG = FlashlightHandler.class.getSimpleName();

	private Camera camera = null;
	private Context context;

	public FlashlightHandler(Context context) {
		this.context = context;
		if(!this.context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
			Toast.makeText(this.context,"Torch flash not supported",Toast.LENGTH_LONG).show();
		}
	}

	private void getCamera() {
		if (camera == null) {
		   try {
		       camera = Camera.open();
		   } catch (RuntimeException e) {
		       Log.e(TAG, "Camera.open() failed: " + e.getMessage());
			   Toast.makeText(this.context,"Camera.open() failed: " + e.getMessage(),Toast.LENGTH_LONG).show();
		   }
		}
	}

	private void releaseCamera() {
		if (camera != null) {
			try {
				camera.release();
			} catch (RuntimeException e) {
				Log.e(TAG, "Camera.release() failed: " + e.getMessage());
				Toast.makeText(this.context,"Camera.release() failed: " + e.getMessage(),Toast.LENGTH_LONG).show();
			}
		}
		camera = null;
	}

	public void toggleLight() {
		getCamera();
		privateToggleLight();
	}

	private void privateToggleLight() {
		if (camera == null)
			return;
		Parameters parameters = camera.getParameters();
		List<String> flashModes = parameters.getSupportedFlashModes();
		if (flashModes == null) {
			Log.e(TAG, "parameters.getSupportedFlashModes() failed: null");
			Toast.makeText(this.context,"parameters.getSupportedFlashModes() failed: null",Toast.LENGTH_LONG).show();
			return;
		}
		String flashMode = parameters.getFlashMode();
//		Log.i(TAG, "Flash mode: " + flashMode);
//		Log.i(TAG, "Flash modes: " + flashModes);
		//Toast.makeText(this.context,"Flash modes: " + flashModes,Toast.LENGTH_LONG).show();
		if (Parameters.FLASH_MODE_OFF.equals(flashMode)){
			if (flashModes.contains(Parameters.FLASH_MODE_TORCH)) {
				parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
				camera.setParameters(parameters);
				camera.startPreview();
			}else{
				Toast.makeText(this.context,"FLASH_MODE_TORCH not supported",Toast.LENGTH_LONG).show();
				Log.e(TAG, "FLASH_MODE_TORCH not supported");
			}
		} else {
			if (flashModes.contains(Parameters.FLASH_MODE_OFF)) {
				parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
				camera.setParameters(parameters);
				camera.stopPreview();
				releaseCamera();
			}else{
				Log.e(TAG, "FLASH_MODE_OFF not supported");
				Toast.makeText(this.context,"FLASH_MODE_OFF not supported",Toast.LENGTH_LONG).show();
			}
		}
	}
}
