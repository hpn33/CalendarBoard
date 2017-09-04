package hpn332.cb.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hpn332.cb.R;
import hpn332.cb.utils.AList;
import hpn332.cb.utils.adapter.AdapterListTask;

import static hpn332.cb.utils.Key.KEY_STEP;

public class TaskFragment extends Fragment {

	private static final String TAG = "TaskFragment";

	public interface OnStepFragment{
		void onClickNext();
	}

	private RecyclerView recyclerView;
	private OnStepFragment onStepFragment;


	public static TaskFragment newInstance(int index) {

		Log.d(TAG, "newInstance: start");

		Bundle args = new Bundle();

		args.putInt(KEY_STEP, index);
		TaskFragment fragment = new TaskFragment();
		fragment.setArguments(args);

		Log.d(TAG, "newInstance: end");
		return fragment;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		Log.d(TAG, "onAttach: start");

		onStepFragment = (OnStepFragment) getActivity();

		Log.d(TAG, "onAttach: end");
	}

	@Nullable
	@Override
	public View onCreateView(
			LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {

		Log.d(TAG, "onCreateView: start");
		View view = inflater.inflate(R.layout.include_recycler_view, container, false);

		recyclerView = view.findViewById(R.id.recycler_view);

		adapter(getArguments().getInt(KEY_STEP));


		Log.d(TAG, "onCreateView: end");

		return view;
	}

	private void adapter(int index) {
		Log.d(TAG, "adapter: start");
		
		recyclerView.setAdapter(
				new AdapterListTask(getContext(), AList.L_STEP[index], index, onStepFragment));

		recyclerView.setLayoutManager(
				new LinearLayoutManager(getContext()));

		Log.d(TAG, "adapter: end");
	}
}
