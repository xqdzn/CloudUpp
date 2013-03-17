package anansi.interactive.xq.cloudupp.helpah;

import java.util.ArrayList;
import java.util.HashMap;

import anansi.interactive.xq.cloudupp.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
		RelativeLayout rlListItem;
		ImageButton ibCompelete;
		ImageButton ibImportant;
		ImageButton ibNotify;
		ImageButton ibLove;
		ImageButton ibShare;
		TextView i_name;
		TextView i_href;
		ImageView i_type;
		TextView i_url;
		TextView i_viewc;
	}

	public int getD(String tipe) {
		Log.e("getD: tipe:", "" + tipe);
		int dd = 0;
		if (tipe.equals("audio")) {
			dd = R.drawable.audio;
		} else if (tipe.equals("video")) {
			dd = R.drawable.video;
		} else if (tipe.equals("text")) {
			dd = R.drawable.text;
		} else if (tipe.equals("archive")) {
			dd = R.drawable.archive;
		} else if (tipe.equals("unknown")) {
			dd = R.drawable.unknown;
		} else if (tipe.equals("image")) {
			dd = R.drawable.image;
		} else if (tipe.equals("bookmark")) {
			dd = R.drawable.bookmark;
		} else if (tipe.equals("popular")) {
			dd = R.drawable.popular;
		} else {
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
			viewHolder.rlListItem = (RelativeLayout) convertView
					.findViewById(R.id.rl_list);

			TextView iName = (TextView) convertView
					.findViewById(R.id.item_name);
			TextView iHref = (TextView) convertView
					.findViewById(R.id.item_href);
			ImageView iType = (ImageView) convertView
					.findViewById(R.id.item_type);
			TextView iUrl = (TextView) convertView.findViewById(R.id.item_url);
			TextView iViewc = (TextView) convertView
					.findViewById(R.id.item_view_count);

			String tipe = data.get(position).get("item_type");
			String viewc = data.get(position).get("view_counter");

			int icon = getD(tipe);
			iType.setImageResource(icon);
			iName.setText(data.get(position).get("name"));
			iHref.setText(data.get(position).get("href"));
			iUrl.setText(data.get(position).get("url"));
			iViewc.setText(viewc);

			viewHolder.vsOperations = (ViewStub) convertView
					.findViewById(R.id.vs_detail_operations);
			convertView.setTag(viewHolder);
			viewHolder.ibCompelete = (ImageButton) convertView
					.findViewById(R.id.btn_compelete);
		} else {

			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.vsOperations.setVisibility(View.GONE);
		}

		try {

			viewHolder.ibCompelete.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					Log.e("btnComplete", "eeeeeeeeeeeeeeee");
					return false;

				}

			});
		} catch (Exception e) {
			Log.e("eeeeeeeeeeeee", "" + e);
		}
		viewHolder.rlListItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (viewHolder.vsOperations.getVisibility() == View.GONE) {

					if (viewHolder.vsOperations instanceof ViewStub) {

						viewHolder.vsOperations = ((ViewStub) viewHolder.vsOperations)
								.inflate();
					}
					viewHolder.vsOperations.setVisibility(View.VISIBLE);

				} else {

					viewHolder.vsOperations.setVisibility(View.GONE);
				}
			}
		});

		return convertView;

	}
}