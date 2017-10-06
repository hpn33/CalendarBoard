package hpn332.cb.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hpn332.cb.R;
import hpn332.cb.utils.Utils;
import hpn332.cb.model.stucture.TagStructure;
import hpn332.cb.utils.U.Type;

public class AdapterListTag extends RecyclerView.Adapter<AdapterListTag.ItemHolder> {

	private LayoutInflater          inflater;
	private ArrayList<TagStructure> arrayList;

	public AdapterListTag(
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

		ItemHolder(View view) {
			super(view);

			title = view.findViewById(R.id.title_textView);
			description = view.findViewById(R.id.description_textView);
			color_view = view.findViewById(R.id.color_view);

			view.setOnLongClickListener(this);
		}

		void onBind(int position) {

			this.position = position;

			title.setText(arrayList.get(position).getTitle());
			description.setText(arrayList.get(position).getDesc());


			color_view.setBackgroundColor(arrayList.get(position).getColor());
		}

		@Override
		public boolean onLongClick(View view) {

			Utils.goTo(inflater.getContext(), Type.EDIT_TAG, position);

			return true;
		}
	}
}
