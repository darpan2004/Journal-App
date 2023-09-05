package com.example.journalappfirebase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
        String base64=model.getImageview();

        // Decode the Base64 string into a byte array
        byte[] imageData = Base64.decode(base64, Base64.DEFAULT);

// Convert the byte array back into a Bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        //vholder.feedImageView.setImageBitmap(bitmap);
        Glide.with(holder.itemView.getContext())
                .load(bitmap)
                .override(100,100)
                .centerCrop()// Replace 'yourBitmap' with the Bitmap you want to load
                .into(holder.feedImageView); // Replace 'yourImageView' with the ImageView where you want to display the Bitmap
        //holder.feedImageView.setImageBitmap(model.getImageview());

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
