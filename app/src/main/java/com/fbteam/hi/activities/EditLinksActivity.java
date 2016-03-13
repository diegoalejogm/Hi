package com.fbteam.hi.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.TextView;
import com.fbteam.hi.Configuration;
import com.fbteam.hi.R;
import com.fbteam.hi.adapters.CategoryListAdapter;
import com.fbteam.hi.adapters.DialogSelectListAdapter;
import com.fbteam.hi.adapters.EditLinksAdapter;
import com.fbteam.hi.models.App;
import com.fbteam.hi.models.Category;
import com.fbteam.hi.models.Link;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by nik on 12/03/16.
 */

public class EditLinksActivity extends ActivityNavMenu implements View.OnClickListener {

    // list view
    private ListView links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_links);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Profile");
        loadNavDrawer(toolbar);
        findElements();
    }


    private void findElements(){

        /// set up list
        links = (ListView) findViewById(R.id.categoriesList);
        links.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        links.setAdapter(new EditLinksAdapter(this, R.layout.edit_link_row));

        View tempV = this.findViewById(R.id.user_name);
        ((EditText)tempV).setText(App.getMe().getFirstName() + " " + App.getMe().getLastName());
        // Add friend button (FAB)
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_new_link);
        fab.setOnClickListener(this);

        fab = (FloatingActionButton) findViewById(R.id.scan_qr);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.scan_qr:

                break;
            case R.id.add_new_link:
                createDialogWithSelection();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.editing, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menu_done) {

            updateChanges();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateChanges()
    {
        View tempV = this.findViewById(R.id.user_name);
        try{
            String newName = ((EditText)tempV).getText().toString();
            System.out.println("going " + newName);
            String[] names = newName.split(" ");
            App.getMe().setFirstName(names[0]);
            App.getMe().setLastName(names[1]);
            System.out.println("going " + names.length);
            System.out.println("going " + names[0]);
        } catch(NullPointerException e){
            System.out.println(e.getCause());
        }

        for(int i = 0; i < links.getChildCount() ; i++)
        {
            EditText newContent= (EditText)links.getChildAt(i).findViewById(R.id.editContent);
            App.getMe().getLinks().get(i).setContent(newContent.getText().toString());
        }
        App.getMe().persist(getSharedPreferences(Configuration.DB_PREFERENCES, Context.MODE_PRIVATE));
    }

    public void createDialogWithSelection(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
//        builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Select One Name:-");

        final DialogSelectListAdapter arrayAdapter = new DialogSelectListAdapter(this,  R.layout.link_dialog_singlechoice);

        for (String key : Configuration.links.keySet()) {
//            System.out.println(pair.getKey() + " = " + pair.getValue());
            arrayAdapter.add(key + "");
        }

        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        Link newL = new Link(strName, "", Configuration.TYPE_LINK_WEBSITE, false);
                        App.getMe().addLinkNoCategory(newL);
                        links.invalidateViews();
                    }
                });
        builderSingle.show();
    }
}


