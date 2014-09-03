package com.hecvd.fortuneflashlight.fortunes;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by hecvd on 2/09/14.
 */
public class FortuneHandler {

	private static final String TAG = FortuneHandler.class.getSimpleName();
	private static final String FORTUNE_REG_DIR_PATH = "fortunes-reg";
	private Context context;
	FortuneFiles fortuneFiles;

	public FortuneHandler(Context context) {
		this.context = context;
		fortuneFiles = new FortuneFiles(context);
		loadFortuneIndexes();
	}

	private void loadFortuneIndexes() {
		BufferedReader reader = null;
		try {
			String[] files = this.context.getAssets().list(FORTUNE_REG_DIR_PATH);
			for(String file : files){
				if(!file.endsWith(".dat"))
					continue;
				String filepath = FORTUNE_REG_DIR_PATH+"/"+file;
				reader = new BufferedReader(new InputStreamReader(
						  this.context.getAssets().open(filepath)));
				StringBuilder sb = new StringBuilder();
				String aux = reader.readLine();
				while (aux != null){
					sb.append(aux);
					aux = reader.readLine();
				}

				this.fortuneFiles.addFortuneFile(filepath.substring(0,filepath.length()-4),
						  new JSONObject(sb.toString()));
			}
		} catch (IOException e) {
			Log.e(TAG,e.getMessage());
		} catch (JSONException e) {
			Log.e(TAG,e.getMessage());
		}
	}

	public String getRandomFortune(){
		return fortuneFiles.getRandomFortune();
	}
}
