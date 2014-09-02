package com.hecvd.fortuneflashlight;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.hecvd.fortuneflashlight.camera.FlashlightHandler;
import com.hecvd.fortuneflashlight.fortunes.FortuneHandler;


public class MainActivity extends Activity {

	FlashlightHandler flashlightHandler;
	FortuneHandler fortuneHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
	    flashlightHandler = new FlashlightHandler(this);
	    fortuneHandler = new FortuneHandler(this);
	    ((Switch)findViewById(R.id.swFlashlight)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    @Override
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			    flashlightHandler.toggleLight();
		    }
	    });

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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
