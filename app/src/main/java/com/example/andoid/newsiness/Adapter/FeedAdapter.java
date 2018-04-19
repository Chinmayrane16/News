package com.example.andoid.newsiness.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andoid.newsiness.Interface.ItemClickListener;
import com.example.andoid.newsiness.Model.RSSObject;
import com.example.andoid.newsiness.R;

import java.util.List;

/**
 * Created by Chinmay on 18-Apr-18.
 */


class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
{

    public TextView mTitle , mDate , mContent;
    private ItemClickListener itemClickListener;



    public FeedViewHolder(View itemView) {
        super(itemView);

        mTitle = itemView.findViewById(R.id.row_title);
        mDate = itemView.findViewById(R.id.row_date);
        mContent = itemView.findViewById(R.id.row_content);

        //  SET EVENT
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}



public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder>{

    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater inflater;

    public FeedAdapter(RSSObject rssObject, Context mContext) {
        this.rssObject = rssObject;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.row, parent, false);
        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        holder.mTitle.setText(rssObject.getItems().get(position).getTitle());
        holder.mDate.setText(rssObject.getItems().get(position).getPubDate());
        holder.mContent.setText(rssObject.getItems().get(position).getContent());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(!isLongClick)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW , Uri.parse(rssObject.getItems().get(position).getLink()));
                    mContext.startActivity(browserIntent);
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return rssObject.items.size();
    }
}
