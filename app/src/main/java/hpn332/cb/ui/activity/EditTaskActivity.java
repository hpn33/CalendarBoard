package hpn332.cb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.util.ArrayList;

import hpn332.cb.R;
import hpn332.cb.utils.Key;
import hpn332.cb.utils.model.TaskStructure;
import hpn332.cb.utils.Utils;
import hpn332.cb.utils.database.Contract;
import hpn332.cb.utils.database.ProviderHelper;

public class EditTaskActivity extends AppCompatActivity {

	private static final String TAG = "EditTaskActivity";

	private ImageView done, backArrow;
	private RadioButton backlog, todo, num1, num2, num3, num4, num5;
	private EditText title, description;
	private Button       addTag;
	private LinearLayout tagLayout;

	private CheckBox[] tagBoxes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);

		setup();

		if (getIntent().getBooleanExtra(Key.KEY_UPDATE, false)) usingEdit();
		else usingAdd();
	}

	private void setup() {

		tagLayout = (LinearLayout) findViewById(R.id.layout_for_tags);

		addTag = (Button) findViewById(R.id.addTag);

		done = (ImageView) findViewById(R.id.done_imageView);
		backArrow = (ImageView) findViewById(R.id.backArrow_imageView);

		title = (EditText) findViewById(R.id.title_editText);
		description = (EditText) findViewById(R.id.description_editText);

		backlog = (RadioButton) findViewById(R.id.backlog_radio);
		todo = (RadioButton) findViewById(R.id.todo_radio);
		num1 = (RadioButton) findViewById(R.id.num1);
		num2 = (RadioButton) findViewById(R.id.num2);
		num3 = (RadioButton) findViewById(R.id.num3);
		num4 = (RadioButton) findViewById(R.id.num4);
		num5 = (RadioButton) findViewById(R.id.num5);
	}

	// add=================================================

	private void usingAdd() {
		Log.d(TAG, "usingAdd: ");

		done.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				ProviderHelper.insertNewTask(getApplicationContext(),
				                             title.getText().toString(),
				                             description.getText().toString(),
				                             getTag(),
				                             getStep(),
				                             getRank());

				finish();
			}
		});

		backArrow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		setupTags();

		addTag.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(getApplicationContext(), EditTagActivity.class));
			}
		});

	}

	private void setupTags() {
		Log.d(TAG, "setupTags: ");

		ProviderHelper.queryListTag(getApplicationContext(), Contract.L_TAGS);

		tagBoxes = new CheckBox[Contract.L_TAGS.size()];

		for (int i = 0; i < Contract.L_TAGS.size(); i++) {
			tagBoxes[i] = new CheckBox(getApplicationContext());
			tagBoxes[i].setPadding(8, 12, 8, 12);
			tagBoxes[i].setText(Contract.L_TAGS.get(i).getTitle());
			tagBoxes[i].setBackgroundResource(Utils.getColor(Contract.L_TAGS.get(i).getColor()));

			tagLayout.addView(tagBoxes[i]);
		}

	}

	@NonNull
	private String getTag() {
		Log.d(TAG, "getTag: ");

		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < Contract.L_TAGS.size(); i++) {
			if (tagBoxes[i].isChecked()) {
				stringBuilder.append(Contract.L_TAGS.get(i).getId() + "`");
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

	private int getStep() {
		if (backlog.isChecked()) return 1;
		if (todo.isChecked()) return 2;
		return 1;
	}

	// edit=================================================

	private void usingEdit() {

		switch (getIntent().getIntExtra(Key.KEY_STEP, 0)) {
			case 0:
				makeReadyStep(Contract.L_STEP_1);
				break;
			case 1:
				makeReadyStep(Contract.L_STEP_2);
				break;
			case 2:
				makeReadyStep(Contract.L_STEP_3);
				break;
			case 3:
				makeReadyStep(Contract.L_STEP_4);
				break;
		}
	}

	private void makeReadyStep(final ArrayList<TaskStructure> arrayList) {

		final int position = getIntent().getIntExtra(Key.KEY_POSITION, 0);

		Log.d(TAG, "makeReadyStep: id :: " + arrayList.get(position).getId());
		title.setText(arrayList.get(position).getTitle());
		description.setText(arrayList.get(position).getDesc());

		done.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				ProviderHelper.updateOneTask(getApplicationContext(),
				                             arrayList.get(position).getId(),
				                             title.getText().toString(),
				                             description.getText().toString(),
				                             arrayList.get(position).getTag(),
				                             arrayList.get(position).getStep(),
				                             arrayList.get(position).getRank());
				Log.d(TAG, "onClick: update id ::" + arrayList.get(position).getId());

				finish();
			}
		});

	}

}
