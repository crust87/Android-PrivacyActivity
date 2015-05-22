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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

// Password Change Activity
public class PasswordChangeActivity extends PrivacyActivity {
	// state enumeration to password change
	private static enum CHANGE_PROCESS{oldPassword, newPassword, passwordConfirm}; 
	
	protected Context mContext;
	private SharedPreferencesManager mSharedPreferencesManager;
	
	private View[] mPasswordDots;				// password dots view
	private View[] mNumbers;					// number pad view
	private View mBack;							// back button view
	private ArrayList<String> mPasswordArray;	// user input from number pad
	
	private TextView mNoticeTextView;			// view for message
	
	// Working variable
	private CHANGE_PROCESS currentProcess;
	private String mNewPassword;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_password_change);
	    
	    mContext = getApplicationContext();
	    mSharedPreferencesManager = new SharedPreferencesManager(mContext);
	    currentProcess = CHANGE_PROCESS.oldPassword;
	    loadGUI();
	} 
	
	private void loadGUI() {
		mPasswordDots = new View[4];
		mPasswordDots[0] = findViewById(R.id.password1);
		mPasswordDots[1] = findViewById(R.id.password2);
		mPasswordDots[2] = findViewById(R.id.password3);
		mPasswordDots[3] = findViewById(R.id.password4);
		mNumbers = new View[10];
		mNumbers[1] = findViewById(R.id.number1);
		mNumbers[2] = findViewById(R.id.number2);
		mNumbers[3] = findViewById(R.id.number3);
		mNumbers[4] = findViewById(R.id.number4);
		mNumbers[5] = findViewById(R.id.number5);
		mNumbers[6] = findViewById(R.id.number6);
		mNumbers[7] = findViewById(R.id.number7);
		mNumbers[8] = findViewById(R.id.number8);
		mNumbers[9] = findViewById(R.id.number9);
		mNumbers[0] = findViewById(R.id.number0);
		
		for (int i = 0; i < mNumbers.length; i++) {
			mNumbers[i].setOnClickListener(mNumberPadListener);
		}
		
		mBack = findViewById(R.id.backButton);
		mBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mPasswordArray.size() > 0) {
					mPasswordArray.remove(mPasswordArray.size() - 1);
				}

				checkPasswordCount();
			}
		});
		
		mPasswordArray = new ArrayList<String>();
		
		mNoticeTextView = (TextView) findViewById(R.id.noticeText);
	}
	
	// event listener for number pad
	private OnClickListener mNumberPadListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mPasswordArray.size() < 4) {
				LinearLayout ll = (LinearLayout) v;
				TextView tv = (TextView) ll.getChildAt(0);
				mPasswordArray.add(tv.getText().toString());
				checkPasswordCount();
			}
		}
	};
	
	private void checkPasswordCount() {
		for (View v : mPasswordDots) {
			v.setSelected(false);
		}
		
		// set message
		switch(currentProcess) {
		case oldPassword:
			mNoticeTextView.setText("Insert current password");
			break;
		case newPassword:
			mNoticeTextView.setText("Insert new password");
			break;
		case passwordConfirm:
			mNoticeTextView.setText("Repeat password");
			break;
		}

		for (int i = 0; i < mPasswordArray.size(); i++) {
			mPasswordDots[i].setSelected(true);
		}

		// if passwordArray size is 4, compare user input with password
		// if user input same as password, change state
		if (mPasswordArray.size() == 4) {
			if(currentProcess == CHANGE_PROCESS.oldPassword) {
				String lPassword = mPasswordArray.get(0) + mPasswordArray.get(1) + mPasswordArray.get(2) + mPasswordArray.get(3);
				
				if(mSharedPreferencesManager.getPassword().equals(lPassword)) {
					currentProcess = CHANGE_PROCESS.newPassword;
					mPasswordArray.clear();
					checkPasswordCount();
				} else {
					mPasswordArray.clear();
					checkPasswordCount();
				}
			} else if(currentProcess == CHANGE_PROCESS.newPassword) {
				mNewPassword = mPasswordArray.get(0) + mPasswordArray.get(1) + mPasswordArray.get(2) + mPasswordArray.get(3);
				mPasswordArray.clear();
				currentProcess = CHANGE_PROCESS.passwordConfirm;
				checkPasswordCount();
			} else if(currentProcess == CHANGE_PROCESS.passwordConfirm) {
				String lPassword = mPasswordArray.get(0) + mPasswordArray.get(1) + mPasswordArray.get(2) + mPasswordArray.get(3);
				
				if(mNewPassword.equals(lPassword)) {
					mSharedPreferencesManager.setPassword(mNewPassword);
					isBackFromHomeKey = false;
					finish();
				} else {
					currentProcess = CHANGE_PROCESS.newPassword;
					mPasswordArray.clear();
					checkPasswordCount();
				}
			}
			
		}
	}

}
