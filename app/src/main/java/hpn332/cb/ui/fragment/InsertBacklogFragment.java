package hpn332.cb.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import hpn332.cb.R;
import hpn332.cb.utils.Key;
import hpn332.cb.utils.Utils;
import hpn332.cb.utils.database.ProviderHelper;
import hpn332.library.view.ColorPanelView;
import hpn332.library.view.ColorPickerView;

public class InsertBacklogFragment extends Fragment {

	private static final String TAG = "InsertBacklogFragment";

	private EditText title, description;

	private ColorPickerView colorPicker;

	public static InsertBacklogFragment newInstance(int project_id) {
		Log.d(TAG, "newInstance: start");

		Log.d(TAG, "newInstance: project :: " + project_id);
		Bundle args = new Bundle();
		args.putInt(Key.KEY_PROJECT, project_id);
		InsertBacklogFragment fragment = new InsertBacklogFragment();
		fragment.setArguments(args);

		Log.d(TAG, "newInstance: end");
		return fragment;
	}

	@Override
	public View onCreateView(
			LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.d(TAG, "onCreateView: start");

		View view = inflater.inflate(R.layout.fragment_insert_backlog, container, false);

		init(view);

		Log.d(TAG, "onCreateView: end");

		return view;
	}

	private void init(View view) {

		Log.d(TAG, "init: start");

		title = view.findViewById(R.id.title_editText);
		description = view.findViewById(R.id.description_editText);

		colorPicker = view.findViewById(R.id.color_picker_view);

		view.findViewById(R.id.fab).setOnClickListener(
				view1 -> {

					ProviderHelper.insertNewBacklog(
							getContext(),
							getArguments().getInt(Key.KEY_PROJECT),
							title.getText().toString(),
							description.getText().toString(),
							colorPicker.getColor());

					getActivity().finish();
				});

		Utils.setupColorPicker(colorPicker,
		                       view.findViewById(R.id.color_panel_new),
		                       view.findViewById(R.id.color_panel_old),
		                       Utils.NO_COLOR);

		Log.d(TAG, "init: end");
	}

}
