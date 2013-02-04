package de;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static SensorManager sensorService;
	private MyCompassView compassView;
	private Sensor sensor;
	Object myvib;
	Vibrator myvibrate;
	Thread vibrateClass;
	float record;
	boolean fisrt_flag=false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		compassView = new MyCompassView(this);
		
		setContentView(compassView);


		sensorService = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		if (sensor != null) {
			//sensorService.registerListener(mySensorEventListener, sensor,
					//SensorManager.SENSOR_DELAY_NORMAL);
			sensorService.registerListener(mySensorEventListener, sensor, 300000000);
			
			Log.i("Compass MainActivity", "Registerered for ORIENTATION Sensor");
			

		} else {
			Log.e("Compass MainActivity", "Registerered for ORIENTATION Sensor");
			Toast.makeText(this, "ORIENTATION Sensor not found",
					Toast.LENGTH_LONG).show();
			finish();
		}
	}

	private SensorEventListener mySensorEventListener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			// angle between the magnetic north direction
			// 0=North, 90=East, 180=South, 270=West
			float azimuth = event.values[0];
			//float target=0;
			compassView.updateData(azimuth);
			myvib=getSystemService(VIBRATOR_SERVICE);
			myvibrate=(Vibrator) myvib;
			long[] pattern=new long[2];
			float target=0,angle=azimuth;
			if (fisrt_flag==false)
			{
				if(Math.abs(target-angle)<15 || Math.abs(360-angle)<15)
				{
					
					myvibrate.vibrate(100);
				}
				else if(Math.abs(target-angle)<30 || Math.abs(360-angle)<30)
				{
					pattern[0]=50;
					pattern[1]=150;
					myvibrate.vibrate(pattern, 0);
				}
				else if(Math.abs(target-angle)<60 || Math.abs(360-angle)<60)
				{
					pattern[0]=750;
					pattern[1]=250;
					myvibrate.vibrate(pattern, 0);			
				}
				else if(Math.abs(target-angle)<120 || Math.abs(360-angle)<120)
				{
					pattern[0]=1150;
					pattern[1]=400;
					myvibrate.vibrate(pattern, 0);
				}
				else
				{
					pattern[0]=1500;
					pattern[1]=700;
					myvibrate.vibrate(pattern, 0);
				}
				record=azimuth;
				fisrt_flag=true;
			}
			else
			{
				if(Math.abs(azimuth-record)>30)
				{
					record=azimuth;
					if(Math.abs(target-angle)<15 || Math.abs(360-angle)<15)
					{
						
						myvibrate.vibrate(100);
					}
					else if(Math.abs(target-angle)<30 || Math.abs(360-angle)<30)
					{
						pattern[0]=50;
						pattern[1]=150;
						myvibrate.vibrate(pattern, 0);
					}
					else if(Math.abs(target-angle)<60 || Math.abs(360-angle)<60)
					{
						pattern[0]=750;
						pattern[1]=250;
						myvibrate.vibrate(pattern, 0);			
					}
					else if(Math.abs(target-angle)<120 || Math.abs(360-angle)<120)
					{
						pattern[0]=1150;
						pattern[1]=400;
						myvibrate.vibrate(pattern, 0);
					}
					else
					{
						pattern[0]=1500;
						pattern[1]=700;
						myvibrate.vibrate(pattern, 0);
					}
					
				}
			}
			
			//myvibrate.vibrate(300);
			
			//vibrateClass=new Vibrations(myvibrate,azimuth);			
			//{
				//vibrateClass.start();
				
			//}
				
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (sensor != null) {
			sensorService.unregisterListener(mySensorEventListener);
		}
	}

}