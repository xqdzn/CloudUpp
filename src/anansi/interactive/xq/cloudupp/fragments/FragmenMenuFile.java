package anansi.interactive.xq.cloudupp.fragments;

import anansi.interactive.xq.cloudupp.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmenMenuFile extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] kat_berkas = getResources().getStringArray(
				R.array.kategori_berkas);
		ArrayAdapter<String> adapterKategoriBerkas = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_1,
				android.R.id.text1, kat_berkas);
		setListAdapter(adapterKategoriBerkas);

	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		switch (position) {
		case 0:
			// newContent = new ColorFragment(R.color.red);
			break;
		case 1:
			// newContent = new ColorFragment(R.color.green);
			break;
		case 2:
			// newContent = new ColorFragment(R.color.blue);
			break;
		case 3:
			// newContent = new ColorFragment(android.R.color.white);
			break;
		case 4:
			// newContent = new ColorFragment(android.R.color.black);
			break;
		}
		if (newContent != null)
			switchFragment(newContent);
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof AllFilesActivity) {
			AllFilesActivity fca = (AllFilesActivity) getActivity();
			fca.switchContent(fragment);
		} else {
		}
	}

}
