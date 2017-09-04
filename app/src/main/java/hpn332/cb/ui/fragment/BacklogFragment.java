package hpn332.cb.ui.fragment;

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
import hpn332.cb.utils.adapter.AdapterListBacklog;


public class BacklogFragment extends Fragment {

	private static final String TAG = "BacklogFragment";

	@Nullable
	@Override
	public View onCreateView(
			LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView: start");
		View view = inflater.inflate(R.layout.include_recycler_view, container, false);

		RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

		recyclerView.setAdapter(
				new AdapterListBacklog(getContext(), AList.L_BACKLOG));

		recyclerView.setLayoutManager(
				new LinearLayoutManager(getContext()));

		Log.d(TAG, "onCreateView: end");

		return view;
	}
}
