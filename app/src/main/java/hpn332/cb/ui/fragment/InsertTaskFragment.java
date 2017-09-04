package hpn332.cb.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import hpn332.cb.R;
import hpn332.cb.ui.activity.AddTagActivity;
import hpn332.cb.utils.AList;
import hpn332.cb.utils.Key;
import hpn332.cb.utils.database.ProviderHelper;
import hpn332.cb.utils.model.CheckTagStructure;
import hpn332.cb.utils.model.TagStructure;

public class InsertTaskFragment extends Fragment {

	private static final String TAG = "InsertTaskFragment";

	private RadioButton num1, num2, num3, num4, num5;
	private LinearLayout tagLayout;
	private CheckBox[]   tagBoxes;

	private EditText title, description;

	public static InsertTaskFragment newInstance(int project_id) {

		Log.d(TAG, "newInstance: start");

		Bundle args = new Bundle();
		args.putInt(Key.KEY_PROJECT, project_id);
		InsertTaskFragment fragment = new InsertTaskFragment();
		fragment.setArguments(args);

		Log.d(TAG, "newInstance: end");
		return fragment;
	}

	@Override
	public View onCreateView(
			LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.d(TAG, "onCreateView: start");

		View view = inflater.inflate(R.layout.fragment_insert_task, container, false);

		init(view);

		Log.d(TAG, "onCreateView: end");

		return view;
	}

	private void init(View view) {

		Log.d(TAG, "init: start");

		title = view.findViewById(R.id.title_editText);
		description = view.findViewById(R.id.description_editText);

		tagLayout = view.findViewById(R.id.layout_for_tags);

		title = view.findViewById(R.id.title_editText);
		description = view.findViewById(R.id.description_editText);

		num1 = view.findViewById(R.id.num1);
		num2 = view.findViewById(R.id.num2);
		num3 = view.findViewById(R.id.num3);
		num4 = view.findViewById(R.id.num4);
		num5 = view.findViewById(R.id.num5);

		view.findViewById(R.id.addTag).setOnClickListener(
				view12 -> startActivity(new Intent(getContext(), AddTagActivity.class)));


		view.findViewById(R.id.fab).setOnClickListener(
				view1 -> {
					ProviderHelper.insertNewTask(
							getContext(), getArguments().getInt(Key.KEY_PROJECT),
							title.getText().toString(), description.getText().toString(),
							"", 1, getRank());
					getActivity().finish();
				});

		setupTags();

		Log.d(TAG, "init: end");
	}

	// both===============================================
	// both===============================================
	// both===============================================


	private void setupTags() {
		Log.d(TAG, "setupTags: start");

		AList.L_TAGS.clear();
		AList.L_CHECK.clear();

		ProviderHelper.queryListTag(getContext(), AList.L_TAGS);

		for (TagStructure arrayList : AList.L_TAGS) {
			AList.L_CHECK.add(
					new CheckTagStructure(arrayList.getTitle(),
					                      arrayList.getColor(),
					                      false));
			Log.d(TAG, "setupTags: color::" + arrayList.getColor());
		}

		tagBoxes = new CheckBox[AList.L_CHECK.size()];

		for (int i = 0; i < AList.L_CHECK.size(); i++) {
			tagBoxes[i] = new CheckBox(getContext());
			tagBoxes[i].setPadding(8, 12, 8, 12);

			tagBoxes[i].setText(AList.L_CHECK.get(i).getTitle());
			//tagBoxes[i].setBackgroundResource(Utils.getColor(AList.L_CHECK.get(i).getColor()));

			tagLayout.addView(tagBoxes[i]);
		}

		Log.d(TAG, "setupTags: end");
	}

	@NonNull
	private String getTags() {
		Log.d(TAG, "getTags: start");

		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < AList.L_TAGS.size(); i++) {
			if (tagBoxes[i].isChecked()) {
				stringBuilder.append(AList.L_TAGS.get(i).getId()).append("`");
			}
		}

		Log.d(TAG, "getTags: end");
		return stringBuilder.toString();
	}

	private int getRank() {
		Log.d(TAG, "getRank: ");
		if (num1.isChecked()) return 1;
		if (num2.isChecked()) return 2;
		if (num3.isChecked()) return 3;
		if (num4.isChecked()) return 4;
		if (num5.isChecked()) return 5;
		return 1;
	}
}
