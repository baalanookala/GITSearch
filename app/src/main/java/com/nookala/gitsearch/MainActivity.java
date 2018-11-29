package com.nookala.gitsearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.nookala.gitsearch.adapter.SearchAdapter;
import com.nookala.gitsearch.utils.DividerItem;
import com.nookala.gitsearch.utils.Downloader;
import com.nookala.gitsearch.utils.Github;
import com.nookala.gitsearch.utils.result;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements result, SearchAdapter.ItemClickListener {

    private static final String TAG = MainActivity.class.toString();
    SearchAdapter mAdapter;
    List<GitItem> results = new ArrayList<>();
    EditText searchText;
    Button search;
    List<GitItem> mResults;
    private RecyclerView recyclerView;
    private static Github github;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
        initializeViews();
    }


    private void initializeViews() {
        recyclerView = findViewById(R.id.recycler_view);
        searchText = findViewById(R.id.textSearch);
        search = findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                displaySearchResults();
            }
        });
        setUpRecyclerView();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("Results", github);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Github github = savedInstanceState.getParcelable("Results");
        if (null != github && mAdapter != null) {
            mAdapter.updateResults(github.getItems());
            mAdapter.notifyDataSetChanged();
        }

    }


    public void displayResults(JSONObject jsn) {
        Gson gson = new Gson();
        github = gson.fromJson(String.valueOf(jsn), Github.class);
        Log.d(TAG, "Response: " + github.toString());
        mResults = github.getItems();
        if (mAdapter != null) {
            mAdapter.updateResults(mResults);
            mAdapter.notifyDataSetChanged();
        }


    }

    private void setUpRecyclerView() {
        mAdapter = new SearchAdapter(getApplicationContext(), this);

        if (recyclerView != null && mAdapter != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.addItemDecoration(new DividerItem(this));
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mAdapter);
        }
    }


    public void displaySearchResults() {
        String search = searchText.getText().toString();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null && null != search) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected() && (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE || networkInfo.getType() == ConnectivityManager.TYPE_WIFI)) {
                new Downloader(this).execute(search);
            }
        }
    }

    @Override
    public void onItemClicked(int position) {

        Log.d(TAG, "Item Clicked");
    }
}