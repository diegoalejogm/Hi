package com.fbteam.hi.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.fbteam.hi.Configuration;
import android.widget.Toast;
import com.fbteam.hi.R;
import com.fbteam.hi.ShowQRActivity;
import com.fbteam.hi.adapters.CategoryListAdapter;
import com.fbteam.hi.helper.CaptureQRActivityAnyOrientation;
import com.fbteam.hi.models.App;
import com.fbteam.hi.models.Category;
import com.fbteam.hi.models.Contact;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class HomeActivity extends ActivityNavMenu implements View.OnClickListener  {

    // list view
    private ListView categoriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         loadNavDrawer(toolbar);
        findElements();
    }

    public static Bitmap getFacebookProfilePicture(String userID) throws IOException {
        URL imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
        Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());

        return bitmap;
    }

    // on createView method

    @Override
    public void onResume() {
        super.onResume();
        categoriesList.invalidateViews();
    }


    private void findElements() {
        // Add friend button (FAB)
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_new_category);
        fab.setOnClickListener(this);

        fab = (FloatingActionButton) findViewById(R.id.scan_qr);
        fab.setOnClickListener(this);

        /// set up list
        categoriesList = (ListView) findViewById(R.id.categoriesList);
        categoriesList.setAdapter(new CategoryListAdapter(this, R.layout.category_row));
        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override


            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("on item click" + i + " " + l + " " + view.getId());
                Category category = (Category) adapterView.getItemAtPosition(i);
                // create
                showQR(category);
            }
        });
        categoriesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                clickOnAndEditCategory(pos);
                return true;
            }
        });

        if (AccessToken.getCurrentAccessToken() != null) {
            /*try {
                Bitmap mBitmap = getFacebookProfilePicture(AccessToken.getCurrentAccessToken().getToken());
                ImageView imageView = (ImageView) this.findViewById(R.id.imageView);
                imageView.setImageBitmap(mBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }

    private void processLongClickOnCategory(int id){
        System.out.println("category pro-clicked " + id);
        Intent intent = new Intent(this, EditCategoryActivity.class);
        intent.putExtra(Configuration.CATEGORY_ID, id);
        startActivityForResult(intent, 9090);
    }

    private void showQR(Category category){

        Intent intent = new Intent(this, ShowQRActivity.class);
        intent.putExtra("categoryId", category.getId());
        startActivityForResult(intent, 9090);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.add_new_category:
                Category newCategory = new Category("New");
                App.getMe().getCategories().add(newCategory);
                int categoryID = App.getMe().getCategories().size() - 1;
                clickOnAndEditCategory(categoryID);
                break;
            case R.id.scan_qr:
                new IntentIntegrator(this)
                        .setCaptureActivity(CaptureQRActivityAnyOrientation.class)
                        .setBeepEnabled(true)
                        .setOrientationLocked(false)
                        .initiateScan();
                break;
        }
    }

    private void createNewCategory(){
        Category newCategory = new Category("New");
        App.getMe().getCategories().add(newCategory);
        int categoryID = App.getMe().getCategories().size() - 1;
        clickOnAndEditCategory(categoryID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (requestCode == 1111 && resultCode == Activity.RESULT_OK)
        {
            Uri contactData = data.getData();
            Cursor c =  managedQuery(contactData, null, null, null, null);

            if (c.moveToFirst())
            {
                String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                Log.d(this.getClass().getName(), "Selected " + id);
                Contact contact = App.getMe().getContacts().get(App.getMe().getContacts().size()-1);
                contact.setId(id);
                AddressBookManager.openContact(this.getApplicationContext(), contact);
            }
        }

        else if(resultCode == Activity.RESULT_CANCELED) return;
        // QR Code Scan Cancelled
        else if(result.getContents() == null) {
            Log.d("MainActivity", "Cancelled scan");
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
        }
        // QR Code Scan Succeed
        else
        {
            Contact c = App.getMe().addContactFromQRString(result.getContents());
            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
            //Log.v("TTTT", App.getMe().getContacts().get(0).toStringEncoding());

            AddressBookManager.createContact(c, this, 1111);
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
