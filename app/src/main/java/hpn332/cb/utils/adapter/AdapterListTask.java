package hpn332.cb.utils.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import hpn332.cb.R;
import hpn332.cb.ui.activity.EditActivity;
import hpn332.cb.ui.fragment.TaskFragment;
import hpn332.cb.utils.AList;
import hpn332.cb.utils.Key;
import hpn332.cb.utils.database.ProviderHelper;
import hpn332.cb.utils.model.TaskStructure;


public class AdapterListTask extends RecyclerView.Adapter<AdapterListTask.ItemHolder> {

	private static final String TAG = "AdapterListTask";

	private LayoutInflater              inflater;
	private ArrayList<TaskStructure>    arrayList;
	private int                         step;
	private TaskFragment.OnStepFragment onStepFragment;

	public AdapterListTask(
			Context context, ArrayList<TaskStructure> arrayList, int step,
			TaskFragment.OnStepFragment onStepFragment) {

		this.onStepFragment = onStepFragment;
		this.arrayList = arrayList;
		inflater = LayoutInflater.from(context);
		this.step = step;

		Log.d(TAG, "AdapterListTask: " + step);
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

	class ItemHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

		private TextView title, description, rank;
		private LinearLayout tagLayout;
		private ImageView    nextStep;
		private int          position;

		ItemHolder(View view) {
			super(view);

			title = view.findViewById(R.id.title_textView);
			description = view.findViewById(R.id.description_textView);
			rank = view.findViewById(R.id.rank_textView);

			nextStep = view.findViewById(R.id.next_step_imageView);

			tagLayout = view.findViewById(R.id.show_tag_layout);

			view.setOnLongClickListener(this);
		}

		void onBind(final int position) {

			this.position = position;

			title.setText(arrayList.get(position).getTitle());
			description.setText(arrayList.get(position).getDesc());
			rank.setText(String.valueOf(arrayList.get(position).getRank()));

			if (arrayList.get(position).getStep() < 4) {

				nextStep.setVisibility(View.VISIBLE);

				nextStep.setOnClickListener(view -> {

					ProviderHelper.updateOneTask(
							inflater.getContext(),
							arrayList.get(position).getId(),
							arrayList.get(position).getTitle(),
							arrayList.get(position).getDesc(),
							arrayList.get(position).getTag(),
							(arrayList.get(position).getStep() + 1),
							arrayList.get(position).getRank());

					onStepFragment.onClickNext();
				});
			}


			if (!arrayList.get(position).getTag().isEmpty()) addTagColor();
		}

		private void addTagColor() {

			tagLayout.setVisibility(View.VISIBLE);

			ProviderHelper.queryListTag(inflater.getContext(), AList.L_TAGS);

			String[] tags = arrayList.get(position).getTag().split("`");

			View[] view = new View[tags.length];

			int j = 0;
			for (String s : tags) {

				for (int i = 0; i < AList.L_TAGS.size(); i++) {

					if (s.equals(String.valueOf(AList.L_TAGS.get(i).getId()))) {
						view[j] = inflater.inflate(R.layout.view_tag_color, tagLayout, false);

						//view[j].findViewById(R.id.view_tag_color).setBackgroundResource(Utils.getColor(AList.L_TAGS.get(i).getColor()));

						tagLayout.addView(view[j]);
					}
				}
				j++;
			}

		}

		@Override
		public boolean onLongClick(View view) {

			Log.d(TAG, "onLongClick: position : " + position);

			inflater.getContext().startActivity(
					new Intent(inflater.getContext(), EditActivity.class)
							.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
							.putExtra(Key.KEY_TYPE, EditActivity.TASK)
							.putExtra(Key.KEY_POSITION, position)
							.putExtra(Key.KEY_STEP, step));

			return true;
		}
	}
}