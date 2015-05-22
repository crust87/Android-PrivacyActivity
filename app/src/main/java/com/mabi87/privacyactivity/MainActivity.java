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

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends PrivacyActivity {

    private LinearLayout mInformationLayout;
    private TextView mSecretView;
    private LinearLayout mChangePasswordItem;
    private LinearLayout mAblePasswordItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load layout
        setContentView(R.layout.activity_main);

        mSecretView = (TextView) findViewById(R.id.secretView);
        mChangePasswordItem = (LinearLayout) findViewById(R.id.changePasswordItem);
        mAblePasswordItem = (LinearLayout) findViewById(R.id.ablePasswordItem);

        if (mSharedPreferencesManager.hasPassword() && !PasswordActivity.PASSWORD_ACTIVITY_STARTED) {
            startActivity(new Intent(mContext, PasswordActivity.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check if password has set
        if (mSharedPreferencesManager.hasPassword()) {
            mSecretView.setText("ON");
            mSecretView.setTextColor(Color.parseColor("#ff0352"));
        } else {
            mSecretView.setText("OFF");
            mSecretView.setTextColor(Color.parseColor("#1254fc"));
        }

        // Check if password has set
        if (mSharedPreferencesManager.hasPassword()) {
            mChangePasswordItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Call PasswordChangeActivity
                    startActivity(new Intent(mContext, PasswordChangeActivity.class));
                }
            });
            mChangePasswordItem.setVisibility(View.VISIBLE);
        } else {
            // Remove view from layout
            mChangePasswordItem.setVisibility(View.GONE);
        }

        mAblePasswordItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Call PasswordStateActivity
                startActivity(new Intent(mContext, PasswordStateActivity.class));
            }
        });
    }
}
