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
import hpn332.cb.model.database.ProviderHelper;

public class AddFragmentProject extends Fragment {

	private static final String TAG = "AddFragmentProject";

	private ImageView done;
	private EditText  title, description;

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

		title = view.findViewById(R.id.title_editText);
		description = view.findViewById(R.id.description_editText);



		view.findViewById(R.id.backArrow_imageView)
				.setOnClickListener(view1 -> getActivity().finish());

		Log.d(TAG, "init: end");
	}

	private void using() {

		Log.d(TAG, "usingAdd: ");

		done.setOnClickListener(view -> {

			ProviderHelper.insertNewProjectWithBacklog(getContext(),
			                                           title.getText().toString(),
			                                           description.getText().toString());
			getActivity().finish();
		});
	}
}
