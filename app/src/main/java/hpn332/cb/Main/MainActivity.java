package hpn332.cb.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import hpn332.cb.AddTask.AddTaskActivity;
import hpn332.cb.R;
import hpn332.cb.Utils.Data.Contract;
import hpn332.cb.Utils.Data.ProviderHelper;
import hpn332.cb.Utils.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				startActivity(new Intent(getApplicationContext(), AddTaskActivity.class));

				/*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();*/
			}
		});

		//Provider.helper(getApplicationContext()).


		setupViewPager();
	}

	@Override
	protected void onResume() {
		super.onResume();

		ProviderHelper.queryListTask(getApplicationContext(), 1, Contract.L_STEP_1);
		ProviderHelper.queryListTask(getApplicationContext(), 2, Contract.L_STEP_2);
		ProviderHelper.queryListTask(getApplicationContext(), 3, Contract.L_STEP_3);
		ProviderHelper.queryListTask(getApplicationContext(), 4, Contract.L_STEP_4);
	}

	private void setupViewPager() {
		Log.d(TAG, "setupViewPager: ");

		SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
		adapter.addFragment(StepFragment.newInstance(0), "backlog"); //index 0
		adapter.addFragment(StepFragment.newInstance(1), "to do"); //index 1
		adapter.addFragment(StepFragment.newInstance(2), "doing"); //index 2
		adapter.addFragment(StepFragment.newInstance(3), "done"); //index 3
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(adapter);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(viewPager);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
