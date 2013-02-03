package com.example.distance_feedback;

import android.os.Vibrator;

public class DistanceFeedback {
	Vibrator myvib;
	float prev;
	// IS SIGNAL STRENGTH < -1*THIS VALUE
	long[] RANGES = {70,72,74,77,80,83,86,89,92,95,999};
	// THE VIBRATION PARAMS CORRESPONDING TO EACH SIGNAL STRENGTH RANGE
	long[][] VIBS = {{1,150},{50,150},{150,150},{250,150},{400,150},{600,150},{750,250},{950,325},{1150,400},{1400,500},{1700,700},{3000,800}};
	// THE NUMBER OF UNIQUE SETTINGS SPECIFIED BY RANGES
	int NUM_RANGE = RANGES.length;
	
	public DistanceFeedback(Vibrator vib){
		myvib = vib;
		prev = RANGES[(RANGES.length-1)+1];
	}
	private void checkPrevAndVibrate(int rangeIndex){
		if(prev >= RANGES[rangeIndex]){
			myvib.vibrate(300);
		}
		myvib.vibrate(VIBS[rangeIndex],0);
	}
	public boolean feedback(float distance){
		distance = distance*-1;
		for(int i = 0; i<NUM_RANGE; i++){
			if(distance<RANGES[i]){
				checkPrevAndVibrate(i);
				if(i == 0){
					return true;
				}
			}
		}
		return false;
	}
	
}
