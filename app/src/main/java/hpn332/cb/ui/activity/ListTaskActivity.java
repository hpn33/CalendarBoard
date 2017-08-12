package hpn332.cb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import hpn332.cb.ui.fragment.StepFragment;
import hpn332.cb.R;
import hpn332.cb.utils.AList;
import hpn332.cb.utils.Key;
import hpn332.cb.utils.database.ProviderHelper;
import hpn332.cb.utils.SectionsPagerAdapter;

public class ListTaskActivity extends AppCompatActivity implements StepFragment.OnStepFragment {

	private static final String TAG = "ListTaskActivity";

	private ImageView            menu;
	private ViewPager            viewPager;
	private SectionsPagerAdapter adapter;
	private int                  project_id;
	private TabLayout            tabLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_list);


		project_id = getIntent().getIntExtra(Key.KEY_PROJECT, 0);

		setup();
	}

	private void setup() {

		menu = (ImageView) findViewById(R.id.menu_imageView);
		findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				startActivity(new Intent(getApplicationContext(), EditTaskActivity.class)
						              .putExtra(Key.KEY_PROJECT, project_id));

				/*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();*/
			}
		});
		setupViewPager();

		using();
	}

	private void using() {
		menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(getApplicationContext(), ListTagActivity.class));
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

		ProviderHelper.queryListTaskByStep(getApplicationContext(), 1, project_id, AList.L_STEP_1);
		ProviderHelper.queryListTaskByStep(getApplicationContext(), 2, project_id, AList.L_STEP_2);
		ProviderHelper.queryListTaskByStep(getApplicationContext(), 3, project_id, AList.L_STEP_3);
		ProviderHelper.queryListTaskByStep(getApplicationContext(), 4, project_id, AList.L_STEP_4);

		viewPager.setAdapter(adapter);
	}


	private void setupViewPager() {
		Log.d(TAG, "setupViewPager: ");

		adapter = new SectionsPagerAdapter(getSupportFragmentManager());
		adapter.addFragment(StepFragment.newInstance(0), "Backlog"); //index 0
		adapter.addFragment(StepFragment.newInstance(1), "TODO"); //index 1
		adapter.addFragment(StepFragment.newInstance(2), "Doing"); //index 2
		adapter.addFragment(StepFragment.newInstance(3), "Done"); //index 3
		viewPager = (ViewPager) findViewById(R.id.viewpager);

		tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(viewPager);
	}

	@Override
	public void onClickNext() {

		refreshAdapter();
	}

	private void refreshAdapter() {

		int selectedPosition = tabLayout.getSelectedTabPosition();

		ProviderHelper.queryListTaskByStep(getApplicationContext(), 1, project_id, AList.L_STEP_1);
		ProviderHelper.queryListTaskByStep(getApplicationContext(), 2, project_id, AList.L_STEP_2);
		ProviderHelper.queryListTaskByStep(getApplicationContext(), 3, project_id, AList.L_STEP_3);
		ProviderHelper.queryListTaskByStep(getApplicationContext(), 4, project_id, AList.L_STEP_4);

		viewPager.setAdapter(adapter);

		tabLayout.getTabAt(selectedPosition).select();
	}
}
