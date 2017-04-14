package com.amitsin6h.rssreader;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by amitsin6h on 2/22/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<FeedItem>feedItems;
    Context context;


    public MyAdapter(Context context, ArrayList<FeedItem>feedItems){
        this.context=context;
        this.feedItems=feedItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_row_news_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        YoYo.with(Techniques.FadeIn).playOn(holder.cardView);
        final FeedItem current = feedItems.get(position);
        holder.Title.setText(current.getTitle());
        holder.Description.setText(current.getDescription());
        holder.Date.setText(current.getPubDate());
        Picasso.with(context).load(current.getThumbnailUrl()).into(holder.Thumbnail);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Details.class);
                intent.putExtra("Title",current.getTitle());
                intent.putExtra("Image",current.getThumbnailUrl());
                intent.putExtra("Descriptions",current.getDescription());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title,Description,Date;
        ImageView Thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            Title = (TextView) itemView.findViewById(R.id.largeText);
            Description = (TextView) itemView.findViewById(R.id.description_text);
            Date = (TextView) itemView.findViewById(R.id.smallText);
            Thumbnail = (ImageView) itemView.findViewById(R.id.imageView);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
        }
    }
}
