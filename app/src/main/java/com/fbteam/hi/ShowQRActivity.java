package com.fbteam.hi;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.ImageView;
import net.glxn.qrgen.android.QRCode;


public class ShowQRActivity extends AppCompatActivity
{

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

        String codedString = "sdflafjhdkasfhaslñfadshfdasfjkladsbfadlskbvadsklccnilewaucnasdlcjasnuie";

        Bitmap myBitmap = QRCode.from(codedString).withSize(width, width).bitmap();
        ImageView myImage = (ImageView) findViewById(R.id.imageView);
        myImage.getDrawable().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
        myImage.setImageBitmap(myBitmap);
    }
}
