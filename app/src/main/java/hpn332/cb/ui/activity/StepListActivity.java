package hpn332.cb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import hpn332.cb.ui.fragment.StepFragment;
import hpn332.cb.R;
import hpn332.cb.utils.database.Contract;
import hpn332.cb.utils.database.ProviderHelper;
import hpn332.cb.utils.SectionsPagerAdapter;

public class StepListActivity extends AppCompatActivity {

	private static final String TAG = "StepListActivity";

	private ImageView            menu;
	private ViewPager            viewPager;
	private SectionsPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				startActivity(new Intent(getApplicationContext(), EditTaskActivity.class));

				/*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();*/
			}
		});

		setup();
	}

	private void setup() {

		menu = (ImageView) findViewById(R.id.menu_imageView);

		setupViewPager();

		using();
	}

	private void using() {
		menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(getApplicationContext(), TagListActivity.class));
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

		ProviderHelper.queryStepListTask(getApplicationContext(), 1, Contract.L_STEP_1);
		ProviderHelper.queryStepListTask(getApplicationContext(), 2, Contract.L_STEP_2);
		ProviderHelper.queryStepListTask(getApplicationContext(), 3, Contract.L_STEP_3);
		ProviderHelper.queryStepListTask(getApplicationContext(), 4, Contract.L_STEP_4);

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
		viewPager.setAdapter(adapter);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(viewPager);
	}
}
