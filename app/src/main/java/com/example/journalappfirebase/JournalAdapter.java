package com.example.journalappfirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {
    ArrayList<FeedModel> list;

    Context context;

    public JournalAdapter(ArrayList<FeedModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.feed_item,parent,false);
        return new JournalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        FeedModel model=list.get(position);
        holder.thoughtsTextView.setText(model.getThoughts());
        holder.titleTextView.setText(model.getTitle());
        holder.feedImageView.setImageBitmap(model.getImageview());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class JournalViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView,thoughtsTextView;
        ImageView feedImageView;

        public JournalViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView=itemView.findViewById(R.id.titleTextView);
            thoughtsTextView=itemView.findViewById(R.id.thoughtsTextView);
            feedImageView=itemView.findViewById(R.id.feedImageView);
        }
    }
}
