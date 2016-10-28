package com.example.mbalza.forumslul2016;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mbalza on 10/18/16.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    String[] mDataSet = {"Lorem ipsum 1","Lorem impsum 2"};


    public MyAdapter() {
    }

    public MyAdapter(String data)
    {
        mDataSet = data.split(";");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView mcardview;
        public TextView textview;
        public ViewHolder(CardView v) {
            super(v);
            textview = (TextView) v.findViewById(R.id.textView3);
            mcardview = v;
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardviewlayout, parent, false);

        ViewHolder vh = new ViewHolder((CardView) v);
        return vh;


    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

        holder.textview.setText(mDataSet[position]);

    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
