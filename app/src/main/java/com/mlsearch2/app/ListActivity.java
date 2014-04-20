package com.mlsearch2.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.CustomAdapter;
import com.Item;
import com.http.HttpHandler;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ListActivity extends ActionBarActivity {
    String message;
    private static final String ITEMSKEY = "items";
    private ArrayList<Item> items = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.QUERY);

        setContentView(R.layout.activity_list);

        if (savedInstanceState != null) {
            Log.wtf("----", savedInstanceState.getSerializable(ITEMSKEY).toString());
            items = (ArrayList<Item>) savedInstanceState.getSerializable(ITEMSKEY);
            CustomAdapter adapter = new CustomAdapter(ListActivity.this, items);

            ListView listView = (ListView) findViewById(R.id.listView);

            listView.setAdapter(adapter);

        }else{
            executeCallApi();
        }
    }


    private void executeCallApi(){
        new HttpHandler() {
            @Override
            public HttpUriRequest getHttpRequestMethod() {
                return new HttpGet("https://api.mercadolibre.com/sites/MLA/search?q=" + message + "&limit=100");
            }
            @Override
            public void onResponse(String result) {
                JSONObject json;
                try {
                    json = new JSONObject(result);
                    JSONArray articles = json.getJSONArray("results");
                    Toast.makeText(getBaseContext(), "Received " + articles.length() + "results!" , Toast.LENGTH_LONG).show();
                    items = generateData(articles);
                    CustomAdapter adapter = new CustomAdapter(ListActivity.this, items);

                    ListView listView = (ListView) findViewById(R.id.listView);

                    listView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }.execute(this);
    }

    private ArrayList<Item> generateData(JSONArray articles){
        ArrayList<Item> items = new ArrayList<Item>();

        for (int i = 0; i < articles.length(); i++) {
            try {
                articles.getJSONObject(i);
                items.add(new Item(articles.getJSONObject(i).getString("title"),
                        articles.getJSONObject(i).getString("subtitle"),
                        articles.getJSONObject(i).getLong("price")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return items;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(ITEMSKEY, items);
        super.onSaveInstanceState(savedInstanceState);
    }

}
