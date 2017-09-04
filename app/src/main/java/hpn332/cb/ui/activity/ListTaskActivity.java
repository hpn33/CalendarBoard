package hpn332.cb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import hpn332.cb.ui.fragment.BacklogFragment;
import hpn332.cb.ui.fragment.TaskFragment;
import hpn332.cb.R;
import hpn332.cb.utils.AList;
import hpn332.cb.utils.Key;
import hpn332.cb.utils.database.ProviderHelper;
import hpn332.cb.utils.adapter.SectionsPagerAdapter;

public class ListTaskActivity extends AppCompatActivity implements TaskFragment.OnStepFragment {

	private static final String TAG = "ListTaskActivity";

	private ViewPager            viewPager;
	private SectionsPagerAdapter adapter;
	private int                  project_id;
	private TabLayout            tabLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_list);
		Log.d(TAG, "onCreate: start");

		init();

		Log.d(TAG, "onCreate: end");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume: start");

		query();

		viewPager.setAdapter(adapter);

		Log.d(TAG, "onResume: end");
	}

	private void init() {
		Log.d(TAG, "init: start");

		project_id = getIntent().getIntExtra(Key.KEY_PROJECT, 0);

		findViewById(R.id.menu_imageView).setOnClickListener(
				view -> startActivity(new Intent(getApplicationContext(), ListTagActivity.class)));

		findViewById(R.id.fab).setOnClickListener(view -> {

			startActivity(new Intent(getApplicationContext(), AddActivity.class)
					              .putExtra(Key.KEY_PROJECT, project_id));

			/*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show();*/
		});

		setupViewPager();

		Log.d(TAG, "init: end");
	}

	private void setupViewPager() {
		Log.d(TAG, "setupViewPager: start");

		adapter = new SectionsPagerAdapter(getSupportFragmentManager());
		adapter.addFragment(new BacklogFragment(), "BackLOG");
		adapter.addFragment(TaskFragment.newInstance(0), "TODO"); //index 0
		adapter.addFragment(TaskFragment.newInstance(1), "Doing"); //index 1
		adapter.addFragment(TaskFragment.newInstance(2), "Done"); //index 2
		viewPager = (ViewPager) findViewById(R.id.viewpager);

		tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(viewPager);

		Log.d(TAG, "setupViewPager: end");
	}

	@Override
	public void onClickNext() {refreshAdapter();}

	private void refreshAdapter() {
		Log.d(TAG, "refreshAdapter: start");

		int selectedPosition = tabLayout.getSelectedTabPosition();

		query();

		viewPager.setAdapter(adapter);

		tabLayout.getTabAt(selectedPosition).select();

		Log.d(TAG, "refreshAdapter: end");
	}

	private void query() {
		Log.d(TAG, "query: start");

		ProviderHelper
				.queryListBacklogByProject(getApplicationContext(), project_id, AList.L_BACKLOG);
		ProviderHelper.queryListTaskByStep(getApplicationContext(), 1, project_id, AList.L_TODO);
		ProviderHelper.queryListTaskByStep(getApplicationContext(), 2, project_id, AList.L_DOING);
		ProviderHelper.queryListTaskByStep(getApplicationContext(), 3, project_id, AList.L_DONE);

		Log.d(TAG, "query: end");
	}
}
