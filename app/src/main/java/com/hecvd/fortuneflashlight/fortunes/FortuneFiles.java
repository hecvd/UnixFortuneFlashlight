package com.hecvd.fortuneflashlight.fortunes;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


public class FortuneFiles {

	private static final String TAG = FortuneFiles.class.getSimpleName();

	Context context;
	Random rn = new Random();
	ArrayList<FortuneFile> files = new ArrayList<FortuneFile>(50);

	public FortuneFiles(Context context) {
		this.context = context;
	}

	public void addFortuneFile(String file, JSONObject reader) throws JSONException {
		FortuneFile fortuneFile = new FortuneFile();
		fortuneFile.setFortuneFile(file);
		fortuneFile.setFortuneCount(reader.getInt("fortune_count"));
		fortuneFile.setLongestFortune(reader.getInt("longest"));
		fortuneFile.setShortestFortune(reader.getInt("shortest"));
		JSONArray fortuneArray = reader.getJSONArray("fortunes");
		ArrayList<int[]> indexes = new ArrayList<int[]>(fortuneArray.length());
		for (int i = 0; i < fortuneArray.length(); i++) {
			JSONArray indexArray = fortuneArray.getJSONArray(i);
			indexes.add(new int[]{indexArray.getInt(0), indexArray.getInt(1)});
		}
		fortuneFile.setIndexes(indexes);
		files.add(fortuneFile);
	}

	public String getRandomFortune(){
		int max = this.files.size()-1, min=0;
		FortuneFile fl = files.get(rn.nextInt(max - min + 1) + min);
//		for(FortuneFile f : files){
//			if(f.getFortuneFile().contains("linux"))
//			fl = f;
//		}
		max = fl.getIndexes().size()-1;
		return fl.getFortune(this.context, rn.nextInt(max - min + 1) + min);
	}

	private static class FortuneFile {
		String fortuneFile;
		int fortuneCount = 0;
		int longestFortune = 0;
		int shortestFortune = 0;
		ArrayList<int[]> indexes;

		public FortuneFile() {

		}

//		public FortuneFile(String fortuneFile, int fortuneCount, int shortestFortune, int longestFortune, ArrayList<int[]> indexes) {
//			this.fortuneFile = fortuneFile;
//			this.fortuneCount = fortuneCount;
//			this.longestFortune = longestFortune;
//			this.shortestFortune = shortestFortune;
//			this.indexes.addAll(indexes);
//		}

		public void setFortuneFile(String file) {
			this.fortuneFile = file;
		}

//		public String getFortuneFile() {
//			return fortuneFile;
//		}

		public void setFortuneCount(int fortuneCount) {
			this.fortuneCount = fortuneCount;
		}

		public void setLongestFortune(int longestFortune) {
			this.longestFortune = longestFortune;
		}

		public void setShortestFortune(int shortestFortune) {
			this.shortestFortune = shortestFortune;
		}

		public void setIndexes(ArrayList<int[]> indexes) {
			this.indexes = indexes;
		}

		public ArrayList<int[]> getIndexes() {
			return indexes;
		}

		public String getFortune(Context context, int index) {
			String fortune = "";
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						  context.getAssets().open(this.fortuneFile)));
				int fortuneIndex[] = indexes.get(index);
				Log.e(TAG,fortuneIndex[0]+ ", "+fortuneIndex[1]);
				reader.skip(fortuneIndex[0]);
				char buffer[] = new char[fortuneIndex[1]];
				reader.read(buffer,0,fortuneIndex[1]);
				fortune = new String(buffer);
				Log.e(TAG,fortune);
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
			}
			return fortune;
		}
	}
}