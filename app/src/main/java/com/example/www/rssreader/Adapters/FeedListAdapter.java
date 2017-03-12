package com.example.www.rssreader.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.www.rssreader.Models.Item;
import com.example.www.rssreader.R;
import com.example.www.rssreader.Services.GetImageFromUrlAsync;

import java.util.List;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

public class FeedListAdapter extends BaseAdapter {

    int _layoutId;
    List<Item> Items;

    public FeedListAdapter(List<Item> items, int layoutId) {
        _layoutId = layoutId;
        Items = items;
    }

    @Override
    public int getCount() {
        return Items.size();
    }

    @Override
    public Item getItem(int position) {
        return Items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView title;
        TextView pubDate;
        TextView author;
        GetImageFromUrlAsync asyncTask;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            view = layoutInflater.inflate(_layoutId, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.pubDate = (TextView) view.findViewById(R.id.pubDate);
            viewHolder.author = (TextView) view.findViewById(R.id.author);
            viewHolder.asyncTask = new GetImageFromUrlAsync(viewHolder.imageView);//test
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Item item = getItem(position);
        if (item != null) {
            viewHolder.asyncTask.cancel(true);
            viewHolder.asyncTask = new GetImageFromUrlAsync(viewHolder.imageView);
            viewHolder.asyncTask.executeOnExecutor(THREAD_POOL_EXECUTOR,item.Image);
            viewHolder.title.setText(item.Title);
            viewHolder.pubDate.setText(item.PubDate);
            viewHolder.author.setText(item.Author);
        }

        return view;
    }
}
