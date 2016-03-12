package com.fbteam.hi.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fbteam.hi.App;
import com.fbteam.hi.R;
import com.fbteam.hi.models.Category;
import com.fbteam.hi.models.Link;


public class LinksEditAdapter extends ArrayAdapter<Link>{

    private int resource;
    private LayoutInflater inflater;
    private Context context;

    public LinksEditAdapter(Context ctx, int resourceId)
    {
        super(ctx, resourceId, App.getMe().getLinks());
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context  = ctx;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent )
    {
    	convertView = (RelativeLayout) inflater.inflate( resource, null );
        Link link = App.getMe().getLinks().get(position);

//        System.out.println(position + " " + shout.getContent());
//        if(position % 2 == 1)
//        {
//            RelativeLayout bg = (RelativeLayout)convertView.findViewById(R.id.feedsLayoutBg);
//        	bg.setBackgroundColor(Color.parseColor("#F3F3F3"));
//        }

        View tempShoutViewObject = (TextView)convertView.findViewById(R.id.categoryNameTxt);
        ((TextView)tempShoutViewObject).setText(link.getName());

//        AppManager.fontTextView(nameLabel, 23);

//        TriggerButtonUI ten = new TriggerButtonUI( (Button)convertView.findViewById(R.id.ten), activityName.addPurchase );
//        ten.setId(position);
//        TriggerButtonUI twenty = new TriggerButtonUI( (Button)convertView.findViewById(R.id.twenty), activityName.addPurchase);
//        twenty.setId(position);
        return convertView;
    }
}
