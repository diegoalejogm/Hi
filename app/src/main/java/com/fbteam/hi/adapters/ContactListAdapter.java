package com.fbteam.hi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fbteam.hi.R;
import com.fbteam.hi.models.App;
import com.fbteam.hi.models.Category;
import com.fbteam.hi.models.Contact;

/**
 * Created by leticiawanderley on 13/03/2016.
 */
public class ContactListAdapter extends ArrayAdapter<Contact> {
    private int resource;
    private LayoutInflater inflater;
    private Context context;

    public ContactListAdapter(Context ctx, int resourceId)
    {
        super(ctx, resourceId, App.getMe().getContacts());
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context  = ctx;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent )
    {
        convertView = (RelativeLayout) inflater.inflate( resource, null );
        Contact contact = App.getMe().getContacts().get(position);

        View tempShoutViewObject = (TextView)convertView.findViewById(R.id.contactNameTxt);
        ((TextView)tempShoutViewObject).setText(contact.getFullName());

        return convertView;
    }
}
