package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ardulous on 15/11/17.
 */

public class NetworkConnectionLoaderThread extends AsyncTaskLoader<ArrayList<entity>>
{
    private String murl;
    public NetworkConnectionLoaderThread(Context context, String url)
    {
        super(context);
        murl=url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<entity> loadInBackground() {
        if(murl==null)
        {
            return null;
        }
        String JSONResponse=queryutils.getJSONResponse(murl);
        ArrayList<entity> arrayList=new ArrayList<>();
        if(JSONResponse!=null) {
            arrayList = queryutils.extractEarthquakes(JSONResponse);
        }
        return arrayList;
    }
}