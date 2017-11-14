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

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    public static String URLString = new String("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10");
    ListView earthquakeListView;
    EarthquakeAdapter Eadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        //ArrayList<entity> earthquakes = new ArrayList<>();
        //earthquakes=queryutils.extractEarthquakes();

        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = (ListView) findViewById(R.id.list);


        //Eadapter=new EarthquakeAdapter(this,earthquakes);
        new NetworkConnection().execute(URLString);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        //earthquakeListView.setAdapter(Eadapter);
    }
    private void updateUI()
    {
        if(earthquakeListView==null || Eadapter==null)
        {
            Log.e("UIUpdateProblem","Problem in updating UI from thread");
        }
        earthquakeListView.setAdapter(Eadapter);
    }
    private class NetworkConnection extends AsyncTask<String,Void,ArrayList<entity>>
    {
        @Override
        protected ArrayList<entity> doInBackground(String... strings) {
            if(strings.length<1 || strings[0]==null)
            {
                return null;
            }
            //URL url=queryutils.getURLfromString(strings[0]);
            String JSONResponse=queryutils.getJSONResponse(strings[0]);
            ArrayList<entity> arrayList=new ArrayList<>();
            if(JSONResponse!=null) {
                arrayList = queryutils.extractEarthquakes(JSONResponse);
            }
            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<entity> equakes) {
            super.onPostExecute(equakes);
            if(equakes==null)
            {
                return;
            }
            Eadapter=new EarthquakeAdapter(EarthquakeActivity.this,equakes);
            updateUI();
        }
    }
}