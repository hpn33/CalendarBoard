package hpn332.cb.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import hpn332.cb.ButtonColor;
import hpn332.cb.R;
import hpn332.cb.ui.activity.EditActivity;
import hpn332.cb.utils.U;
import hpn332.cb.utils.Utils;
import hpn332.cb.model.database.ProviderHelper;

public class AddFragmentBacklog extends Fragment {

	private static final String TAG = "AddFragmentBacklog";

	private EditText title, description;

	@Override
	public View onCreateView(
			LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.d(TAG, "onCreateView: start");

		View view = inflater.inflate(R.layout.fragment_add_backlog, container, false);

		init(view);

		Log.d(TAG, "onCreateView: end");

		return view;
	}

	private void init(View view) {

		Log.d(TAG, "init: start");

		title = view.findViewById(R.id.title_editText);
		description = view.findViewById(R.id.description_editText);

		EditActivity.color_panel = view.findViewById(R.id.color_panel);

		view.findViewById(R.id.fab).setOnClickListener(
				view1 -> {

					ProviderHelper.insertNewBacklog(
							getContext(),
							getActivity().getIntent().getIntExtra(U.Key.PROJECT, 0),
							title.getText().toString(),
							description.getText().toString(),
							EditActivity.color_panel.getColor());

					getActivity().finish();
				});

		((ButtonColor) view.findViewById(R.id.color_picker_view_dialog_button))
				.setOnShowDialogListener(initColor1 -> {
					DialogFragmentColorPicker dialog =
							DialogFragmentColorPicker.newInstance(Utils.ZERO);
					dialog.show(getActivity().getFragmentManager(), "d");
				});

		Log.d(TAG, "init: end");
	}

}
