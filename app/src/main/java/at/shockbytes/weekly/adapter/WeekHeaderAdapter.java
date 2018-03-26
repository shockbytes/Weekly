package at.shockbytes.weekly.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.shockbytes.weekly.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeekHeaderAdapter extends RecyclerView.Adapter<WeekHeaderAdapter.RemoteViewHolder> {

    private final ArrayList<String> data = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private final LayoutInflater inflater;

    public  interface OnItemClickListener{

        void onItemClick(CharSequence entity);
    }

    public WeekHeaderAdapter(Context cxt){
        inflater = LayoutInflater.from(cxt);
        setData(Arrays.asList(cxt.getResources().getStringArray(R.array.week_days_short)));
    }

    @Override
    public WeekHeaderAdapter.RemoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RemoteViewHolder(inflater.inflate(R.layout.adapter_week_header, parent, false));
    }

    @Override
    public void onBindViewHolder(WeekHeaderAdapter.RemoteViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    private void moveEntity(int i, int dest){
        String temp = data.remove(i);
        data.add(dest, temp);
        notifyItemMoved(i, dest);
    }

    public void addEntity(int i, String entity){
        data.add(i, entity);
        notifyItemInserted(i);
    }

    private void deleteEntity(int i){
        data.remove(i);
        notifyItemRemoved(i);
    }

    public void setData(List<String> data){

        if(data == null){
            return;
        }

        //Remove all deleted items
        for(int i = this.data.size() - 1; i >= 0; --i){
            //Remove all deleted items
            if(getLocation(data, this.data.get(i)) < 0){
                deleteEntity(i);
            }
        }

        //Add and move items
        for(int i = 0; i < data.size(); ++i){
            String entity = data.get(i);
            int location = getLocation(this.data, entity);
            if(location < 0){
                addEntity(i, entity);
            }
            else if(location != i){
                moveEntity(i, location);
            }
        }
        notifyDataSetChanged();
    }

    private int getLocation(List<String> data, String entity){

        for(int j = 0; j < data.size(); ++j){
            String newEntity = data.get(j);
            if(entity.equals(newEntity)){
                return j;
            }
        }

        return -1;
    }

    public class RemoteViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.adapter_weekheader_text)
        protected TextView textTitle;

        public RemoteViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                    }
                }
            });

        }

        public void bind(String entity){
            textTitle.setText(entity);
        }
    }

}


