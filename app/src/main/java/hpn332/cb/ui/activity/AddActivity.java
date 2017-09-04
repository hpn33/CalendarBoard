package hpn332.cb.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import hpn332.cb.R;
import hpn332.cb.ui.fragment.InsertBacklogFragment;
import hpn332.cb.ui.fragment.InsertTaskFragment;
import hpn332.cb.utils.Key;
import hpn332.cb.utils.adapter.SectionsPagerAdapter;

public class AddActivity extends AppCompatActivity {

	private static final String TAG = "AddActivity";

	private ViewPager            viewPager;
	private SectionsPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		Log.d(TAG, "onCreate: start");

		init();

		Log.d(TAG, "onCreate: end");
	}

	private void init() {
		Log.d(TAG, "init: start");

		setupViewPager();

		findViewById(R.id.backArrow_imageView).setOnClickListener(view -> finish());

		Log.d(TAG, "init: end");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume: start");

		viewPager.setAdapter(adapter);

		Log.d(TAG, "onResume: end");
	}

	private void setupViewPager() {
		Log.d(TAG, "setupViewPager: start");

		int project_id = getIntent().getIntExtra(Key.KEY_PROJECT, 0);

		Log.d(TAG, "setupViewPager: project :: " + project_id);

		adapter = new SectionsPagerAdapter(getSupportFragmentManager());
		adapter.addFragment(InsertBacklogFragment.newInstance(project_id), "BACKLOG");
		adapter.addFragment(InsertTaskFragment.newInstance(project_id), "TASK");

		viewPager = (ViewPager) findViewById(R.id.viewpager);

		((TabLayout) findViewById(R.id.tabs)).setupWithViewPager(viewPager);

		Log.d(TAG, "setupViewPager: end");
	}

}
