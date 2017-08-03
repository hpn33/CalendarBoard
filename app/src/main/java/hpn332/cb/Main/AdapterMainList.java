package hpn332.cb.Main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hpn332.cb.R;
import hpn332.cb.Utils.TaskStructure;


class AdapterMainList extends RecyclerView.Adapter<AdapterMainList.ItemHolder> {

	private LayoutInflater           inflater;
	private ArrayList<TaskStructure> arrayList;

	AdapterMainList(
			Context context, ArrayList<TaskStructure> arrayList) {
		this.arrayList = arrayList;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.row_item_task, parent, false);

		return new ItemHolder(view);
	}

	@Override
	public void onBindViewHolder(final ItemHolder holder, int position) {holder.onBind(position);}

	@Override
	public int getItemCount() {
		return arrayList.size();
	}

	class ItemHolder extends RecyclerView.ViewHolder {

		private TextView title, description, rank;

		ItemHolder(View view) {
			super(view);

			title = view.findViewById(R.id.title_textView);
			description = view.findViewById(R.id.description_textView);
			rank = view.findViewById(R.id.rank_textView);
		}

		void onBind(int position) {

			title.setText(arrayList.get(position).getTitle());
			description.setText(arrayList.get(position).getDesc());
			rank.setText(String.valueOf(arrayList.get(position).getRank()));
		}
	}


}
