#BlueTooth Dead-Reckoning App

This repository contains the source code for the Dead Reckoning Bluetooth app

## Building

You will need the [Android SDK](http://developer.android.com/sdk/index.html)
to be installed in your development environment.

After satisfying those requirements, imp

You might find that your device doesn't let you install your build if you
already have the version from the Android Market installed.  This is standard
Android security as it it won't let you directly replace an app that's been
signed with a different key.  Manually uninstall GitHub from your device and
you will then be able to install your own built version.

See [here](https://github.com/github/android/wiki/Building-From-Eclipse) for
instructions on building from [Eclipse](http://eclipse.org).

## Code
The only class is Main Activity which is responsible for looping through creating bluetooth connection, finding the RSSI (bluetooth signal strength) 
and then disconnecting. Everytime a bluetooth connection is made the RSSI is first compared to the previous value and then either a "whistle sound" (getting closer)
or a "car click" sound is made (getting farther). Then the RSSI is fed to the DistanceFeedback inner class which creates a vibration pattern which is proportional to the
RSSI value. This allows the user to get an absolute signal as to their distance from the other user. If the RSSI is lower than 70 (which corresponds to around 1 m)
the application stops.

There is also a branch containing the compass branch. There is a separate main activity class which unfortunately was unable to be 
incorporated into the main code. This code allows for haptic feedback of angle orientation.


## Acknowledgements
We utilized some resources to implement the bluetooth chat