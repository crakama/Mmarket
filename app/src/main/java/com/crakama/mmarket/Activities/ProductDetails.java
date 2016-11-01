package com.crakama.mmarket.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.Display;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crakama.mmarket.FirebaseModels.PicassoClient;
import com.crakama.mmarket.R;

public class ProductDetails extends AppCompatActivity {

    TextView txtPname, txtPprice, txtPDesc,txtSeller,txtSellerNo;
    ImageView imgPimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       supportRequestWindowFeature(AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_product_details);

        // Get display size -- API L13 and up. Otherwise use getResources().getDisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay();
        Point screensize = new Point();
        display.getSize(screensize);

        // You want size to be 50% per EditText, so divide available height by 2.
        // Note: this is absolute height, does not take into consideration window decoration!
        int imageHeight = (screensize.y * 3) / 4;
        int sellerDetailsHeight = screensize.y / 4;
        int productdescHeight = screensize.y / 2;

        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00212121")) );
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99000000")) );
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        // Get a handle to your EditTexts
        //LinearLayout t1 = (LinearLayout) findViewById(R.id.linsum);

        // Set height to 50% of screen size each
        //t1.setMinimumHeight(imageHeight);



        // Gets linearlayout
        LinearLayout layout = (LinearLayout)findViewById(R.id.linsum);
          // Gets the layout params that will allow you to resize the layout
        ViewGroup.LayoutParams params = layout.getLayoutParams();
// Changes the height and width to the specified *pixels*
        params.height = imageHeight;
        //params.width = 100;
        layout.setLayoutParams(params);

        // Get a handle to your EditTexts
        //TextView productdetails = (TextView) findViewById(R.id.);
        //TextView sellerdetails = (TextView) findViewById(R.id.);


        // Set height to 50% of screen size each
        //productdetails.setHeight(productdescHeight);
        //sellerdetails.setHeight(sellerDetailsHeight);
        //t3.setHeight(editTextHeight);


        // Get a handle to your EditTexts
        EditText t1 = (EditText) findViewById(R.id.id4);
        EditText t2 = (EditText) findViewById(R.id.id2);
        EditText t3 = (EditText) findViewById(R.id.id3);

        // Set height to 50% of screen size each
        t1.setHeight(productdescHeight);
        t2.setHeight(productdescHeight);
        t3.setHeight(productdescHeight);

        txtPname = (TextView) findViewById(R.id.tvdProductName);
        txtPprice = (TextView) findViewById(R.id.tvdProductPrice);
        txtPDesc = (TextView) findViewById(R.id.txdProductDesc);
        txtSeller = (TextView) findViewById(R.id.tvdSellerName);
        //txtSellerNo = (TextView) findViewById(R.id.tvdSellerNo);
        imgPimage = (ImageView)findViewById(R.id.imgProductImage);



        /*
        *GET INTENT
        */
        Intent newsIntent = this.getIntent();

        /*
        * RECEIVE DATA
        */
        String name = newsIntent.getExtras().getString("NAME_KEY");
        String desc = newsIntent.getExtras().getString("DESC_KEY");
        String price = newsIntent.getExtras().getString("PRICE_KEY");
        String sellername = newsIntent.getExtras().getString("SELLER_KEY");
        String sellerno = newsIntent.getExtras().getString("MOBILE_KEY");
        String image = newsIntent.getExtras().getString("IMG_URL_KEY");

        /*
        * BIND DATA
        */

        txtPname.setText(name);
        txtPprice.setText(price);
        txtPDesc.setText(desc);
       // txtSellerNo.setText(sellerno);
        txtSeller.setText(sellername);
        PicassoClient.downloadProductImage(ProductDetails.this, image, imgPimage);
        //imgPimage.setImageResource(  );


    }
}
