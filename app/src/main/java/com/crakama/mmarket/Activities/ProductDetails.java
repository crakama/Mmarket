package com.crakama.mmarket.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.crakama.mmarket.R;

public class ProductDetails extends AppCompatActivity {

    TextView txtPname, txtPprice, txtPDesc,txtSeller,txtSellerNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        txtPname = (TextView) findViewById(R.id.tvdProductName);
        txtPprice = (TextView) findViewById(R.id.tvdProductPrice);
        txtPDesc = (TextView) findViewById(R.id.txdProductDesc);
        txtSeller = (TextView) findViewById(R.id.tvdSellerName);
        txtSellerNo = (TextView) findViewById(R.id.tvdSellerNo);
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


        /*
        * BIND DATA
        */
        txtPname.setText(name);
        txtPprice.setText(desc);
        txtPDesc.setText(sellername);
        txtSellerNo.setText(sellerno);
        txtSeller.setText(price);


    }
}
