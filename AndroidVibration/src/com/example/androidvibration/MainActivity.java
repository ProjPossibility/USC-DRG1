package com.example.androidvibration;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Vibrator;

public class MainActivity extends Activity {

	 long[] pttrn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Object myvib=getSystemService(VIBRATOR_SERVICE);
		final Vibrator myvibrate=(Vibrator) myvib;
		final Button button=(Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				pttrn=new long [3];
				EditText text1=(EditText) findViewById(R.id.editText1);
				EditText text2=(EditText) findViewById(R.id.editText2);
				pttrn[0]=Long.parseLong(text1.getText().toString());
				pttrn[1]=Long.parseLong(text2.getText().toString());
				myvibrate.vibrate(pttrn, 0);
				myvibrate.vibrate(300);
				//System.out.println(flag);
				
				
			}
		});
		
		final Button button2=(Button) findViewById(R.id.button2);
		button2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myvibrate.cancel();
				
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
