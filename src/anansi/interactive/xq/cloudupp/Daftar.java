package anansi.interactive.xq.cloudupp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import anansi.interactive.xq.cloudupp.helper.AlertDialogManager;
import anansi.interactive.xq.cloudupp.helper.ConnectionDetector;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

public class Daftar extends Activity {

	Button daftar;
	EditText surel, sandi;
	CheckBox setuju;

	ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();

	String _surel, _sandi, pesan;
	Boolean _setuju = true;
	static String url = "http://my.cl.ly/register";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.daftar_act);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		surel = (EditText) findViewById(R.id.surelDaftar);
		sandi = (EditText) findViewById(R.id.sandiDaftar);
		setuju = (CheckBox) findViewById(R.id.checkBox1);
		daftar = (Button) findViewById(R.id.btnDaftar);

		setuju.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (setuju.isChecked()) {
					daftar.setEnabled(true);
				} else {
					daftar.setEnabled(false);
				}
			}
		});

		daftar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JsonObject jsonLuar = new JsonObject();
				JsonObject jsonDalam = new JsonObject();
				jsonDalam.addProperty("email", surel.getText().toString());
				jsonDalam.addProperty("password", sandi.getText().toString());
				jsonDalam.addProperty("accept_tos", _setuju);
				jsonLuar.add("user", jsonDalam);

				String jsonUser = jsonLuar.toString();
				if (surel.getText().length() > 0
						&& sandi.getText().length() > 0) {
					Daptarin daftarDong = new Daptarin();
					daftarDong.execute(jsonUser);
				} else {
					ab("Please fill the email address and password", "Wait");
				}

			}

		});
	}

	public class Daptarin extends AsyncTask<String, Void, Integer> {
		HttpResponse responHttp;
		int statusTCP;

		ProgressDialog pd = ProgressDialog.show(Daftar.this, "Wait ah",
				"Checking...", true);

		protected void onPreExecute(String pede) {
			cd = new ConnectionDetector(getApplicationContext());

			// Check for internet connection
			if (!cd.isConnectingToInternet()) {
				// Internet Connection is not present
				alert.showAlertDialogs(Daftar.this,
						"Internet Connection Error",
						"Please connect to working Internet connection");
				// stop executing code by return
				return;
			}

			pd.show();

		}

		@Override
		protected Integer doInBackground(String... jsonStringed) {
			// TODO Auto-generated method stub
			HttpPost httpPost = new HttpPost("http://my.cl.ly/items");
			HttpClient httpClient = new DefaultHttpClient();

			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			int TCP = 0;
			try {
				httpPost.setEntity(new StringEntity(jsonStringed[0], "UTF-8"));
				responHttp = httpClient.execute(httpPost);

				HttpEntity entity = responHttp.getEntity();
				String entitas = entity.toString();
				Log.e("entitasbalasan", "" + entitas);
				statusTCP = responHttp.getStatusLine().getStatusCode();
				TCP = statusTCP;
				Log.e("statusTCPnya", "" + statusTCP);
				Log.e("asynccccc", "" + jsonStringed[0]);

				switch (statusTCP) {
				case 201:
					Log.e("Case Status TCP", "201 succeed");
					break;
				case 302:
					Log.e("Case Status TCP", "401 already registered");
					break;
				case 422:
					Log.e("Case Status TCP", "422 email address invalid");
					break;
				default:
					Log.e("Case Status TCP", "unknown tcp error woy");
					break;
				}

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
			String ppoe = poe.toString();
			Log.e("postEksehh", "" + ppoe);
			pd.dismiss();
			if (poe == 201) {
				abwi("Completed", "Register succeed");
			}

		}

	}

	public void ab(String pesan, String judul) {
		new AlertDialog.Builder(this)
				.setTitle(judul)
				.setMessage(pesan)
				.setNeutralButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int whichButton) {
							}
						}).show();
	}

	public void abwi(String judul, String pesan) {
		new AlertDialog.Builder(this)
				.setTitle(judul)
				.setMessage(pesan)
				.setNeutralButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent i = new Intent(Daftar.this,
										SemuaBerkas.class);
								// i.putExtra("imel",surel.getText().toString());
								// i.putExtra("passwot",
								// sandi.getText().toString());
								startActivity(i);
								finish();
							};
						}).show();

	}

}
