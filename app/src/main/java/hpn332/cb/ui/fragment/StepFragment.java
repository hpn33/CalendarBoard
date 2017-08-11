package hpn332.cb.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hpn332.cb.R;
import hpn332.cb.utils.AList;
import hpn332.cb.utils.adapter.AdapterStepList;
import static hpn332.cb.utils.Key.KEY_STEP;

public class StepFragment extends Fragment {

	public interface OnStepFragment {
		void onClickNextStep();
	}

	private RecyclerView recyclerView;

	private OnStepFragment onStepFragment;

	public static StepFragment newInstance(int index) {

		Bundle args = new Bundle();

		args.putInt(KEY_STEP, index);
		StepFragment fragment = new StepFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		onStepFragment = (OnStepFragment) context;
	}

	@Nullable
	@Override
	public View onCreateView(
			LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.include_recycler_view, container, false);

		recyclerView = view.findViewById(R.id.recycler_view);

		adapter(getArguments().getInt(KEY_STEP));

		return view;
	}

	private void adapter(int index) {

		recyclerView.setAdapter(
				new AdapterStepList(getContext(), AList.listStep[index], index, onStepFragment));

		recyclerView.setLayoutManager(
				new LinearLayoutManager(getContext()));
	}
}
