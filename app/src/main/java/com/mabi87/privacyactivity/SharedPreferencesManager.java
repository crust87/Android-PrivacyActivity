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

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesManager {
	// Components;
	private Context mContext;
	
	private SharedPreferences mSharedPreferences;
	private Editor mEditer;
	
	public SharedPreferencesManager(Context pContext) {
		mContext = pContext;
		
		// change your SharedPreferences name!!
		mSharedPreferences = mContext.getSharedPreferences("privacyactivity", Context.MODE_PRIVATE);
		mEditer = mSharedPreferences.edit();
	}
	
	public boolean hasPassword() {
		return mSharedPreferences.getBoolean("hasPassword", false);
	}
	
	public void disablePassword() {
		mEditer.putBoolean("hasPassword", false).putString("password", "none");
		mEditer.commit();
	}
	
	public void setPassword(String pPassword) {
		mEditer.putString("password", pPassword).putBoolean("hasPassword", true);
		mEditer.commit();
	}
	
	public String getPassword() {
		return mSharedPreferences.getString("password", "none");
	}
	
	public void putString(String pKey, String pValue) {
		mEditer.putString(pKey, pValue);
		mEditer.commit();
	}
	
	public void putInt(String pKey, int pValue) {
		mEditer.putInt(pKey, pValue);
		mEditer.commit();
	}
	
	public void putBoolean(String pKey, boolean pValue) {
		mEditer.putBoolean(pKey, pValue);
		mEditer.commit();
	}
	
	public String getString(String pKey) {
		return mSharedPreferences.getString(pKey, null);
	}
	
	public int getInt(String pKey) {
		return mSharedPreferences.getInt(pKey, -1);
	}
	
	public boolean getBoolean(String pKey) {
		return mSharedPreferences.getBoolean(pKey, false);
	}
}
