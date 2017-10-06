package hpn332.cb.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import hpn332.cb.R;
import hpn332.cb.utils.U;
import hpn332.cb.utils.Utils;
import hpn332.cb.model.database.ProviderHelper;
import hpn332.cb.model.stucture.TagStructure;
import hpn332.library.view.ColorPanelView;
import hpn332.library.view.ColorPickerView;

public class EditFragmentTag extends Fragment {

	private static final String TAG = "EditFragmentTag";

	private ColorPickerView colorPicker;
	private ColorPanelView  oldColorPanel;
	private ColorPanelView  newColorPanel;

	private ImageView done, delete;
	private EditText title, description;

	@Override
	public View onCreateView(
			LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView: start");

		View view = inflater.inflate(R.layout.fragment_edit_tag, container, false);

		init(view);
		using();

		Log.d(TAG, "onCreateView: end");
		return view;
	}

	private void init(View view) {

		Log.d(TAG, "init: start");

		done = view.findViewById(R.id.done_imageView);
		delete = view.findViewById(R.id.delete_imageView);

		title = view.findViewById(R.id.title_editText);
		description = view.findViewById(R.id.description_editText);

		view.findViewById(R.id.backArrow_imageView)
				.setOnClickListener(view1 -> getActivity().finish());

		colorPicker = view.findViewById(R.id.color_picker_view);
		oldColorPanel = view.findViewById(R.id.color_panel_old);
		newColorPanel = view.findViewById(R.id.color_panel_new);

		Log.d(TAG, "init: end");
	}

	private void using() {

		Log.d(TAG, "using: start");

		final int                     position  =
				getActivity().getIntent().getIntExtra(U.Key.POSITION, 0);
		final ArrayList<TagStructure> arrayList = U.AList.L_TAGS;

		title.setText(arrayList.get(position).getTitle());
		description.setText(arrayList.get(position).getDesc());

		delete.setOnClickListener(view -> {
			ProviderHelper.deleteOneTag(getContext(), arrayList.get(position).getId());

			getActivity().finish();
		});

		done.setOnClickListener(view -> {
			ProviderHelper
					.updateOneTag(getContext(),
					              arrayList.get(position).getId(),
					              title.getText().toString(),
					              description.getText().toString(),
					              colorPicker.getColor());
			getActivity().finish();
		});

		Utils.setupColorPicker(colorPicker, newColorPanel, oldColorPanel,
		                       arrayList.get(position).getColor());

		Log.d(TAG, "using: end");
	}

}
