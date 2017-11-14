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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<ArrayList<entity>>{

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    public static String URLString = new String("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10");
    ListView earthquakeListView;
    EarthquakeAdapter Eadapter;

    @Override
    public Loader<ArrayList<entity>> onCreateLoader(int i, Bundle bundle) {
        return new NetworkConnectionLoaderThread(this,URLString);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<entity>> loader, ArrayList<entity> entities) {
        Eadapter=new EarthquakeAdapter(this,entities);
        updateUI();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<entity>> loader) {
        Eadapter.clear();
        Eadapter=new EarthquakeAdapter(this,null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        earthquakeListView = (ListView) findViewById(R.id.list);
        getLoaderManager().initLoader(0,null,this);

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