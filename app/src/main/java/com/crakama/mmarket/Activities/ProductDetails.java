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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import com.crakama.mmarket.FirebaseModels.PicassoClient;
import com.crakama.mmarket.R;

public class ProductDetails extends AppCompatActivity {

    FloatingActionMenu fam;
    FloatingActionButton fab1, fab2, fab3;

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
        int productdescHeight = (screensize.y * 3)/ 4;
        int makeorder = screensize.y/8;

        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00212121")) );
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99000000")) );
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Gets linearlayout
        LinearLayout layout = (LinearLayout)findViewById(R.id.linsum);
          // Gets the layout params that will allow you to resize the layout
        ViewGroup.LayoutParams params = layout.getLayoutParams();

        // Changes the height and width to the specified *pixels*
        params.height = imageHeight;
        //params.width = 100;
        layout.setLayoutParams(params);



        // Get a handle to your EditTexts
     TextView t1 = (TextView) findViewById(R.id.txdProductDesc);
     TextView sellerDetails = (TextView) findViewById(R.id.tvdSellerName);
     TextView t3 = (TextView) findViewById(R.id.tvdOrder);

        // Set height to 50% of screen size each
        t1.setHeight(productdescHeight);
        sellerDetails.setHeight(sellerDetailsHeight);
        t3.setHeight(makeorder);

        txtPname = (TextView) findViewById(R.id.tvdProductName);
        txtPprice = (TextView) findViewById(R.id.tvdProductPrice);
       // txtPDesc = (TextView) findViewById(R.id.txdProductDesc);
        //txtSeller = (TextView) findViewById(R.id.tvdSellerName);
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
        //txtPDesc.setText(desc);
        t1.setText(desc);
        // txtSellerNo.setText(sellerno);
        //txtSeller.setText(sellername);
        sellerDetails.setText(sellername);
        PicassoClient.downloadProductImage(ProductDetails.this, image, imgPimage);
        //imgPimage.setImageResource(  );

        fam = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        fab1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        fab2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        fab3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);

        fab1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked

            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked

            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked

            }
        });
    }

    public void makeProductOrder(View view) {
        Intent makeorderIntent = new Intent(ProductDetails.this, MakeOrder.class);
        startActivity(makeorderIntent);
    }
}
