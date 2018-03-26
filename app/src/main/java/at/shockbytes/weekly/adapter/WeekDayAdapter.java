package at.shockbytes.weekly.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.shockbytes.weekly.R;
import at.shockbytes.weekly.task.model.Task;
import at.shockbytes.weekly.view.InformationChipView;
import at.shockbytes.weekly.view.ItemTouchHelperAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeekDayAdapter extends RecyclerView.Adapter<WeekDayAdapter.RemoteViewHolder>
        implements ItemTouchHelperAdapter {

    private final List<Task> data = new ArrayList<>();
    private final LayoutInflater inflater;

    private OnItemMoveListener onItemMoveListener;
    private OnItemClickListener onItemClickListener;

    @Override
    public boolean onItemMove(int from, int to) {

        if (from < to) {
            for (int i = from; i < to; i++) {
                Collections.swap(data, i, i + 1);
            }
        } else {
            for (int i = from; i > to; i--) {
                Collections.swap(data, i, i - 1);
            }
        }
        notifyItemMoved(from, to);
        if (onItemMoveListener != null) {
            onItemMoveListener.onItemMove(data.get(to), from, to);
        }
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        Task entry = data.remove(position);
        if (onItemMoveListener != null) {
            onItemMoveListener.onItemDismissed(entry);
        }
        notifyItemRemoved(position);
    }

    public interface OnItemClickListener {
        void onItemClick(Task entity);
    }

    public interface OnItemMoveListener {

        void onItemMove(Task entry, int from, int to);

        void onItemDismissed(Task entry);
    }

    public WeekDayAdapter(Context cxt, List<Task> data) {
        inflater = LayoutInflater.from(cxt);
        setData(data);
    }

    @Override
    public WeekDayAdapter.RemoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RemoteViewHolder(inflater.inflate(R.layout.adapter_weekday_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(WeekDayAdapter.RemoteViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public void setOnItemMoveListener(OnItemMoveListener listener) {
        onItemMoveListener = listener;
    }

    public void addEntity(int i, Task entity) {
        data.add(i, entity);
        notifyItemInserted(i);
    }

    private void deleteEntity(int i) {
        data.remove(i);
        notifyItemRemoved(i);
    }

    private void moveEntity(int i, int dest) {
        Task temp = data.remove(i);
        data.add(dest, temp);
        notifyItemMoved(i, dest);
    }

    public void setData(List<Task> data) {

        if (data == null) {
            return;
        }

        //Remove all deleted items
        for (int i = this.data.size() - 1; i >= 0; --i) {
            //Remove all deleted items
            if (getLocation(data, this.data.get(i)) < 0) {
                deleteEntity(i);
            }
        }

        //Add and move items
        for (int i = 0; i < data.size(); ++i) {
            Task entity = data.get(i);
            int location = getLocation(this.data, entity);
            if (location < 0) {
                addEntity(i, entity);
            } else if (location != i) {
                moveEntity(i, location);
            }
        }
        notifyDataSetChanged();
    }

    private int getLocation(List<Task> data, Task entity) {

        for (int j = 0; j < data.size(); ++j) {
            Task newEntity = data.get(j);
            if (entity.equals(newEntity)) {
                return j;
            }
        }

        return -1;
    }

    public class RemoteViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.adapter_wde_txt_title)
        protected TextView textTitle;

        @BindView(R.id.adapter_wde_txt_content)
        protected TextView textContent;

        @BindView(R.id.adapter_wde_layout_chips)
        protected LinearLayout chipsContainer;

        public RemoteViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                    }
                }
            });
        }

        public void bind(Task entity) {
            textTitle.setText(entity.getTitle());
            textContent.setText(entity.getContent());

            //Clear all chips first
            chipsContainer.removeAllViews();

            if (entity.isImportant()) {
                addChip("Important", R.mipmap.ic_chip_important);
            }
            if (entity.isReminderSet()) {
                addChip("18:00", R.mipmap.ic_chip_reminder);
            }
            if (entity.hasLabel()) {
                addChip(entity.getLabel(), R.mipmap.ic_chip_label);
            }

        }

        private void addChip(String title, int image) {
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            InformationChipView chipView1 = new InformationChipView(inflater.getContext());
            chipView1.setTextAndImage(title, image);
            chipsContainer.addView(chipView1);
        }
    }

}


