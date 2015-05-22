package com.mabi87.privacyactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

// Broadcast Receiver for sensing screen offed
public class ScreenReceiver extends BroadcastReceiver {

	public static boolean SCREEN_OFFED = true;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
			SCREEN_OFFED = true;
		}
	}

}
