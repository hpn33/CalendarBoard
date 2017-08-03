package hpn332.cb.AddTag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import hpn332.cb.R;
import hpn332.cb.Utils.Data.ProviderHelper;

public class AddTagActivity extends AppCompatActivity {


	private ImageView done, backArrow;
	private EditText title, description;

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
	}

	private void using() {

		done.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ProviderHelper.insertNewTag(getApplicationContext(),
				                            title.getText().toString(),
				                            description.getText().toString(),
				                            "111111");
				finish();
			}
		});

		backArrow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
	}
}
