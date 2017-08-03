package hpn332.cb.Main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hpn332.cb.R;
import hpn332.cb.Utils.Data.Contract;

public class StepFragment extends Fragment {

	private RecyclerView recyclerView;
	public static final String KEY_INDEX = "KEY_INDEX";

	public static StepFragment newInstance(int index) {

		Bundle args = new Bundle();

		args.putInt(KEY_INDEX, index);
		StepFragment fragment = new StepFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(
			LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.include_recycler_view, container, false);

		recyclerView = view.findViewById(R.id.recycler_view);

		int index = getArguments().getInt(KEY_INDEX);

		adapter(index);

		return view;
	}

	private void adapter(int index) {

		AdapterList adapter = new AdapterList(getContext(), Contract.listStep[index]);

		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
	}
}
