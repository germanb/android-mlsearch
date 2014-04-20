package com.http;

import android.content.Context;

import org.apache.http.client.methods.HttpUriRequest;
import com.http.AsyncHttpTask;

public abstract class HttpHandler {

    public abstract HttpUriRequest getHttpRequestMethod();

    public abstract void onResponse(String result);

    public void execute(Context ctx){
        new AsyncHttpTask(this, ctx).execute();
    }
}