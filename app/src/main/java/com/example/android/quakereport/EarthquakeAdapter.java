package com.example.android.quakereport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ardulous on 10/11/17.
 */

public class EarthquakeAdapter extends ArrayAdapter{
    public EarthquakeAdapter(Activity context, ArrayList<entity> arr)
    {
        super(context,0,arr);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull final ViewGroup parent)
    {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        final entity currEntity=(entity) getItem(position);
        TextView mag=(TextView)listItemView.findViewById(R.id.richter);
        TextView place=(TextView)listItemView.findViewById(R.id.place);
        TextView date=(TextView)listItemView.findViewById(R.id.date);


        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();
        int desiredMagnitudeBackground=getMagnitudeColor(currEntity.getEmagnitude());
        magnitudeCircle.setColor(desiredMagnitudeBackground);
        mag.setText(String.valueOf(currEntity.getEmagnitude()));
        place.setText(currEntity.getEplace());
        date.setText(currEntity.getEdate());
        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(currEntity.getEurl()));
                getContext().startActivity(intent);
            }
        });
        return listItemView;
    }
    int getMagnitudeColor(double value)
    {
        //variable color actually holds a resource id
        int color;
        if(value>=10)
        {
            color=R.color.magnitude10plus;
        }
        else if(value>=9)
        {
            color=R.color.magnitude9;
        }
        else if(value>=8)
        {
            color=R.color.magnitude8;
        }
        else if(value>=7)
        {
            color=R.color.magnitude7;
        }
        else if(value>=6)
        {
            color=R.color.magnitude6;
        }
        else if(value>=5)
        {
            color=R.color.magnitude5;
        }
        else if(value>=4)
        {
            color=R.color.magnitude4;
        }
        else if(value>=3)
        {
            color=R.color.magnitude3;
        }
        else if(value>=2)
        {
            color=R.color.magnitude2;
        }
        else
        {
            color=R.color.magnitude1;
        }
        return ContextCompat.getColor(getContext(), color);
    }
}
