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
import hpn332.cb.model.adapter.AdapterListBacklog;

public class ListFragmentBacklog extends Fragment {

	public interface OnBacklogFragment {
		void onClickBacklog(int backlogId);
	}

	private static final String TAG = "ListFragmentBacklog";

	private OnBacklogFragment onBacklogFragment;
	private RecyclerView recyclerView;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		onBacklogFragment = (OnBacklogFragment) getActivity();
	}

	@Nullable
	@Override
	public View onCreateView(
			LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView: start");
		View view = inflater.inflate(R.layout.include_recycler_view, container, false);

		recyclerView = view.findViewById(R.id.recycler_view);

		recyclerView.setLayoutManager(
				new LinearLayoutManager(
						getActivity(), LinearLayoutManager.HORIZONTAL, false));

		Log.d(TAG, "onCreateView: end");

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

		recyclerView.setAdapter(
				new AdapterListBacklog(getContext(), AList.L_BACKLOG, onBacklogFragment));
	}
}
