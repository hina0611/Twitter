package com.codepath.apps.restclienttemplate.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.codepath.oauth.OAuthLoginActivity;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {

	private Button btnTwitterLogin;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}


	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	private void initView(){
		btnTwitterLogin = (Button) findViewById(R.id.btn_twitter_login);
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
		 Intent i = new Intent(this, TimelineActivity.class);
		 startActivity(i);
//		Toast.makeText(LoginActivity.this, "Login Success" , Toast.LENGTH_LONG).show();
	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToRest(View view) {
		btnTwitterLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLoginSuccess();

			}
		});
		getClient().connect();
	}

}
