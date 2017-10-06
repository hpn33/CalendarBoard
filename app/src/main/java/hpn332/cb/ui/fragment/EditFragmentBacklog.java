package hpn332.cb.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import hpn332.cb.R;
import hpn332.cb.ui.activity.EditActivity;
import hpn332.cb.ButtonColor;
import hpn332.cb.model.database.ProviderHelper;
import hpn332.cb.model.stucture.BackLogStructure;
import hpn332.cb.utils.U;

public class EditFragmentBacklog extends Fragment {

	private static final String TAG = "EditFragmentBacklog";

	@Override
	public View onCreateView(
			LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView: start");

		View view = inflater.inflate(R.layout.fragment_edit_backlog, container, false);

		init(view);

		Log.d(TAG, "onCreateView: end");
		return view;
	}

	private void init(View view) {

		Log.d(TAG, "init: start");

		ArrayList<BackLogStructure> arrayList = U.AList.L_BACKLOG;

		final int position  = getActivity().getIntent().getIntExtra(U.Key.POSITION, 0);
		final int id        = arrayList.get(position).getId();
		final int initColor = arrayList.get(position).getColor();

		EditText title       = view.findViewById(R.id.title_editText);
		EditText description = view.findViewById(R.id.description_editText);

		EditActivity.color_panel = view.findViewById(R.id.color_panel);
		EditActivity.color_panel.setColor(initColor);

		title.setText(arrayList.get(position).getTitle());
		description.setText(arrayList.get(position).getDesc());

		view.findViewById(R.id.done_imageView).setOnClickListener(view1 -> {

			ProviderHelper.updateOneBacklog(getContext(),
			                                id,
			                                title.getText().toString(),
			                                description.getText().toString(),
			                                EditActivity.color_panel.getColor());

			getActivity().finish();
			Log.d(TAG, "init: update id ::" + id);
		});

		view.findViewById(R.id.delete_imageView).setOnClickListener(view1 -> {
			ProviderHelper.deleteOneBacklog(getContext(), id);
			getActivity().finish();
			Log.d(TAG, "init: delete :: " + id);
		});

		view.findViewById(R.id.backArrow_imageView)
				.setOnClickListener(view1 -> getActivity().finish());

		((ButtonColor) view.findViewById(R.id.color_picker_view_dialog_button))
				.setOnShowDialogListener(initColor1 -> {
					DialogFragmentColorPicker dialog =
							DialogFragmentColorPicker.newInstance(initColor);
					dialog.show(getActivity().getFragmentManager(), "d");
				});

		Log.d(TAG, "init: end");
	}
}
