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
import hpn332.cb.ui.activity.EditTagActivity;
import hpn332.cb.utils.Key;
import hpn332.cb.utils.model.TagStructure;
import hpn332.cb.utils.Utils;


public class AdapterTagList extends RecyclerView.Adapter<AdapterTagList.ItemHolder> {

	private LayoutInflater          inflater;
	private ArrayList<TagStructure> arrayList;

	public AdapterTagList(
			Context context, ArrayList<TagStructure> arrayList) {
		this.arrayList = arrayList;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.row_item_tag, parent, false);

		return new ItemHolder(view);
	}

	@Override
	public void onBindViewHolder(final ItemHolder holder, int position) {holder.onBind(position);}

	@Override
	public int getItemCount() {
		return arrayList.size();
	}

	class ItemHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

		private TextView title, description;
		private View color_view;
		private int  position;
		private String setText;

		ItemHolder(View view) {
			super(view);

			title = view.findViewById(R.id.title_textView);
			description = view.findViewById(R.id.description_textView);
			color_view = view.findViewById(R.id.color_view);


			setText = arrayList.get(position).getDesc();

			view.setOnLongClickListener(this);
		}

		void onBind(int position) {

			this.position = position;

			title.setText(arrayList.get(position).getTitle());

			if (!setText.equals("") && !setText.isEmpty()) {
				description.setVisibility(View.VISIBLE);
				description.setText(setText);
			}

			color_view.setBackgroundResource(Utils.getColor(arrayList.get(position).getColor()));
		}


		@Override
		public boolean onLongClick(View view) {

			inflater.getContext().startActivity(
					new Intent(inflater.getContext(), EditTagActivity.class)
							.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
							.putExtra(Key.KEY_UPDATE, true)
							.putExtra(Key.KEY_POSITION, position));
			return true;
		}
	}
}
