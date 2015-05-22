package com.mabi87.privacyactivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

// Password State Activity
// if Password has set, this activity for disable password
// else create new password
public class PasswordStateActivity extends PrivacyActivity {
	private static enum STATE_PROCESS{disable, create, createConfirm}; 
	
	protected Context mContext;
	private SharedPreferencesManager mSharedPreferencesManager;
	
	private View[] mPasswordDots;
	private View[] mNumbers;
	private View mBack;
	private ArrayList<String> mPasswordArray;
	
	private STATE_PROCESS currentProcess;
	private String mNewPassword;
	
	private TextView mNoticeTextView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_password_change);
	     
	    mContext = getApplicationContext();
	    mSharedPreferencesManager = new SharedPreferencesManager(mContext);
	    
	    // check password has set
	    if(mSharedPreferencesManager.hasPassword()) {
	    	currentProcess = STATE_PROCESS.disable;
	    } else { 
	    	currentProcess = STATE_PROCESS.create;
	    }
	    
	    mPasswordArray = new ArrayList<String>();
	    
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
		
		mNoticeTextView = (TextView) findViewById(R.id.noticeText);
		checkPasswordCount();
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
		case disable:
			mNoticeTextView.setText("Insert password");
			break;
		case create:
			mNoticeTextView.setText("Insert new password");
			break;
		case createConfirm:
			mNoticeTextView.setText("Repeat password");
			break;
		}

		for (int i = 0; i < mPasswordArray.size(); i++) {
			mPasswordDots[i].setSelected(true);
		}

		// if passwordArray size is 4, compare user input with password
		// if user input same as password, change state or finish
		if (mPasswordArray.size() == 4) {
			if(currentProcess == STATE_PROCESS.disable) {
				String lPassword = mPasswordArray.get(0) + mPasswordArray.get(1) + mPasswordArray.get(2) + mPasswordArray.get(3);
				
				if(mSharedPreferencesManager.getPassword().equals(lPassword)) {
					mSharedPreferencesManager.disablePassword();
					finish();
				} else {
					mPasswordArray.clear();
					checkPasswordCount();
				}
			} else if(currentProcess == STATE_PROCESS.create) {
				mNewPassword = mPasswordArray.get(0) + mPasswordArray.get(1) + mPasswordArray.get(2) + mPasswordArray.get(3);
				mPasswordArray.clear();
				currentProcess = STATE_PROCESS.createConfirm;
				checkPasswordCount();
			} else if(currentProcess == STATE_PROCESS.createConfirm) {
				String lPassword = mPasswordArray.get(0) + mPasswordArray.get(1) + mPasswordArray.get(2) + mPasswordArray.get(3);
				
				if(mNewPassword.equals(lPassword)) {
					mSharedPreferencesManager.setPassword(mNewPassword);
					isBackFromHomeKey = false;
					finish();
				} else {
					currentProcess = STATE_PROCESS.create;
					mPasswordArray.clear();
					checkPasswordCount();
				}
			}
			
		}
	}

}
