package hpn332.cb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import hpn332.cb.R;
import hpn332.cb.ui.fragment.AddFragmentTag;
import hpn332.cb.model.adapter.AdapterListTag;
import hpn332.cb.model.database.ProviderHelper;
import hpn332.cb.utils.U.AList;

public class ListTagActivity extends AppCompatActivity {

	private static final String TAG = "ListTagActivity";

	private RecyclerView   recyclerView;
	private AdapterListTag adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag_list);

		Log.d(TAG, "onCreate: start");

		init();
		using();

		Log.d(TAG, "onCreate: end");
	}

	@Override
	protected void onResume() {
		super.onResume();

		Log.d(TAG, "onResume: start");

		ProviderHelper.queryListTag(getApplicationContext(), AList.L_TAGS);
		recyclerView.setAdapter(adapter);

		Log.d(TAG, "onResume: end");
	}

	private void init() {

		Log.d(TAG, "init: start");

		findViewById(R.id.backArrow_imageView).setOnClickListener(view -> finish());

		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

		adapter();

		Log.d(TAG, "init: end");
	}

	private void using() {

		Log.d(TAG, "using: start");

		findViewById(R.id.fab).setOnClickListener(
				view -> startActivity(
						new Intent(getApplicationContext(), AddFragmentTag.class)));

		Log.d(TAG, "using: end");
	}

	private void adapter() {

		Log.d(TAG, "adapter: start");

		adapter = new AdapterListTag(getApplicationContext(), AList.L_TAGS);

		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

		Log.d(TAG, "adapter: end");
	}
}
