package de;
import android.os.Vibrator;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Vibrations extends Thread {
	Vibrator vib;
	Sensor sense;
	float angle;
	
	public Vibrations(Vibrator myvibrate,float ang) {
		// TODO Auto-generated constructor stub
		vib=myvibrate;
		angle=ang;
	}

	public void Compass()
	{
		//Timer abc=new Timer();
		float target=0;
		long pattern[]=new long[2];

	}

}
