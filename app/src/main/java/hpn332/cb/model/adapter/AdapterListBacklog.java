package hpn332.cb.model.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hpn332.cb.R;
import hpn332.cb.ui.fragment.ListFragmentBacklog;
import hpn332.cb.utils.Type;
import hpn332.cb.utils.Utils;
import hpn332.cb.model.stucture.BackLogStructure;

public class AdapterListBacklog extends RecyclerView.Adapter<AdapterListBacklog.ItemHolder> {

	private static final String TAG = "AdapterListBacklog";

	private LayoutInflater                        inflater;
	private ArrayList<BackLogStructure>           arrayList;
	private ListFragmentBacklog.OnBacklogFragment onBacklogFragment;

	public AdapterListBacklog(
			Context context, ArrayList<BackLogStructure> arrayList,
			ListFragmentBacklog.OnBacklogFragment onBacklogFragment) {

		this.arrayList = arrayList;
		inflater = LayoutInflater.from(context);
		this.onBacklogFragment = onBacklogFragment;
	}

	@Override
	public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.row_item_backlog, parent, false);

		return new ItemHolder(view);
	}

	@Override
	public void onBindViewHolder(final ItemHolder holder, int position) {
		Log.d(TAG, "onBindViewHolder: position :: " + position);
		holder.onBind(position);
	}

	@Override
	public int getItemCount() {
		return arrayList.size();
	}

	class ItemHolder extends RecyclerView.ViewHolder
			implements View.OnLongClickListener, View.OnClickListener {

		private TextView title, description;
		private View colorView;
		private int  position;

		ItemHolder(View view) {
			super(view);

			title = view.findViewById(R.id.title_textView);
			description = view.findViewById(R.id.description_textView);

			colorView = view.findViewById(R.id.color_view);

			view.setOnLongClickListener(this);
			view.setOnClickListener(this);
		}

		void onBind(final int position) {

			this.position = position;

			title.setText(arrayList.get(position).getTitle());
			description.setText(arrayList.get(position).getDesc());

			colorView.setBackgroundColor(arrayList.get(position).getColor());
		}

		@Override
		public boolean onLongClick(View view) {

			Log.d(TAG, "onLongClick: position : " + position);

			if (!arrayList.get(position).getTitle().equals("BASE")
					&& !arrayList.get(position).getDesc().equals("BASE Backlog")
					&& arrayList.get(position).getColor() != -1)
				Utils.goTo(inflater.getContext(), Type.EDIT_BACKLOG, position);

			else Snackbar.make(view, "this not provider for Edit", Snackbar.LENGTH_SHORT).show();

			return true;
		}

		@Override
		public void onClick(View view) {
			Log.d(TAG, "onClick: position : " + position);

			onBacklogFragment.onClickBacklog(arrayList.get(position).getId());
		}
	}
}
