package hpn332.cb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import hpn332.cb.R;
import hpn332.cb.utils.AList;
import hpn332.cb.utils.adapter.AdapterTagList;
import hpn332.cb.utils.database.ProviderHelper;

public class ListTagActivity extends AppCompatActivity {

	private RecyclerView   recyclerView;
	private ImageView      backArrow;
	private AdapterTagList adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag_list);

		setup();
	}

	@Override
	protected void onResume() {
		super.onResume();

		ProviderHelper.queryListTag(getApplicationContext(), AList.L_TAGS);
		recyclerView.setAdapter(adapter);
	}

	private void setup() {

		backArrow = (ImageView) findViewById(R.id.backArrow_imageView);

		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

		using();
		adapter();
	}

	private void using() {

		findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				startActivity(new Intent(getApplicationContext(), EditTagActivity.class));
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

		adapter = new AdapterTagList(getApplicationContext(), AList.L_TAGS);

		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
	}
}
