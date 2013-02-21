package anansi.interactive.xq.cloudupp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import anansi.interactive.xq.cloudupp.fragments.AllFilesActivity;
import anansi.interactive.xq.cloudupp.helpah.AlertDialogManager;
import anansi.interactive.xq.cloudupp.helpah.ConnectionDetector;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class Masuk extends Activity {

	EditText sandi, surel;
	Button masuk, daftar, sekip;

	
	ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.masuk_act);

		surel = (EditText) findViewById(R.id.editText1);
		sandi = (EditText) findViewById(R.id.editText2);
		masuk = (Button) findViewById(R.id.button1);
		daftar = (Button) findViewById(R.id.button2);
		sekip = (Button) findViewById(R.id.button3);

		cd = new ConnectionDetector(getApplicationContext());
		 
        // Check for internet connection
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialogs(Masuk.this, "Internet Connection Error",
                    "Please connect to working Internet connection");
            // stop executing code by return
            return;
        }
		
		masuk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AsyncAuth ta = new AsyncAuth();
				ta.execute(surel.getText().toString(), sandi.getText()
						.toString());

			}
		});

		sekip.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Masuk.this, AllFilesActivity.class);
				startActivity(i);
				finish();

			}
		});

		daftar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//Intent i = new Intent(Masuk.this, Daftar.class);
				//startActivity(i);
				//finish();
			}
		});

	}

	private class AsyncAuth extends AsyncTask<String, Void, Integer> {
		ProgressDialog pd = ProgressDialog.show(Masuk.this, "Wait ah",
				"Checking...", true);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd.show();

		}

		@Override
		protected Integer doInBackground(String... arg0) {
			DefaultHttpClient hcl = new DefaultHttpClient();
			hcl.getCredentialsProvider().setCredentials(
					new AuthScope("my.cl.ly", 80),
					new UsernamePasswordCredentials(arg0[0], arg0[1]));
			HttpGet hget = new HttpGet("http://my.cl.ly/items");
			hget.addHeader("Accept", "application/json");
			HttpResponse rbd = null;
			int TCP = 0;
			try {
				rbd = hcl.execute(hget);
				TCP = rbd.getStatusLine().getStatusCode();
			} catch (UnsupportedEncodingException e1) {
				Log.e("UnsupportedEncodingException", e1.toString());
			} catch (ClientProtocolException e2) {
				Log.e("ClientProtocolException", e2.toString());
			} catch (IllegalStateException e3) {
				Log.e("IllegalStateException", e3.toString());
			} catch (IOException e4) {
				Log.e("IOException", e4.toString());
			}
			return TCP;
		}

		@Override
		protected void onPostExecute(Integer poe) {
			if (poe == 200) {
				Intent i = new Intent(Masuk.this, SemuaBerkas.class);
				i.putExtra("imel", surel.getText().toString());
				i.putExtra("passwot", sandi.getText()
						.toString());
				startActivity(i);
				finish();
				
			} else {
				 alert.showAlertDialogs(Masuk.this, "Oops",
		                    "Please check your password and email address.");
			}
			pd.dismiss();
		}

	}



}
