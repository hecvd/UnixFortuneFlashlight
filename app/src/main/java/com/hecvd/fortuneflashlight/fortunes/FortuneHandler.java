package com.hecvd.fortuneflashlight.fortunes;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
		BufferedReader reader;
		try {
			String[] files = this.context.getAssets().list(FORTUNE_REG_DIR_PATH);
			for(String file : files){
				if(!file.endsWith(".dat"))
					continue;
				String filePath = FORTUNE_REG_DIR_PATH+"/"+file;
				reader = new BufferedReader(new InputStreamReader(
						  this.context.getAssets().open(filePath)));
				StringBuilder sb = new StringBuilder();
				String aux = reader.readLine();
				while (aux != null){
					sb.append(aux);
					aux = reader.readLine();
				}

				this.fortuneFiles.addFortuneFile(filePath.substring(0,filePath.length()-4),
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
