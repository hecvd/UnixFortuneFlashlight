package com.hecvd.fortuneflashlight;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.hecvd.fortuneflashlight.camera.FlashlightHandler;
import com.hecvd.fortuneflashlight.fortunes.FortuneHandler;


public class MainActivity extends Activity {

	FlashlightHandler flashlightHandler;
	FortuneHandler fortuneHandler;
	TextSwitcher tvFortuneDisplay = null;

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_main);
		 flashlightHandler = new FlashlightHandler(this);
		 fortuneHandler = new FortuneHandler(this);
		 tvFortuneDisplay = ((TextSwitcher)findViewById(R.id.tvFortuneDisp));
		 tvFortuneDisplay.setFactory(new ViewSwitcher.ViewFactory() {
			 @Override
			 public View makeView() {
//				 <TextView
//				 android:layout_width="fill_parent"
//				 android:layout_height="wrap_content"
//				 android:gravity="top"
//				 android:singleLine="false"
//				 android:textAppearance="?android:attr/textAppearanceMedium"
//				 android:textColor="#fff"
//				 android:textIsSelectable="true"
//						   />
				 TextView tv = new TextView(MainActivity.this);
				 tv.setSingleLine(false);
				 tv.setTextAppearance(MainActivity.this, android.R.style.TextAppearance_Medium);
				 tv.setTextColor(Color.WHITE);
				 tv.setGravity(Gravity.TOP);
				 return tv;
			 }
		 });
		 Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
		 //in.setInterpolator(AnimationUtils.loadInterpolator(MainActivity.this,android.R.anim.bounce_interpolator));
		 Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
		 //in.setStartOffset(out.getDuration());
		 tvFortuneDisplay.setInAnimation(in);
		 tvFortuneDisplay.setOutAnimation(out);
		 ((Switch)findViewById(R.id.swFlashlight)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			 @Override
			 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				 flashlightHandler.toggleLight();
			 }
		 });
		 findViewById(R.id.btnNewFortune).setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 tvFortuneDisplay.setText(fortuneHandler.getRandomFortune());
			 }
		 });
		 tvFortuneDisplay.setText(fortuneHandler.getRandomFortune());
	 }

	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
	 }

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		 return id == R.id.action_settings || super.onOptionsItemSelected(item);
	 }
}
