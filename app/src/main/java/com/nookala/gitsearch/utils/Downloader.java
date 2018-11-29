package com.nookala.gitsearch.utils;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * Created by nookaba on 11/24/2018.
 */


public class Downloader extends AsyncTask<String, Void, JSONObject> {

    private result resultDisplayInt;

    public Downloader(result resultDisplayInt) {
        this.resultDisplayInt = resultDisplayInt;
    }


    @Override
    protected JSONObject doInBackground(String... params) {
        try {
            URL url = Network.buildURL(params[0]);
            return Network.downloadResults(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        if (jsonObject != null) {
            this.resultDisplayInt.displayResults(jsonObject);
        }
    }
}
