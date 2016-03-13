package com.fbteam.hi.activities;

import android.app.Activity;
import android.content.*;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;
import com.fbteam.hi.Configuration;
import com.fbteam.hi.models.Contact;
import com.fbteam.hi.models.Link;

import java.util.ArrayList;

import static android.provider.ContactsContract.*;

/**
 * Created by Diego on 3/12/16.
 */
public class AddressBookManager
{
    public static void createContactSneaky(Contact c, ContentResolver contentResolver, Context context)
    {
        String firstName = c.getFirstName();
        String lastName = c.getLastName();
        String MobileNumber = "123456";
        String HomeNumber = "1111";
        String WorkNumber = "2222";
        String emailID = "email@nomail.com";

        ArrayList<ContentProviderOperation> ops = new ArrayList < ContentProviderOperation > ();

        ops.add(ContentProviderOperation.newInsert(
                RawContacts.CONTENT_URI)
                .withValue(RawContacts.ACCOUNT_TYPE, null)
                .withValue(RawContacts.ACCOUNT_NAME, null)
                .build());

        //------------------------------------------------------ Names
        if (firstName != null) {
            ops.add(ContentProviderOperation.newInsert(
                    Data.CONTENT_URI)
                    .withValueBackReference(Data.RAW_CONTACT_ID, 0)
                    .withValue(Data.MIMETYPE,
                            CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            CommonDataKinds.StructuredName.GIVEN_NAME,
                            firstName).build());
        }

        if (lastName != null) {
            ops.add(ContentProviderOperation.newInsert(
                    Data.CONTENT_URI)
                    .withValueBackReference(Data.RAW_CONTACT_ID, 0)
                    .withValue(Data.MIMETYPE,
                            CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            CommonDataKinds.StructuredName.FAMILY_NAME,
                            lastName).build());
        }

        //------------------------------------------------------ Organization

        // Asking the Contact provider to create a new contact                 
        try {
            contentResolver.applyBatch(AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static String findAndroidUserName(Activity a)
    {
        Cursor c = a.getApplication().getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);
        c.moveToFirst();
        try
        {
            String msj = c.getString(c.getColumnIndex("display_name"));
            return msj;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
        finally
        {
            c.close();
        }



    }

    public static void createContact(Contact c, Activity activity)
    {
        ArrayList < ContentValues > data = new ArrayList<>();

        Intent intent = new Intent(Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.NAME, c.getFullName());

        for(Link l : c.getLinks())
        {
            ContentValues cv = new ContentValues();

            if(l.getType() == Configuration.TYPE_LINK_PHONE)
            {
                cv.put(Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                cv.put(CommonDataKinds.Phone.NUMBER, l.getContent());
            }
            else if(l.getType() == Configuration.TYPE_LINK_WEBSITE)
            {
                cv.put(Data.MIMETYPE, CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                String linkWebsite = l.getContent();
                if (Configuration.links.containsKey(l.getName())) {
                    StringBuilder stringBuilder = new StringBuilder(linkWebsite);
                    stringBuilder.insert(0, Configuration.links.get(l.getName()));
                    linkWebsite = stringBuilder.toString();
                }
                cv.put(CommonDataKinds.Website.URL, linkWebsite);
            }
            else if(l.getType() == Configuration.TYPE_LINK_EMAIL)
            {
                cv.put(Data.MIMETYPE, CommonDataKinds.Email.CONTENT_ITEM_TYPE);
                cv.put(CommonDataKinds.Email.ADDRESS, l.getContent());
            }
            data.add(cv);
        }

        intent.putParcelableArrayListExtra(Intents.Insert.DATA, data);

        activity.startActivity(intent);
    }

}
