package com.example.www.rssreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.www.rssreader.Adapters.FeedListAdapter;
import com.example.www.rssreader.Models.Item;
import com.example.www.rssreader.Services.GetCbcTopStoriesAsync;

import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        ListView listview = (ListView) findViewById(R.id.feed_list);

        ArrayList<Item> items = new ArrayList<Item>();

        FeedListAdapter feedListAdapter = new FeedListAdapter(items, R.layout.feed_row);
        listview.setAdapter(feedListAdapter);
        new GetCbcTopStoriesAsync(items, feedListAdapter).execute("");

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) parent.getAdapter().getItem(position);
                Intent intent = new Intent(parent.getContext(), WebActivity.class);
                intent.putExtra("link", item.Link);
                startActivity(intent);
            }
        });
    }
}
