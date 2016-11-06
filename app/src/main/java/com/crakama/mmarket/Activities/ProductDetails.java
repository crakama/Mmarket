package com.crakama.mmarket.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;
//import com.github.clans.fab.FloatingActionButton;
//import com.github.clans.fab.FloatingActionMenu;

import com.crakama.mmarket.FirebaseModels.PicassoClient;
import com.crakama.mmarket.R;

import java.util.ArrayList;
import java.util.List;

public class ProductDetails extends AppCompatActivity {

    //FloatingActionMenu fam;
    //FloatingActionButton fab1, fab2, fab3;
    FloatingActionButton fab;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;
    CoordinatorLayout rootLayout;


    //Save the FAB's active status
    //false -> fab = close
    //true -> fab = open
    private boolean FAB_Status = false;

    //Animations
    Animation show_fab_1;
    Animation hide_fab_1;
    Animation show_fab_2;
    Animation hide_fab_2;
    Animation show_fab_3;
    Animation hide_fab_3;


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
        int imageHeight = (screensize.y * 3) / 5;
        int sellerDetailsHeight = screensize.y / 4;
        int productdescHeight = (screensize.y * 1)/ 2;
        int makeorder = screensize.y/8;

        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00212121")) );
      //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99000000")) );
//        // add back arrow to toolbar
//        if (getSupportActionBar() != null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

        // Gets linearlayout
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.linsum);
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


        rootLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        //Floating Action Buttons
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab_1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab_2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab_3);

        //Animations
        show_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);
        hide_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);
        show_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);
        hide_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FAB_Status == false) {
                    //Display FAB menu
                    expandFAB();
                    FAB_Status = true;
                } else {
                    //Close FAB menu
                    hideFAB();
                    FAB_Status = false;
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "Floating Action Button 1", Toast.LENGTH_SHORT).show();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "Floating Action Button 2", Toast.LENGTH_SHORT).show();
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "Floating Action Button 3", Toast.LENGTH_SHORT).show();
            }
        });


        //Initialize an empty list of 50 elements
        List list = new ArrayList();
        for (int i = 0; i < 50; i++) {
            list.add(new Object());
        }

//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//        Recycler_View_Adapter adapter = new Recycler_View_Adapter(list, getApplication());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final LinearLayout linearLayout =(LinearLayout) findViewById(R.id.linearparent);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (FAB_Status) {
                    hideFAB();
                    FAB_Status = false;
                }
                return false;
            }
        });




        //imgPimage.setImageResource(  );

//        fam = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
//        fab1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
//        fab2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
//        fab3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);
//
//        fab1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                //TODO something when floating action menu first item clicked
//
//            }
//        });
//        fab2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                //TODO something when floating action menu second item clicked
//
//            }
//        });
//        fab3.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                //TODO something when floating action menu third item clicked
//
//            }
//        });
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99000000")) );
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



    }




    private void expandFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin += (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin += (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(show_fab_2);
        fab2.setClickable(true);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin += (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin += (int) (fab3.getHeight() * 1.7);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(show_fab_3);
        fab3.setClickable(true);
    }


    private void hideFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(hide_fab_2);
        fab2.setClickable(false);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin -= (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin -= (int) (fab3.getHeight() * 1.7);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(hide_fab_3);
        fab3.setClickable(false);
    }

    public void makeProductOrder(View view) {
        Intent makeorderIntent = new Intent(ProductDetails.this, MakeOrder.class);
        startActivity(makeorderIntent);
    }
}
