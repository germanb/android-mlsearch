package com.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import com.http.HttpHandler;
import com.mlsearch2.app.ListActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncHttpTask extends AsyncTask<String, Void, String>{
    private volatile boolean running = true;
    private final ProgressDialog progressDialog;
    private HttpHandler httpHandler;
    public AsyncHttpTask(HttpHandler httpHandler, Context ctx){

        this.httpHandler = httpHandler;
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Loading...");

        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // actually could set running = false; right here, but I'll
                // stick to contract.
                cancel(true);
            }
        });
    }

    @Override
    protected String doInBackground(String... arg0) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make the http request
            HttpResponse httpResponse = httpclient.execute(httpHandler.getHttpRequestMethod());

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }
    @Override
    protected void onPostExecute(String result) {
        httpHandler.onResponse(result);
        progressDialog.dismiss();
    }

    //--------------------------------------------------------------------------------------------
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();


        return result;
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }
}