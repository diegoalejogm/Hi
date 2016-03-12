package com.fbteam.hi.adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fbteam.hi.App;
import com.fbteam.hi.R;
import com.fbteam.hi.models.Category;

import java.util.ArrayList;


public class CategoryListAdapter extends ArrayAdapter<Category>{

    private int resource;
    private LayoutInflater inflater;
    private Context context;

    public CategoryListAdapter(Context ctx, int resourceId)
    {
        super(ctx, resourceId, App.getMe().getCategories());
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context  = ctx;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent )
    {
    	convertView = (RelativeLayout) inflater.inflate( resource, null );
        Category category = App.getMe().getCategories().get(position);

//        System.out.println(position + " " + shout.getContent());
//        if(position % 2 == 1)
//        {
//            RelativeLayout bg = (RelativeLayout)convertView.findViewById(R.id.feedsLayoutBg);
//        	bg.setBackgroundColor(Color.parseColor("#F3F3F3"));
//        }

        View tempShoutViewObject = (TextView)convertView.findViewById(R.id.linkName);
        ((TextView)tempShoutViewObject).setText(category.getName());

//        AppManager.fontTextView(nameLabel, 23);

//        TriggerButtonUI ten = new TriggerButtonUI( (Button)convertView.findViewById(R.id.ten), activityName.addPurchase );
//        ten.setId(position);
//        TriggerButtonUI twenty = new TriggerButtonUI( (Button)convertView.findViewById(R.id.twenty), activityName.addPurchase);
//        twenty.setId(position);
        return convertView;
    }
}
