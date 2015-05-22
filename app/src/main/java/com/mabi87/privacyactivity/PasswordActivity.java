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
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

// PasswordActivity
// if Password has set, this Activity will call
public class PasswordActivity extends ActionBarActivity {
	public static boolean PASSWORD_ACTIVITY_STARTED; // if activity started
	
	protected Context mContext;
	private SharedPreferencesManager mSharedPreferencesManager;
	
	private View[] mPasswordDots;				// password dots view
	private View[] mNumbers;					// number pad view
	private View mBack;							// back button view
	private ArrayList<String> mPasswordArray;	// user input from number pad
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_password);
	    
	    mContext = getApplicationContext();
	    mSharedPreferencesManager = new SharedPreferencesManager(mContext);
	    PasswordActivity.PASSWORD_ACTIVITY_STARTED = true;
	    
	    loadGUI();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		PasswordActivity.PASSWORD_ACTIVITY_STARTED = false;
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

		for (int i = 0; i < mPasswordArray.size(); i++) {
			mPasswordDots[i].setSelected(true);
		}

		// if passwordArray size is 4, compare user input with password
		if (mPasswordArray.size() == 4) {
			String lPassword = mPasswordArray.get(0) + mPasswordArray.get(1) + mPasswordArray.get(2) + mPasswordArray.get(3);
			
			// if user input same sa password, activity finish
			if(mSharedPreferencesManager.getPassword().equals(lPassword)) {
				finish();
			} else {
				// else clear user input
				mPasswordArray.clear();
				checkPasswordCount();
			}
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// disable back key
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
