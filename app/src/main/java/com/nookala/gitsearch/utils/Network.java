package com.nookala.gitsearch.utils;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Network {

    private final static String BASE_URL = "https://api.github.com/search/repositories";
    private final static String PARAM_QUERY = "q";
    private final static String PARAM_SORT = "sort";
    private final static String SORT_BY = "stars";
    private final static String ORDER_BY = "order";
    private final static String ORDER = "desc";


    public static URL buildURL(String searchText) {
        Uri uri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(PARAM_QUERY, searchText).appendQueryParameter(PARAM_SORT, SORT_BY).
                appendQueryParameter(ORDER_BY, ORDER).build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        return url;
    }

    public static JSONObject downloadResults(URL url) throws IOException, JSONException {
        JSONObject jsonObject = null;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(3000);
        connection.setRequestMethod("GET");
        connection.connect();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String inputdata;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputdata = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputdata);
            }

            jsonObject = new JSONObject(stringBuilder.toString());

        }

        return jsonObject;
    }

}
