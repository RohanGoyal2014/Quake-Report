package com.example.android.quakereport;

import static com.example.android.quakereport.R.id.richter;

/**
 * Created by ardulous on 10/11/17.
 */

public class entity {
    private double emagnitude;
    private String eplace,edate,eurl;
    public entity(double richter,String place,String Date,String url)
    {
        emagnitude=richter;
        eplace=place;
        edate=Date;
        eurl=url;
    }
    public double getEmagnitude()
    {
        return emagnitude;
    }
    public String getEplace()
    {
        return eplace;
    }
    public String getEdate()
    {
        return edate;
    }
    public String getEurl()
    {
        return eurl;
    }
}
