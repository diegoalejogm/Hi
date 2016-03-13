package com.fbteam.hi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fbteam.hi.Configuration;
import com.fbteam.hi.R;
import com.fbteam.hi.models.App;
import com.fbteam.hi.models.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leticiawanderley on 13/03/2016.
 */
public class DialogSelectListAdapter extends ArrayAdapter<String> {
    private int resource;
    private LayoutInflater inflater;
    private Context context;

    public DialogSelectListAdapter(Context ctx, int resourceId)
    {
        super(ctx, resourceId, new ArrayList<String>());
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context  = ctx;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent )
    {
        convertView = (RelativeLayout) inflater.inflate( resource, null );
        List keys = new ArrayList(Configuration.links.keySet());
        String key = keys.get(position).toString();
        //android.R.mipmap.
        int id = context.getResources().getIdentifier(key+".png", "drawable", context.getPackageName());
//        imageView.setImageResource(id);
        //Configuration.links
        View tempShoutViewObject = (TextView)convertView.findViewById(R.id.linkTypeNameTxt);
        ((TextView)tempShoutViewObject).setText(keys.get(position).toString());

        return convertView;
    }
}
