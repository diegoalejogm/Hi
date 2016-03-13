package com.fbteam.hi.adapters;


import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fbteam.hi.models.App;
import com.fbteam.hi.R;
import com.fbteam.hi.models.Category;
import com.fbteam.hi.models.Link;


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

        View tempShoutViewObject = (TextView)convertView.findViewById(R.id.categoryNameTxt);
        ((TextView)tempShoutViewObject).setText(category.getName());

        TextView tempShoutViewObject2 = (TextView)convertView.findViewById(R.id.categoryDescriptionTxt);
        StringBuffer sb = new StringBuffer();
        for(Link l : category.getLinks())
        {
            sb.append(l.getName()+", ");
        }
        if(sb.length() >= 2) {
            sb.setLength(sb.length()-2);
        }
        else
        {
            sb.append("Empty");
            final TypedValue value = new TypedValue();
            tempShoutViewObject2.setTextColor( context.getResources().getColor(R.color.accent));
        }

        ((TextView)tempShoutViewObject2).setText(sb.toString());

        return convertView;
    }
}
