/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.reconing;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


/**
 * This Activity appears as a dialog. It lists any paired devices and
 * devices detected in the area after discovery. When a device is chosen
 * by the user, the MAC address of the device is sent back to the parent
 * Activity in the result Intent.
 */
public class MainActivity extends Activity {
    // Debugging
    private static final String TAG = "MainActivity";
    private static final boolean D = true;
    
    //distance feedback
	DistanceFeedback df;
	Vibrator myVib;

    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    // Member fields
    private BluetoothAdapter mBtAdapter;
//    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    
    String oldRSSIValue="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //distance feedback 
		myVib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		df = new DistanceFeedback(myVib);

        // Setup the window
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.device_list);

        // Set result CANCELED in case the user backs out
        setResult(Activity.RESULT_CANCELED);

        // Initialize the button to perform device discovery
        Button scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                doDiscovery();
                v.setVisibility(View.GONE);
            }
        });

        // Initialize array adapters. One for already paired devices and
        // one for newly discovered devices
//        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

        // Find and set up the ListView for paired devices
//        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
//        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
//        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // Find and set up the ListView for newly discovered devices
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        //newDevicesListView.setOnItemClickListener(mDeviceClickListener);

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }
        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {
        if (D) Log.d(TAG, "doDiscovery()");

        // Indicate scanning in the title
        setProgressBarIndeterminateVisibility(true);
        setTitle(R.string.scanning);

        // Turn on sub-title for new devices
        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

        // If we're already discovering, stop it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
    }

    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	

            String action = intent.getAction();
            
            
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
            	

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                String EXTRA_DEVICE = "android.bluetooth.device.extra.DEVICE";
//                intent.putExtra(EXTRA_DEVICE, device);
                String deviceRSSI = (intent.getExtras()).get(BluetoothDevice.EXTRA_RSSI).toString();
                
                
                
                // If it's already paired, skip it, because it's been listed already
//                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
//                    	//mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
//
//                }
//                mNewDevicesArrayAdapter.add(device.getName() + " Old: " + oldRSSIValue + " New: " + deviceRSSI + "\n");

                if(device.getName().equalsIgnoreCase("kanika") && device.getAddress().equalsIgnoreCase("74:45:8A:B8:B5:76")) //kanika's mac address
                //if(device.getName().equalsIgnoreCase("swapnil") && device.getAddress().equalsIgnoreCase("C8:19:F7:16:A6:DB")) //swapnil's mac address
                {
                	//mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress()+ "\n" + deviceRSSI);
                	myVib.cancel();	
                	

                	
                	mNewDevicesArrayAdapter.add("Kanika: Old: " + oldRSSIValue + " New: " + deviceRSSI + "\n");
                	if(df.feedback(Integer.parseInt(deviceRSSI)))
                	{
                		//you are done exit app
                		myVib.cancel();	  
                		return;
                	}
                	float oldVal = Float.parseFloat(oldRSSIValue);
                	float newVal = Float.parseFloat(deviceRSSI);
                	
                	oldVal *= -1;
                	newVal *= -1;
                	
                	if(oldVal > newVal) //going closer
                	{
                    	//play beep when rssi changed
                    	Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    	Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    	r.play();
                	}
                	else //going away
                	{
                    	//play beep when rssi changed
                    	Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                    	Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    	r.play();
                	}
                	oldRSSIValue = deviceRSSI; 
                	doDiscovery();
                }
//                if(device.getAddress().equalsIgnoreCase("74:45:8A:B8:B5:76")) //kanika's mac address
//                {
//                	Toast.makeText(getApplicationContext(), "Kanika: "+deviceRSSI, Toast.LENGTH_SHORT).show();
//                }
            // When discovery is finished, change the Activity title
            } 
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) 
            {
                setProgressBarIndeterminateVisibility(false);
                setTitle(R.string.select_device);
                if (mNewDevicesArrayAdapter.getCount() == 0) {
                    //String noDevices = getResources().getText(R.string.none_found).toString();
                    //mNewDevicesArrayAdapter.add(noDevices);
                	mNewDevicesArrayAdapter.clear();
                }
                doDiscovery();
            }
        }
    };

    
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
    		prev = RANGES[RANGES.length-1];
    	}
    	private void checkPrevAndVibrate(int rangeIndex){
//    		if(prev >= RANGES[rangeIndex]){
//    			myvib.vibrate(300);
//    		}
    		myvib.vibrate(300);
    		myvib.vibrate(VIBS[rangeIndex],0);
    		String display = String.valueOf(VIBS[rangeIndex][0]);
    		display += " ,";
    		display += String.valueOf(VIBS[rangeIndex][1]);
    		Toast.makeText(getApplicationContext(), display, Toast.LENGTH_SHORT);
    	}
    	public boolean feedback(float distance){
    		distance = distance*-1;
    		for(int i = 0; i<NUM_RANGE; i++){
    			if(distance<RANGES[i]){
    				checkPrevAndVibrate(i);
    				if(i == 0){
    					return true;
    				}
    				break;
    			}
    		}
    		return false;
    	}
    	
    }    
    
}
