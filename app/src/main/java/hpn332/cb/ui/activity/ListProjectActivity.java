package hpn332.cb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import hpn332.cb.R;
import hpn332.cb.utils.AList;
import hpn332.cb.utils.adapter.AdapterProjectList;
import hpn332.cb.utils.database.ProviderHelper;

public class ListProjectActivity extends AppCompatActivity {

	private static final String TAG = "ListProjectActivity";

	private Toolbar              toolbar;
	private RecyclerView         recyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_list);

		setup();
	}

	private void setup() {

		toolbar = (Toolbar) findViewById(R.id.toolbar);

		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		using();
	}

	private void using() {
		findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				startActivity(new Intent(getApplicationContext(), EditProjectActivity.class));
			}
		});

		setSupportActionBar(toolbar);

		toolbar.setTitle(TAG);

		recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

		adapter();
	}

	@Override
	protected void onResume() {
		super.onResume();

		ProviderHelper.queryListProject(getApplicationContext(), AList.L_PROJECT);
		recyclerView.setAdapter(new AdapterProjectList(getApplicationContext(), AList.L_PROJECT));
	}

	private void adapter() {

	}

}
