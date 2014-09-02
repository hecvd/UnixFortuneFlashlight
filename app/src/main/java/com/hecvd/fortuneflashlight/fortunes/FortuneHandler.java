package com.hecvd.fortuneflashlight.fortunes;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

/**
 * Created by hecvd on 2/09/14.
 */
public class FortuneHandler {

	private static final String TAG = FortuneHandler.class.getSimpleName();
	private Context context;

	public FortuneHandler(Context context) {
		this.context = context;
		try {
			String reg_fortunes[] = this.context.getAssets().list("fortunes-reg/");
			Log.i(TAG,reg_fortunes.length+"");
			for(String str : reg_fortunes){
				Log.i(TAG,str);
			}
		} catch (IOException e) {
			Log.e(TAG, "this.context.getAssets().list(\"fortunes-reg\") " + e.getMessage());
		}
	}
}
