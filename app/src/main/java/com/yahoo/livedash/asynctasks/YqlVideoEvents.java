package com.yahoo.livedash.asynctasks;

import android.os.AsyncTask;

import com.yahoo.livedash.handlers.EventsCallbackHandler;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.security.auth.callback.CallbackHandler;

/**
 * Created by josephr on 9/30/14.
 */
public class YqlVideoEvents extends AsyncTask<String, String, String>{
    EventsCallbackHandler handler;

    public YqlVideoEvents(EventsCallbackHandler handler) {
        this.handler = handler;
    }

    @Override
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(uri[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        JSONArray json_result = null;
        try {
            json_result = new JSONObject(result).getJSONObject("events").getJSONArray("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.handler.onSuccess(json_result);

    }

}
