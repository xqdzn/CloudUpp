package anansi.interactive.xq.cloudupp.helpah;

import java.util.ArrayList;
import java.util.HashMap;

import anansi.interactive.xq.cloudupp.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater layoutInflater;

	public CustomAdapter(Context context,
			ArrayList<HashMap<String, String>> data) {
		// this.context = context;
		this.data = data;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		String ee = data.toString();
		Log.e("ee", "" + ee);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		View vsOperations;
		ImageButton ibCompelete;
		ImageButton ibImportant;
		ImageButton ibNotify;
		ImageButton ibLove;
		ImageButton ibShare;
		TextView i_name;
		TextView i_href;
		RelativeLayout rlListItem;
		ImageView i_type;
	}

	public int getD(String tipe){
		Log.e("getD: tipe:", "" + tipe);
		int dd = 0;
		if (tipe.equals("audio")){
			dd = R.drawable.audio;
		} else if (tipe.equals("video")){
			dd = R.drawable.video;
		} else if (tipe.equals("text")){
			dd = R.drawable.text;
		} else if (tipe.equals("archive")){
			dd = R.drawable.archive;
		} else if (tipe.equals("unknown")){
			dd = R.drawable.unknown;
		} else if (tipe.equals("image")){
			dd = R.drawable.image;
		} else if (tipe.equals("bookmark")){
			dd = R.drawable.bookmark;
		} else if (tipe.equals("popular")){
			dd = R.drawable.popular;
		} else{
			dd = R.drawable.other;
		}
		return dd;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.list_items, null);
			viewHolder = new ViewHolder();

			TextView iName = (TextView) convertView.findViewById(R.id.item_name);
			TextView iHref = (TextView) convertView.findViewById(R.id.item_href);
			ImageView iType = (ImageView) convertView.findViewById(R.id.item_type);
			
			String tipe = data.get(position).get("item_type");
			
			int icon = getD(tipe);
			iType.setImageResource(icon);
			iName.setText(data.get(position).get("name"));
			iHref.setText(data.get(position).get("href"));
			
			viewHolder.vsOperations = (ViewStub) convertView.findViewById(R.id.vs_detail_operations);
			convertView.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.vsOperations.setVisibility(View.GONE);
		}
		return convertView;

	}
}