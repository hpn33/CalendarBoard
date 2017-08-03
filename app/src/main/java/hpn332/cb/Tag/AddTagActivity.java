package hpn332.cb.Tag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import hpn332.cb.R;
import hpn332.cb.Utils.Data.ProviderHelper;

public class AddTagActivity extends AppCompatActivity {


	private ImageView done, backArrow;
	private EditText title, description;
	private RadioButton green, orange, blue, gold;

	private View color_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_tag);

		setup();

		using();
	}

	private void setup() {

		done = (ImageView) findViewById(R.id.done_imageView);
		backArrow = (ImageView) findViewById(R.id.backArrow_imageView);

		title = (EditText) findViewById(R.id.title_editText);
		description = (EditText) findViewById(R.id.description_editText);

		green = (RadioButton) findViewById(R.id.color_green);
		orange = (RadioButton) findViewById(R.id.color_orange);
		blue = (RadioButton) findViewById(R.id.color_blue);
		gold = (RadioButton) findViewById(R.id.color_gold);

		color_view = findViewById(R.id.color_view);
	}

	private void using() {

		done.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ProviderHelper.insertNewTag(getApplicationContext(),
				                            title.getText().toString(),
				                            description.getText().toString(),
				                            checkColor());
				finish();
			}
		});

		backArrow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		green.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				color_view.setBackgroundResource(R.color.green);
			}
		});

		orange.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				color_view.setBackgroundResource(R.color.orange);
			}
		});

		blue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				color_view.setBackgroundResource(R.color.blue);
			}
		});

		gold.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				color_view.setBackgroundResource(R.color.gold);
			}
		});
	}

	private int checkColor() {
		if (green.isChecked()) return 1;
		if (orange.isChecked()) return 2;
		if (blue.isChecked()) return 3;
		if (gold.isChecked()) return 4;
		return 1;
	}
}
