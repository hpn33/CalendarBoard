package hpn332.cb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import hpn332.cb.R;
import hpn332.cb.utils.AList;
import hpn332.cb.utils.adapter.AdapterListProject;
import hpn332.cb.utils.database.ProviderHelper;

public class ListProjectActivity extends AppCompatActivity {

	private static final String TAG = "ListProjectActivity";

	private RecyclerView         recyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_list);
		Log.d(TAG, "onCreate: start");

		init();
		Log.d(TAG, "onCreate: end");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume: start");

		ProviderHelper.queryListProject(getApplicationContext(), AList.L_PROJECT);
		recyclerView.setAdapter(new AdapterListProject(getApplicationContext(), AList.L_PROJECT));

		Log.d(TAG, "onResume: end");
	}

	private void init() {
		Log.d(TAG, "init: start");

		((Toolbar) findViewById(R.id.toolbar)).setTitle(TAG);

		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		using();

		Log.d(TAG, "init: end");
	}

	private void using() {
		Log.d(TAG, "using: start");

		findViewById(R.id.fab).setOnClickListener(
				view -> startActivity(new Intent(getApplicationContext(), AddProjectActivity.class)));

		recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

		Log.d(TAG, "using: end");
	}

}
