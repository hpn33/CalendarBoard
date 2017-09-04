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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.util.ArrayList;

import hpn332.cb.R;
import hpn332.cb.ui.activity.AddTagActivity;
import hpn332.cb.utils.AList;
import hpn332.cb.utils.Key;
import hpn332.cb.utils.database.ProviderHelper;
import hpn332.cb.utils.model.CheckTagStructure;
import hpn332.cb.utils.model.TagStructure;
import hpn332.cb.utils.model.TaskStructure;

public class EditTaskFragment extends Fragment {

	private static final String TAG = "EditTaskFragment";

	private ImageView done, delete;
	private RadioButton backlog, todo, num1, num2, num3, num4, num5;
	private EditText title, description;
	private LinearLayout tagLayout;
	private CheckBox[]   tagBoxes;
	private int          project_id;

	@Override
	public View onCreateView(
			LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView: start");

		View view = inflater.inflate(R.layout.fragment_edit_task, container, false);

		init(view);
		using();

		Log.d(TAG, "onCreateView: end");
		return view;
	}

	private void init(View view) {

		Log.d(TAG, "init: start");

		project_id = getActivity().getIntent().getIntExtra(Key.KEY_PROJECT, 0);

		tagLayout = view.findViewById(R.id.layout_for_tags);

		done = view.findViewById(R.id.done_imageView);
		delete = view.findViewById(R.id.delete_imageView);

		title = view.findViewById(R.id.title_editText);
		description = view.findViewById(R.id.description_editText);

		backlog = view.findViewById(R.id.backlog_radio);
		todo = view.findViewById(R.id.todo_radio);
		num1 = view.findViewById(R.id.num1);
		num2 = view.findViewById(R.id.num2);
		num3 = view.findViewById(R.id.num3);
		num4 = view.findViewById(R.id.num4);
		num5 = view.findViewById(R.id.num5);

		view.findViewById(R.id.backArrow_imageView).setOnClickListener(
				view12 -> getActivity().finish());

		view.findViewById(R.id.addTag).setOnClickListener(
				view1 -> startActivity(new Intent(getContext(), AddTagActivity.class)));

		Log.d(TAG, "init: end");
	}

	private void using() {
		ArrayList<TaskStructure> arrayList = null;

		switch (getActivity().getIntent().getIntExtra(Key.KEY_STEP, 0)) {
			case 0:
				arrayList = AList.L_TODO;
				break;
			case 1:
				arrayList = AList.L_DOING;
				break;
			case 2:
				arrayList = AList.L_DONE;
				break;
		}


		final int position = getActivity().getIntent().getIntExtra(Key.KEY_POSITION, 0);
		final int id       = arrayList != null ? arrayList.get(position).getId() : 0;
		final int step     = arrayList != null ? arrayList.get(position).getStep() : 0;
		Log.d(TAG, "makeReadyStep: id :: " + id);


		setCheckStep(arrayList != null ? arrayList.get(position).getStep() : 0);
		title.setText(arrayList != null ? arrayList.get(position).getTitle() : null);
		description.setText(arrayList != null ? arrayList.get(position).getDesc() : null);
		setCheckTag(arrayList, position);
		setCheckRank(arrayList != null ? arrayList.get(position).getRank() : 0);

		delete.setVisibility(View.VISIBLE);
		delete.setOnClickListener(view -> {
			ProviderHelper.deleteOneTask(getContext(), id);
			getActivity().finish();
		});

		done.setOnClickListener(view -> {

			ProviderHelper.updateOneTask(getContext(),
			                             id,
			                             title.getText().toString(),
			                             description.getText().toString(),
			                             getTags(),
			                             step,
			                             getRank());
			Log.d(TAG, "onClick: update id ::" + id);

			getActivity().finish();
		});

	}


	private void setCheckStep(int step) {
		switch (step) {
			case 1:
				backlog.toggle();
				break;
			case 2:
				todo.toggle();
				break;
		}
	}

	private void setCheckTag(ArrayList<TaskStructure> arrayList, int position) {

		setupTags();

		String[] s = arrayList.get(position).getTag().split("`");

		for (int i = 0; i < AList.L_TAGS.size(); i++) {
			for (String value : s) {
				if (!value.equals("") &&
						AList.L_TAGS.get(i).getId() == Integer.valueOf(value)) {
					tagBoxes[i].toggle();
					AList.L_CHECK.get(i).setBool(true);
				}
			}
		}
	}

	private void setCheckRank(int rank) {
		switch (rank) {
			case 1:
				num1.toggle();
				break;
			case 2:
				num2.toggle();
				break;
			case 3:
				num3.toggle();
				break;
			case 4:
				num4.toggle();
				break;
			case 5:
				num5.toggle();
				break;
		}
	}

	// both===============================================
	// both===============================================
	// both===============================================


	private void setupTags() {
		Log.d(TAG, "setupTags: ");

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
	}

	@NonNull
	private String getTags() {
		Log.d(TAG, "getTags: ");

		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < AList.L_TAGS.size(); i++) {
			if (tagBoxes[i].isChecked()) {
				stringBuilder.append(AList.L_TAGS.get(i).getId()).append("`");
			}
		}

		return stringBuilder.toString();
	}

	private int getRank() {
		if (num1.isChecked()) return 1;
		if (num2.isChecked()) return 2;
		if (num3.isChecked()) return 3;
		if (num4.isChecked()) return 4;
		if (num5.isChecked()) return 5;
		return 1;
	}

}
