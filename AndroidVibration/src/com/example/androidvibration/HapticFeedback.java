package com.example.androidvibration;

import android.os.Vibrator;

public class HapticFeedback {
	
	/**
	 * Makes the vibrator vibrate for a certain duration separated by a certain gap
	 * @param vibrator the vibrator object
	 * @param gap in ms
	 * @param duration in ms
	 */
	public static void makeVibrate(Vibrator vibrator, long gap, long duration){
		vibrator.vibrate(new long[]{gap,duration}, 0);
	}
	
	public static boolean orientDirection(float inputAngle, float desiredAngle){
		double diffAngle = Math.pow(inputAngle - desiredAngle,2);
		if(diffAngle <5){
			
		} else if(diffAngle < 90){
			
		} else {
			
		}
		
		return false;
	}
	
	
}
