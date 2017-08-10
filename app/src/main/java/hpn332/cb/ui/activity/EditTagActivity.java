package hpn332.cb.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.util.ArrayList;

import hpn332.cb.R;
import hpn332.cb.utils.Key;
import hpn332.cb.utils.database.Contract;
import hpn332.cb.utils.database.ProviderHelper;
import hpn332.cb.utils.model.TagStructure;

public class EditTagActivity extends AppCompatActivity {

	private ImageView done, delete;
	private EditText title, description;
	private RadioButton green, orange, blue, gold;

	private View color_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_tag);

		setup();

		if (getIntent().getBooleanExtra(Key.KEY_UPDATE, false)) usingEdit();
		else usingAdd();
	}

	private void setup() {

		done = (ImageView) findViewById(R.id.done_imageView);
		delete = (ImageView) findViewById(R.id.delete_imageView);
		ImageView backArrow = (ImageView) findViewById(R.id.backArrow_imageView);

		title = (EditText) findViewById(R.id.title_editText);
		description = (EditText) findViewById(R.id.description_editText);

		green = (RadioButton) findViewById(R.id.color_green);
		orange = (RadioButton) findViewById(R.id.color_orange);
		blue = (RadioButton) findViewById(R.id.color_blue);
		gold = (RadioButton) findViewById(R.id.color_gold);

		color_view = findViewById(R.id.color_view);

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

	// Add============================================
	// Add============================================
	// Add============================================

	private void usingAdd() {

		done.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ProviderHelper.insertNewTag(getApplicationContext(),
				                            title.getText().toString(),
				                            description.getText().toString(),
				                            setColor());
				finish();
			}
		});
	}

	// edit============================================
	// edit============================================
	// edit============================================

	private void usingEdit() {

		final int                     position  = getIntent().getIntExtra(Key.KEY_POSITION, 0);
		final ArrayList<TagStructure> arrayList = Contract.L_TAGS;

		title.setText(arrayList.get(position).getTitle());
		description.setText(arrayList.get(position).getDesc());

		setCheckColor(arrayList, position);

		delete.setVisibility(View.VISIBLE);
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ProviderHelper.deleteOneTag(getApplicationContext(),
				                            arrayList.get(position).getId());
				finish();
			}
		});

		done.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ProviderHelper
						.updateOneTag(getApplicationContext(),
						              arrayList.get(position).getId(),
						              title.getText().toString(),
						              description.getText().toString(),
						              setColor());
				finish();
			}
		});
	}

	private void setCheckColor(ArrayList<TagStructure> arrayList, int position) {
		switch (arrayList.get(position).getColor()) {
			case 1:
				green.toggle();
				break;
			case 2:
				orange.toggle();
				break;
			case 3:
				blue.toggle();
				break;
			case 4:
				gold.toggle();
				break;
		}

		color_view.setBackgroundResource(showColor(arrayList.get(position).getColor()));
	}

	private int showColor(int index) {
		switch (index) {
			case 1:
				return R.color.green;
			case 2:
				return R.color.orange;
			case 3:
				return R.color.blue;
			case 4:
				return R.color.gold;

			default:
				return R.color.green;
		}
	}

	// both============================================
	// both============================================
	// both============================================

	private int setColor() {
		if (green.isChecked()) return 1;
		if (orange.isChecked()) return 2;
		if (blue.isChecked()) return 3;
		if (gold.isChecked()) return 4;
		return 1;
	}

}
