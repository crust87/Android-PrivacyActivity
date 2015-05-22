/*
 * PrivacyActivity
 * https://github.com/mabi87/Android-PrivacyActivity
 *
 * Mabi
 * crust87@gmail.com
 * last modify 2015-05-22
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
