package com.fbteam.hi;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.ImageView;

import com.fbteam.hi.models.App;
import com.fbteam.hi.models.Category;

import net.glxn.qrgen.android.QRCode;


public class ShowQRActivity extends AppCompatActivity
{
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qr);
        getSupportActionBar().hide();

        String catId =getIntent().getExtras().getString("categoryId");
        for(Category c : App.getMe().getCategories())
        {
            if(c.getId().equals(catId))
            {
                String codedString = App.getMe().toQRRepresentationWithCategory(c);
                Bitmap myBitmap = QRCode.from(codedString).withSize(width, width).bitmap();
                ImageView myImage = (ImageView) findViewById(R.id.imageView);
                myImage.setImageBitmap(myBitmap);

                break;
            }
        }


    }
}
