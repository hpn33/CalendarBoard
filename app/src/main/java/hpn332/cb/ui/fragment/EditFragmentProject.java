package hpn332.cb.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import hpn332.cb.R;
import hpn332.cb.utils.AList;
import hpn332.cb.utils.Key;
import hpn332.cb.model.database.ProviderHelper;

public class EditFragmentProject extends Fragment {

	private static final String TAG = "EditFragmentProject";

	private ImageView done, delete;
	private EditText title, description;

	@Override
	public View onCreateView(
			LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView: start");

		View view = inflater.inflate(R.layout.fragment_edit_project, container, false);

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

		(view.findViewById(R.id.backArrow_imageView))
				.setOnClickListener(view1 -> getActivity().finish());
		Log.d(TAG, "init: end");
	}

	private void using() {

		final int position = getActivity().getIntent().getIntExtra(Key.POSITION, 0);
		final int id       = AList.L_PROJECT.get(position).getId();
		Log.d(TAG, "makeReadyStep: id :: " + id);

		title.setText(AList.L_PROJECT.get(position).getTitle());
		description.setText(AList.L_PROJECT.get(position).getDesc());

		delete.setVisibility(View.VISIBLE);
		delete.setOnClickListener(view -> {
			ProviderHelper.deleteOneProject(getContext(), id);
			getActivity().finish();
		});

		done.setOnClickListener(view -> {

			ProviderHelper.updateOneProject(getContext(),
			                                id,
			                                title.getText().toString(),
			                                description.getText().toString());

			Log.d(TAG, "onClick: update id ::" + id);

			getActivity().finish();
		});

	}

}
