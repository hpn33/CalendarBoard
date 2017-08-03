package hpn332.cb.Tag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import hpn332.cb.R;
import hpn332.cb.Utils.Data.Contract;
import hpn332.cb.Utils.Data.ProviderHelper;

public class TagListActivity extends AppCompatActivity {

	private RecyclerView recyclerView;
	private ImageView    add, backArrow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag_list);

		setup();
	}

	@Override
	protected void onResume() {
		super.onResume();

		ProviderHelper.queryListTag(getApplicationContext(), Contract.L_TAGS);
	}

	private void setup() {

		add = (ImageView) findViewById(R.id.add_imageView);
		backArrow = (ImageView) findViewById(R.id.backArrow_imageView);

		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

		using();
		adapter();
	}

	private void using() {

		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(getApplicationContext(), AddTagActivity.class));
			}
		});

		backArrow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
	}


	private void adapter() {

		AdapterTagList adapter = new AdapterTagList(getApplicationContext(), Contract.L_TAGS);

		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
	}
}
