package anansi.interactive.xq.cloudupp;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import anansi.interactive.xq.cloudupp.helpah.AlertDialogManager;
import anansi.interactive.xq.cloudupp.helpah.ConnectionDetector;
import anansi.interactive.xq.cloudupp.helpah.JSONParser;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class SemuaBerkas extends ListActivity {
JSONArray items = null;
	
	// Connection detector
	ConnectionDetector cd;
		
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
		
	// Progress Dialog
	private ProgressDialog pd;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	ArrayList<HashMap<String, String>> itemsList;

	// items JSON url
	private static final String URL = "http://my.cl.ly/items";

	// ALL JSON node names
	private static final String TAG_HREF = "href";
	private static final String TAG_NAME = "name";
	private static final String TAG_ITEM_TYPE = "item_type";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.semua_berkas_act);
		
		cd = new ConnectionDetector(getApplicationContext());
		 
        // Check for internet connection
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialogs(SemuaBerkas.this, "Internet Connection Error", "Please connect to working Internet connection");
            // stop executing code by return
            return;
        }

		// Hashmap for ListView
		itemsList = new ArrayList<HashMap<String, String>>();

		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String surel = extras.getString("imel");
			String sandi = extras.getString("passwot");
			LoadItems la = new LoadItems();
			la.execute(surel, sandi);
		}

		ListView lv = getListView();
		
		lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// on selecting a single album
				// TrackListActivity will be launched to show tracks inside the album
				//Intent i = new Intent(getApplicationContext(), TrackListActivity.class);
				//Toast.makeText(SemuaBerkas.this, "yup", Toast.LENGTH_LONG).show();
				// send album id to tracklist activity to get list of songs under that album
				//String album_id = ((TextView) view.findViewById(R.id.album_id)).getText().toString();
				//i.putExtra("album_id", album_id);				
				
				//startActivity(i);
			}
		});		
		
	}

	
	class LoadItems extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(SemuaBerkas.this);
			pd.setMessage("Grabbing items...");
			pd.show();
		}

		protected String doInBackground(String... args) {
			String json = jsonParser.mkeHttpRequest(URL, args[0], args[1]);
			// Check your log cat for JSON reponse
			return json;
		}
	
		@Override
		protected void onPostExecute(String json) {
			// TODO Auto-generated method stub
			super.onPostExecute(json);
			try {				
				items = new JSONArray(json);
				HashMap<String, String> map = null;
				if (items != null) {
					// looping through All albums
					for (int i = 0; i < items.length(); i++) {
						JSONObject c = items.getJSONObject(i);
						// Storing each json item values in variable
						String href = c.getString(TAG_HREF);
						String name = c.getString(TAG_NAME);
						String type = c.getString(TAG_ITEM_TYPE);
						// creating new HashMap
						 map = new HashMap<String, String>();
						// adding each child node to HashMap key => value
						map.put(TAG_HREF, href);
						map.put(TAG_NAME, name);
						map.put(TAG_ITEM_TYPE, type);
						// adding HashList to ArrayList
						itemsList.add(map);
					} 
				}else{
					
				}
				// updating UI from Background Thread
				runOnUiThread(new Runnable() {
					public void run() {
						/**
						 * Updating parsed JSON data into ListView
						 * */
						ListAdapter adapter = new SimpleAdapter(
								SemuaBerkas.this, itemsList,
								R.layout.list_items, new String[] { TAG_HREF,
										TAG_NAME, TAG_ITEM_TYPE }, new int[] {
										R.id.item_href, R.id.item_name});
						
						// updating listview
						setListAdapter(adapter);
					}
				});
				pd.dismiss();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
	}
/**	
	class CustomViewBinder extends SimplerAdapter.ViewBinder
	{
	            @Override
	    public boolean setViewValue(View view, Object data,
	            String textRepresentation) {
	        int id=view.getId();
	            String country=(String)data; 
	                switch(id)
	                {
	                  case R.id.country:
	                            if(country.equals("us")
	                                setYourImage();
	                   .....

	            }
	        }
	}
**/

}
