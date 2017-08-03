package hpn332.cb.AddTask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import hpn332.cb.AddTag.AddTagActivity;
import hpn332.cb.R;
import hpn332.cb.Utils.Data.ProviderHelper;

public class AddTaskActivity extends AppCompatActivity {

	private static final String TAG = "AddTaskActivity";

	private ImageView done, backArrow;
	private RadioButton backlog, todo, num1, num2, num3, num4, num5;
	private EditText title, description;
	private Button  addTag;
	private Spinner tagList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);

		setup();

		using();
	}

	private void setup() {

		tagList = (Spinner) findViewById(R.id.tagList);

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

	private void using() {

		done.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ProviderHelper.insertNewTask(getApplicationContext(),
				                             title.getText().toString(),
				                             description.getText().toString(),
				                             "_1",
				                             checkStep(),
				                             checkRank());
				finish();
			}
		});

		backArrow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});


		addTag.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(getApplicationContext(), AddTagActivity.class));
			}
		});

	}

	private int checkRank() {
		if (num1.isChecked()) return 1;
		if (num2.isChecked()) return 2;
		if (num3.isChecked()) return 3;
		if (num4.isChecked()) return 4;
		if (num5.isChecked()) return 5;
		return 1;
	}

	private int checkStep() {
		if (backlog.isChecked()) return 1;
		if (todo.isChecked()) return 2;
		return 1;
	}

}
