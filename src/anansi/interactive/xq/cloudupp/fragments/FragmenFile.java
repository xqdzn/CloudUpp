package anansi.interactive.xq.cloudupp.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import anansi.interactive.xq.cloudupp.R;
import anansi.interactive.xq.cloudupp.helpah.AlertDialogManager;
import anansi.interactive.xq.cloudupp.helpah.ConnectionDetector;
import anansi.interactive.xq.cloudupp.helpah.CustomAdapter;
import anansi.interactive.xq.cloudupp.helpah.JSONParser;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class FragmenFile extends ListFragment {
	private static final String TAG_HREF = "href";
	private static final String TAG_NAME = "name";
	private static final String TAG_ITEM_TYPE = "item_type";
	ArrayList<HashMap<String, String>> itemsList = new ArrayList<HashMap<String, String>>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 LoadItems li = new LoadItems();
		 li.execute("my3m41l@gmail.com", "mys3cr3t");
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	private void bikinList(String daftarBerkas) {
		try {
			JSONArray items = new JSONArray(daftarBerkas);
			HashMap<String, String> map = null;
			if (items != null) {
				for (int i = 0; i < items.length(); i++) {
					JSONObject c = items.getJSONObject(i);
					String href = c.getString(TAG_HREF);
					String name = c.getString(TAG_NAME);
					String type = c.getString(TAG_ITEM_TYPE);
					map = new HashMap<String, String>();
					map.put(TAG_HREF, href);
					map.put(TAG_NAME, name);
					map.put(TAG_ITEM_TYPE, type);
					Log.e("type", "" + type);
					itemsList.add(map);
				}
			}
			String[] from = new String[] { TAG_HREF, TAG_NAME, TAG_ITEM_TYPE };
			int[] to = new int[] { R.id.item_href, R.id.item_name // ,
			// R.id.item_type
			};

			//SimpleAdapter adapter = new SimpleAdapter(getActivity(), itemsList,	R.layout.list_items, from, to);
			//setListAdapter(adapter);

			CustomAdapter customAdapter = new CustomAdapter(getActivity(), itemsList);
			setListAdapter(customAdapter);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			String ee = e.toString();
			Log.e("ee", "" + ee);
		}
	}

	public class LoadItems extends AsyncTask<String, String, String> {
		JSONArray items = null;
		ConnectionDetector cd;
		AlertDialogManager alert = new AlertDialogManager();
		JSONParser jsonParser = new JSONParser();
		ArrayList<HashMap<String, String>> itemsList;

		String daftarBerkas = null;

		private static final String URL = "http://my.cl.ly/items";

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		protected String doInBackground(String... args) {
			String json = jsonParser.mkeHttpRequest(URL, args[0], args[1]);
			return json;
		}

		@Override
		protected void onPostExecute(String json) {
			super.onPostExecute(json);
			bikinList(json);
		}
	}
}
