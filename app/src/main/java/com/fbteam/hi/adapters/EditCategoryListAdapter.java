package com.fbteam.hi.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fbteam.hi.Configuration;
import com.fbteam.hi.R;
import com.fbteam.hi.models.App;
import com.fbteam.hi.models.Category;
import com.fbteam.hi.models.Link;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nik on 12/03/16.
 */

public class EditCategoryListAdapter extends ArrayAdapter<Link> {


    private int resource;
    private LayoutInflater inflater;
    private Context context;
    private Category editingCategory;

    public EditCategoryListAdapter(Context ctx, int resourceId, Category category)
    {
        super(ctx, resourceId, App.getMe().getLinks());
        resource = resourceId;
        editingCategory = category;
        inflater = LayoutInflater.from( ctx );
        context  = ctx;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent )
    {
        convertView = (RelativeLayout) inflater.inflate( resource, null );
        Link link = App.getMe().getLinks().get(position);

        System.out.println(position + " " + link.getName());
        if(position % 2 == 1)
        {
            RelativeLayout bg = (RelativeLayout)convertView.findViewById(R.id.layoutBg);
        	bg.setBackgroundColor(Color.parseColor("#F3F3F3"));
        }

        View tempLinkViewObject = (TextView)convertView.findViewById(R.id.linkNameTxt);
        ((TextView)tempLinkViewObject).setText(link.getName());

        if(editingCategory.containsLink(link)){
            tempLinkViewObject = (CheckBox)convertView.findViewById(R.id.checkBox);
            ((CheckBox)tempLinkViewObject).setChecked(true);
        }

        // image setting

        List keys = new ArrayList(Configuration.links.keySet());
        int id = Configuration.linksPics.get(link.getName());

        tempLinkViewObject = (ImageView)convertView.findViewById(R.id.linkImage);
        ((ImageView)tempLinkViewObject).setImageResource(id);

        return convertView;
    }
}

