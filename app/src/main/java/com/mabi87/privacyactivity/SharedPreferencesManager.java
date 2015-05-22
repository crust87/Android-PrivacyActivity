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
