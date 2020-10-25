package com.example.lifestyle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRVAdapter extends RecyclerView.Adapter<MyRVAdapter.ViewHolder> {
    private List<StepRecordData> mListItems = new ArrayList<>();
    private Context mContext;

    public MyRVAdapter() {
//        mListItems = inputList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        protected View itemLayout;
        protected TextView itemTvStartTime;
        protected TextView itemTvEndTime;
        protected TextView itemTvStepCount;

        public ViewHolder(View view){
            super(view);
            itemLayout = view;
            itemTvStartTime = (TextView) view.findViewById(R.id.tv_start_time);
            itemTvEndTime = (TextView) view.findViewById(R.id.tv_end_time);
            itemTvStepCount = (TextView) view.findViewById(R.id.tv_step_count_history);
        }
    }

    @NonNull
    @Override
    public MyRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View myView = layoutInflater.inflate(R.layout.step_count_list_item,parent,false);
        return new ViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.itemTvStartTime.setText(mListItems.get(position).getStartTime());
        holder.itemTvEndTime.setText(mListItems.get(position).getEndTime());
        holder.itemTvStepCount.setText(Integer.toString(mListItems.get(position).getStepCount()));
//        holder.itemLayout.setOnClickListener(new View.OnClickListener(){
//                                                 @Override
//                                                 public void onClick(View view) {
//                                                     remove(position);
//                                                 }
//                                             }
//        );
    }

//    public void remove(int position){
//        mListItems.remove(position);
//        notifyItemRemoved(position);
//    }


    public void setData(List<StepRecordData> newData) {
        this.mListItems = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

}
