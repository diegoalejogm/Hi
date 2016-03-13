package com.fbteam.hi.adapters;


import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fbteam.hi.Configuration;
import com.fbteam.hi.models.App;
import com.fbteam.hi.R;
import com.fbteam.hi.models.Link;

import java.util.ArrayList;
import java.util.List;


public class EditLinksAdapter extends ArrayAdapter<Link>{

    private int resource;
    private LayoutInflater inflater;
    private Context context;

    public EditLinksAdapter(Context ctx, int resourceId)
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

        if(position % 2 == 1)
        {
            RelativeLayout bg = (RelativeLayout)convertView.findViewById(R.id.layoutBg);
        	bg.setBackgroundColor(Color.parseColor("#F3F3F3"));
        }

        View tempLinkViewObject = (TextView)convertView.findViewById(R.id.linkNameTxt);
        ((TextView)tempLinkViewObject).setText(link.getName());

        tempLinkViewObject = (EditText) convertView.findViewById(R.id.editContent);
        for (Link tempL: App.getMe().getLinks()){
            if(link.getName().equals(tempL.getName()))
                ((EditText) tempLinkViewObject).setText(link.getContent());
        }

        // image setting
        List keys = new ArrayList(Configuration.links.keySet());
//        String key = keys.get(position).toString();
        int id = Configuration.linksPics.get(link.getName());
//        System.out.println("SHIT  " + key+".png ... " + id);

        tempLinkViewObject = (ImageView)convertView.findViewById(R.id.linkImage);
        ((ImageView)tempLinkViewObject).setImageResource(id);
        return convertView;
    }
}
