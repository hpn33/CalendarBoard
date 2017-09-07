package hpn332.cb.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import hpn332.cb.R;
import hpn332.cb.utils.database.ProviderHelper;

public class AddProjectActivity extends AppCompatActivity {

	private static final String TAG = "AddProjectActivity";

	private ImageView done;
	private EditText title, description;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_edit_project);

		setup();
	}

	@Override
	protected void onResume() {
		super.onResume();

		//if (getIntent().getBooleanExtra(Key.KEY_UPDATE, false)) usingEdit();
		//else
			usingAdd();
	}

	private void setup() {

		done = (ImageView) findViewById(R.id.done_imageView);

		title = (EditText) findViewById(R.id.title_editText);
		description = (EditText) findViewById(R.id.description_editText);

		findViewById(R.id.backArrow_imageView).setOnClickListener(view -> finish());
	}

	// add=================================================
	// add=================================================
	// add=================================================

	private void usingAdd() {
		Log.d(TAG, "usingAdd: ");

		done.setOnClickListener(view -> {

			ProviderHelper.insertNewProject(getApplicationContext(),
			                                title.getText().toString(),
			                                description.getText().toString());
			finish();
		});

	}

	// edit=================================================
	// edit=================================================
	// edit=================================================

	/*private void usingEdit() {

		final int position = getIntent().getIntExtra(Key.KEY_POSITION, 0);
		final int id       = AList.L_PROJECT.get(position).getId();
		Log.d(TAG, "makeReadyStep: id :: " + id);

		title.setText(AList.L_PROJECT.get(position).getTitle());
		description.setText(AList.L_PROJECT.get(position).getDesc());

		delete.setVisibility(View.VISIBLE);
		delete.setOnClickListener(view -> {
			ProviderHelper.deleteOneProject(getApplicationContext(), id);
			finish();
		});

		done.setOnClickListener(view -> {

			ProviderHelper.updateOneProject(getApplicationContext(),
			                                id,
			                                title.getText().toString(),
			                                description.getText().toString());

			Log.d(TAG, "onClick: update id ::" + id);

			finish();
		});

	}*/
}
