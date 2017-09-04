package hpn332.cb.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import hpn332.cb.R;
import hpn332.cb.utils.Utils;
import hpn332.cb.utils.database.ProviderHelper;
import hpn332.library.view.ColorPanelView;
import hpn332.library.view.ColorPickerView;

public class AddTagActivity extends AppCompatActivity {

	private static final String TAG = "AddTagActivity";

	private EditText title, description;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_tag);

		Log.d(TAG, "onCreate: start");

		init();

		Log.d(TAG, "onCreate: end");
	}

	private void init() {

		Log.d(TAG, "init: start");

		title = (EditText) findViewById(R.id.title_editText);
		description = (EditText) findViewById(R.id.description_editText);

		ColorPickerView colorPicker = (ColorPickerView) findViewById(R.id.color_picker_view);

		findViewById(R.id.backArrow_imageView).setOnClickListener(view -> finish());

		findViewById(R.id.done_imageView).setOnClickListener(view -> {
			ProviderHelper.insertNewTag(getApplicationContext(),
			                            title.getText().toString(),
			                            description.getText().toString(),
			                            colorPicker.getColor());
			finish();
		});

		Utils.setupColorPicker(colorPicker,
		                       (ColorPanelView) findViewById(R.id.color_panel_new),
		                       (ColorPanelView) findViewById(R.id.color_panel_old),
		                       Utils.NO_COLOR);

		Log.d(TAG, "init: end");
	}

}
