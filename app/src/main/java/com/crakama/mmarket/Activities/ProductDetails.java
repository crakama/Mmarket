package com.crakama.mmarket.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.crakama.mmarket.FirebaseModels.PicassoClient;
import com.crakama.mmarket.R;

public class ProductDetails extends Activity {

    TextView txtPname, txtPprice, txtPDesc,txtSeller,txtSellerNo;
    ImageView imgPimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // supportRequestWindowFeature(AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY);

        setContentView(R.layout.activity_product_details);


        txtPname = (TextView) findViewById(R.id.tvdProductName);
        txtPprice = (TextView) findViewById(R.id.tvdProductPrice);
        txtPDesc = (TextView) findViewById(R.id.txdProductDesc);
        txtSeller = (TextView) findViewById(R.id.tvdSellerName);
        txtSellerNo = (TextView) findViewById(R.id.tvdSellerNo);
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
        txtSellerNo.setText(sellerno);
        txtSeller.setText(sellername);
        PicassoClient.downloadProductImage(ProductDetails.this, image, imgPimage);
        //imgPimage.setImageResource(  );


    }
}
