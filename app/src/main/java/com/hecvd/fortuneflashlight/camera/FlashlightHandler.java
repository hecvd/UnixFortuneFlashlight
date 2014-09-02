package com.hecvd.fortuneflashlight.camera;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;

import java.util.List;

public class FlashlightHandler {

	private static final String TAG = FlashlightHandler.class.getSimpleName();

	private Camera camera = null;
	private Context context;

	public FlashlightHandler(Context context) {
		this.context = context;
		getCamera();
	}

	private void getCamera() {
		if (camera == null) {
		   try {
		       camera = Camera.open();
		   } catch (RuntimeException e) {
		       Log.e(TAG, "Camera.open() failed: " + e.getMessage());
		   }
		}
	}

	public void toggleLight() {
		if (camera == null)
			return;
		Parameters parameters = camera.getParameters();
		if (parameters == null) {
			Log.e(TAG, "Camera.getParameters() failed: null");
			return;
		}
		List<String> flashModes = parameters.getSupportedFlashModes();
		if (flashModes == null) {
			Log.e(TAG, "parameters.getSupportedFlashModes() failed: null");
			return;
		}
		String flashMode = parameters.getFlashMode();
		Log.i(TAG, "Flash mode: " + flashMode);
		Log.i(TAG, "Flash modes: " + flashModes);
		if (Parameters.FLASH_MODE_OFF.equals(flashMode)){
			if (flashModes.contains(Parameters.FLASH_MODE_TORCH)) {
				parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
				camera.setParameters(parameters);
			}else{
				Log.e(TAG, "FLASH_MODE_TORCH not supported");
				return;
			}
		} else {
			if (flashModes.contains(Parameters.FLASH_MODE_OFF)) {
				parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
				camera.setParameters(parameters);
			}else{
				Log.e(TAG, "FLASH_MODE_OFF not supported");
				return;
			}
		}
	}
}
