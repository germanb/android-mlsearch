package com.mlsearch2.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    public final static String QUERY = "com.mlsearch2.app.QUERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Restore query
        SharedPreferences settings = getPreferences(0);
        String query = settings.getString("query", "");
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(query, TextView.BufferType.EDITABLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void doSearch(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);

        if( editText.getText().toString().trim().equals(""))
        {
            editText.setError( "Required!" );
        }
        else {
            Intent intent = new Intent(this, ListActivity.class);
            String query = editText.getText().toString();

            SharedPreferences settings = getPreferences(0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("query", query);
            editor.commit();

            intent.putExtra(QUERY, query);
            startActivity(intent);
        }
    }

}
