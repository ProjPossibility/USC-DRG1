package com.example.distance_feedback;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button buttonOne = (Button) findViewById(R.id.button1);
		
		Vibrator myVib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		final DistanceFeedback df = new DistanceFeedback(myVib);
		buttonOne.setOnClickListener(new Button.OnClickListener() {
		    public void onClick(View v) {
		           EditText text = (EditText) findViewById(R.id.editText1);
		           if(df.feedback(Float.parseFloat(text.getText().toString()))){
		        	   Toast.makeText(MainActivity.this.getApplicationContext(), "You are there!", Toast.LENGTH_LONG).show();
		           } else {
		        	   Toast.makeText(MainActivity.this.getApplicationContext(), "Keep going", Toast.LENGTH_LONG).show();
		           }
		    }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
