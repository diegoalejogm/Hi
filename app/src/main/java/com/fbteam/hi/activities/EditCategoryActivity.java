package com.fbteam.hi.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.fbteam.hi.Configuration;
import com.fbteam.hi.R;
import com.fbteam.hi.adapters.CategoryListAdapter;
import com.fbteam.hi.adapters.EditCategoryListAdapter;
import com.fbteam.hi.helper.CaptureQRActivityAnyOrientation;
import com.fbteam.hi.models.App;
import com.fbteam.hi.models.Category;
import com.fbteam.hi.models.Link;
import com.google.zxing.integration.android.IntentIntegrator;

/**
 * Created by nik on 12/03/16.
 */

public class EditCategoryActivity extends ActivityNavMenu implements View.OnClickListener {

    // list view
    private ListView links;
    private Category editingCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Category");
        loadNavDrawer(toolbar);
        // load editing category id
        int categId = getIntent().getExtras().getInt(Configuration.CATEGORY_ID);
//        int categId = Integer.parseInt(categIdStr);
        editingCategory = App.getMe().getCategories().get(categId);
        findElements();
    }


    private void findElements(){

        // set up title

        TextView txt = (TextView)findViewById(R.id.category_title);
        txt.setText(editingCategory.getName());

        /// set up list

        links = (ListView) findViewById(R.id.categoriesList);
        links.setAdapter(new EditCategoryListAdapter(this, R.layout.edit_category_row, editingCategory));
        links.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Link tempL = editingCategory.getLinks().get(i);
                CheckBox cb = (CheckBox)view.findViewById(R.id.checkBox);
                System.out.println("pressing");
                if(editingCategory.isLinkInCategory(tempL)) {
                    editingCategory.removeLink(tempL);
                    cb.setChecked(false);
                }else{
                    editingCategory.addLink(tempL);
                    cb.setChecked(true);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.FAB_AddFriend:

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
            //save user
            System.out.println("saved user and exit");
            App.getMe().persist(getSharedPreferences(Configuration.DB_PREFERENCES, Context.MODE_PRIVATE));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

