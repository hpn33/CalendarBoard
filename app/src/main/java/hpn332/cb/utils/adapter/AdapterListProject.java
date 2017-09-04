package hpn332.cb.utils.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hpn332.cb.R;
import hpn332.cb.ui.activity.EditActivity;
import hpn332.cb.ui.activity.ListTaskActivity;
import hpn332.cb.utils.Key;
import hpn332.cb.utils.model.ProjectStructure;

public class AdapterListProject extends RecyclerView.Adapter<AdapterListProject.ItemHolder> {

	private LayoutInflater              inflater;
	private ArrayList<ProjectStructure> arrayList;

	public AdapterListProject(Context context, ArrayList<ProjectStructure> arrayList) {

		this.arrayList = arrayList;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		return new ItemHolder(inflater.inflate(R.layout.row_item_project, parent, false));
	}

	@Override
	public void onBindViewHolder(final ItemHolder holder, int position) {holder.onBind(position);}

	@Override
	public int getItemCount() {return arrayList.size();}

	class ItemHolder extends RecyclerView.ViewHolder
			implements View.OnClickListener, View.OnLongClickListener {

		private TextView title, count_task;
		private int position;

		ItemHolder(View view) {
			super(view);

			title = view.findViewById(R.id.title_textView);
			count_task = view.findViewById(R.id.count_task_textView);

			view.setOnClickListener(this);
			view.setOnLongClickListener(this);
		}

		void onBind(int position) {

			this.position = position;

			title.setText(arrayList.get(position).getTitle());
			count_task.setText(arrayList.get(position).getDesc());
		}


		@Override
		public boolean onLongClick(View view) {

			inflater.getContext().startActivity(
					new Intent(inflater.getContext(), EditActivity.class)
							.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
							.putExtra(Key.KEY_TYPE, EditActivity.PROJECT)
							.putExtra(Key.KEY_POSITION, position));

			return true;
		}

		@Override
		public void onClick(View view) {
			inflater.getContext().startActivity(
					new Intent(inflater.getContext(), ListTaskActivity.class)
							.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
							.putExtra(Key.KEY_PROJECT, arrayList.get(position).getId()));
		}
	}
}
