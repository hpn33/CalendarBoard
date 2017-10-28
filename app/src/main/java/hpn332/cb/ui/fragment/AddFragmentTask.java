package hpn332.cb.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import hpn332.cb.model.database.ProviderHelper;
import hpn332.cb.model.stucture.CheckTagStructure;
import hpn332.cb.model.stucture.TagStructure;
import hpn332.cb.ui.activity.EditActivity;
import hpn332.cb.utils.Key;
import hpn332.cb.utils.List;

public class AddFragmentTask extends Fragment {

	private static final String TAG = "AddFragmentTask";

	private RadioButton num1, num2, num3, num4, num5;
	private LinearLayout tagLayout;
	private CheckBox[]   tagBoxes;


	private EditText title, description;

	@Override
	public View onCreateView(
			LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.d(TAG, "onCreateView: start");

		View view = inflater.inflate(R.layout.fragment_add_task, container, false);

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
				view12 -> startActivity(new Intent(getContext(), AddFragmentTag.class)));

		if (EditActivity.backlogId == 0)
			view.findViewById(R.id.fab).setOnClickListener(
					view1 -> {
						ProviderHelper.insertNewTask(
								getContext(),
								getActivity().getIntent().getIntExtra(Key.PROJECT, 0),
								EditActivity.backlogId,
								title.getText().toString(),
								description.getText().toString(),
								"",
								getRank());
						getActivity().finish();
					});
		else Snackbar.make(view, "select the backlog", Snackbar.LENGTH_SHORT).show();

		setupTags();
		setupBacklogFragment();

		Log.d(TAG, "init: end");
	}

	// both===============================================
	// both===============================================
	// both===============================================

	private void setupBacklogFragment() {
		Log.d(TAG, "setupBacklogFragment: start");

		getActivity().getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.frameLayout, new ListFragmentBacklog())
				.commit();

		Log.d(TAG, "setupBacklogFragment: end");
	}

	private void setupTags() {
		Log.d(TAG, "setupTags: start");

		List.L_TAGS.clear();
		List.L_CHECK.clear();

		ProviderHelper.queryListTag(getContext(), List.L_TAGS);

		for (TagStructure arrayList : List.L_TAGS) {
			List.L_CHECK.add(
					new CheckTagStructure(arrayList.getTitle(),
					                      arrayList.getColor(),
					                      false));
			Log.d(TAG, "setupTags: color::" + arrayList.getColor());
		}

		tagBoxes = new CheckBox[List.L_CHECK.size()];

		for (int i = 0; i < List.L_CHECK.size(); i++) {
			tagBoxes[i] = new CheckBox(getContext());
			tagBoxes[i].setPadding(8, 12, 8, 12);

			tagBoxes[i].setText(List.L_CHECK.get(i).getTitle());
			//tagBoxes[i].setBackgroundResource(Utils.getColor(List.L_CHECK.get(i).getColor()));

			tagLayout.addView(tagBoxes[i]);
		}

		Log.d(TAG, "setupTags: end");
	}

	@NonNull
	private String getTags() {
		Log.d(TAG, "getTags: start");

		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < List.L_TAGS.size(); i++) {
			if (tagBoxes[i].isChecked()) {
				stringBuilder.append(List.L_TAGS.get(i).getId()).append("`");
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
