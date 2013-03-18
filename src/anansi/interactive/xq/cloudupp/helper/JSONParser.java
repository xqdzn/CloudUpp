package anansi.interactive.xq.cloudupp.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	// constructor
	public JSONParser() {

	}

	public String mkeHttpRequest(String url, String email, String password) {
		DefaultHttpClient hcl = new DefaultHttpClient();
		hcl.getCredentialsProvider().setCredentials(
				new AuthScope("my.cl.ly", 80),
				new UsernamePasswordCredentials(email, password));
		HttpGet hget = new HttpGet(url);
		hget.addHeader("Accept", "application/json");
		try {
			HttpResponse httpResponse = hcl.execute(hget);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e1) {
			Log.e("UnsupportedEncodingException", e1.toString());
		} catch (ClientProtocolException e2) {
			Log.e("ClientProtocolException", e2.toString());
		} catch (IllegalStateException e3) {
			Log.e("IllegalStateException", e3.toString());
		} catch (IOException e5) {
			Log.e("IOException", e5.toString());
			// } catch (JSONException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(is,
					"iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = bf.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// return JSON String
		return json;

	}
}