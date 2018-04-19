package com.example.andoid.newsiness;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.example.andoid.newsiness.Adapter.FeedAdapter;
import com.example.andoid.newsiness.Common.HTTPDataHandler;
import com.example.andoid.newsiness.Model.RSSObject;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar mToolbar;
    RecyclerView recyclerView;
    RSSObject rssObject;

    private final String RSS_link = "http://rss.nytimes.com/services/xml/rss/nyt/Science.xml";
    private  final String RSS_to_JSON_API = "https://api.rss2json.com/v1/api.json?rss_url=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.main_toolbar);
        mToolbar.setTitle("News");
        setSupportActionBar(mToolbar);

        recyclerView = findViewById(R.id.main_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadRSS();


    }


    private void loadRSS() {

        AsyncTask<String, String, String > loadRssAsync = new AsyncTask<String, String, String>() {

            ProgressDialog progressDialg = new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {
                progressDialg.setMessage("Please Wait");
                progressDialg.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                String result;
                HTTPDataHandler http = new HTTPDataHandler();
                result = http.GetHTTPData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
               progressDialg.hide();
               rssObject = new Gson().fromJson(s ,RSSObject.class );  //  CONVERT RSS TO JSON
                FeedAdapter adapter = new FeedAdapter(rssObject , getBaseContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };

        StringBuilder url_get_data = new StringBuilder(RSS_to_JSON_API);
        url_get_data.append(RSS_link);
        loadRssAsync.execute(url_get_data.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_refresh)
        {
            loadRSS();
        }
        return true;
    }
}
