package hpn332.cb.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import hpn332.cb.R;
import hpn332.cb.utils.Utils;
import hpn332.cb.model.database.ProviderHelper;
import hpn332.library.view.ColorPickerView;

public class AddFragmentTag extends Fragment {

	private static final String TAG = "AddFragmentTag";

	private EditText title, description;

	@Override
	public View onCreateView(
			LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView: start");

		View view = inflater.inflate(R.layout.fragment_edit_tag, container, false);

		init(view);

		Log.d(TAG, "onCreateView: end");
		return view;
	}

	private void init(View view) {

		Log.d(TAG, "init: start");

		title = view.findViewById(R.id.title_editText);
		description = view. findViewById(R.id.description_editText);

		ColorPickerView colorPicker = view. findViewById(R.id.color_picker_view);

		view.findViewById(R.id.backArrow_imageView)
				.setOnClickListener(view1 -> getActivity().finish());

		view.findViewById(R.id.done_imageView).setOnClickListener(view1 -> {
			ProviderHelper.insertNewTag(getContext(),
			                            title.getText().toString(),
			                            description.getText().toString(),
			                            colorPicker.getColor());
			getActivity().finish();
		});

		Utils.setupColorPicker(colorPicker,
		                       view.findViewById(R.id.color_panel_new),
		                       view.findViewById(R.id.color_panel_old),
		                       Utils.ZERO);

		Log.d(TAG, "init: end");
	}
}
