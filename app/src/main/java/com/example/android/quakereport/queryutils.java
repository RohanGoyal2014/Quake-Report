package com.example.android.quakereport;

import android.util.Log;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * Created by ardulous on 11/11/17.
 */

public class queryutils {

        /**
         * Create a private constructor because no one should ever create a {@link QueryUtils} object.
         * This class is only meant to hold static variables and methods, which can be accessed
         * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
         */
        private queryutils() {
        }
        public static URL getURLfromString(String string)
        {
            URL url=null;
            try
            {
                url = new URL(string);
            }
            catch(MalformedURLException e)
            {
                Log.e("URLException","The URL provided to getURLfromString() is incorrect");
                return null;
            }
            return url;
        }
        public static String getJSONResponse(String queryString)
        {
            URL url=getURLfromString(queryString);
            StringBuilder stringBuilder=new StringBuilder();
            if(url!=null)
            {
                try {
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(10000);
                    urlConnection.setConnectTimeout(15000);
                    urlConnection.connect();
                    int httpCode=urlConnection.getResponseCode();
                    if(httpCode==200)
                    {
                        try {
                            InputStream inputStream = urlConnection.getInputStream();
                            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            String line=bufferedReader.readLine();
                            while(line!=null)
                            {
                                stringBuilder.append(line);
                                line=bufferedReader.readLine();
                            }
                        }
                        catch (IOException e)
                        {
                            Log.e("InputStreamError","Input Stream could not be retreived");
                            return null;
                        }
                    }
                    else
                    {
                        Log.e("HttpCodeError",String.valueOf(httpCode).concat(" value received"));
                        return null;
                    }
                }
                catch (IOException e)
                {
                    Log.e("URLConnection","Connection could not be established");
                    return null;
                }
            }
            return stringBuilder.toString();
        }

        /**
         * Return a list of {@link Earthquake} objects that has been built up from
         * parsing a JSON response.
         */
        public static ArrayList<entity> extractEarthquakes(String SAMPLE_JSON_RESPONSE) {

            // Create an empty ArrayList that we can start adding earthquakes to
            ArrayList<entity> earthquakes = new ArrayList<>();

            // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.
            try {
                // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
                // build up a list of Earthquake objects with the corresponding data.
                JSONObject root=new JSONObject(SAMPLE_JSON_RESPONSE);
                JSONArray features=root.getJSONArray("features");
                for(int i=0;i<features.length();++i)
                {
                    JSONObject object=features.getJSONObject(i);
                    object=object.getJSONObject("properties");
                    String url=object.getString("url");
                    double mag=object.getDouble("mag");
                    String place=object.getString("place");
                    int pos=place.indexOf("of ",0)+2;
                    String desc=place.substring(0,pos);
                    desc=desc.toUpperCase();
                    StringBuilder build=new StringBuilder();
                    build.append(desc).append("\n").append(place.substring(pos+1,place.length()));
                    if(pos>1)
                    {
                        place=build.toString();
                    }
                    long date=object.getLong("time");
                    Date dated=new Date(date);
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM DD, yyyy\nhh:mm a");
                    String dateToDisplay=simpleDateFormat.format(dated);
                    earthquakes.add(new entity(mag,place,dateToDisplay,url));


                }
            }
            catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
                return null;
            }

            // Return the list of earthquakes
            return earthquakes;
        }

    }
