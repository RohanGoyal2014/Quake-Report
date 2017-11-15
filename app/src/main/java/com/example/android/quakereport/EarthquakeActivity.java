/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<ArrayList<entity>>{

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    public static String URLString = new String("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10");
    ListView earthquakeListView;
    EarthquakeAdapter Eadapter;
    TextView textView;
    ProgressBar progressBar;
    @Override
    public Loader<ArrayList<entity>> onCreateLoader(int i, Bundle bundle) {
        Log.e("Loader","Inside oncreateloader----------------------------------");
        return new NetworkConnectionLoaderThread(this,URLString);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<entity>> loader, ArrayList<entity> entities) {
        Log.e("Loader","insde onfinished and updating ui----------------------------------");
        Eadapter=new EarthquakeAdapter(this,entities);
        textView.setText("Nothing to show...");
        progressBar.setVisibility(View.GONE);
        updateUI();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<entity>> loader) {
        Log.e("Loader","Inside reset----------------------------------");
        Eadapter.clear();
        Eadapter=new EarthquakeAdapter(this,null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        earthquakeListView = (ListView) findViewById(R.id.list);
        textView=(TextView)findViewById(R.id.cover);
        earthquakeListView.setEmptyView(textView);
        progressBar=(ProgressBar)findViewById(R.id.pbar);
        ConnectivityManager connectivityManager;
        connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null) {
            getLoaderManager().initLoader(0, null, this);
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            textView.setText("No Internet Available");
        }

    }

    private void updateUI()
    {
        if(earthquakeListView==null || Eadapter==null)
        {
            Log.e("UIUpdateProblem","Problem in updating UI from thread");
        }
        earthquakeListView.setAdapter(Eadapter);
    }
}