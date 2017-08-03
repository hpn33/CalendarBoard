package hpn332.cb.Tag;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hpn332.cb.R;
import hpn332.cb.Utils.TagStructure;


class AdapterTagList extends RecyclerView.Adapter<AdapterTagList.ItemHolder> {

	private LayoutInflater          inflater;
	private ArrayList<TagStructure> arrayList;

	AdapterTagList(
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

	class ItemHolder extends RecyclerView.ViewHolder {

		private TextView title, description;
		private View color_view;

		ItemHolder(View view) {
			super(view);

			title = view.findViewById(R.id.title_textView);
			description = view.findViewById(R.id.description_textView);
			color_view = view.findViewById(R.id.color_view);
		}

		void onBind(int position) {

			title.setText(arrayList.get(position).getTitle());
			description.setText(arrayList.get(position).getDesc());
			color_view.setBackgroundResource(getColor(arrayList.get(position).getColor()));
		}

		int getColor(int color) {

			switch (color) {
				case 1:
					return R.color.green;
				case 2:
					return R.color.orange;
				case 3:
					return R.color.blue;
				case 4:
					return R.color.gold;

				default:
					return R.color.green;
			}
		}
	}
}
