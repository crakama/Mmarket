package com.crakama.mmarket.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crakama.mmarket.FirebaseModels.ProductModel;
import com.crakama.mmarket.ProductData;
import com.crakama.mmarket.R;
import com.crakama.mmarket.RV_DataAdapter;
import com.crakama.mmarket.RV_ItemClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {


    // Set grid view items titles and images
    DatabaseReference dbref;
    FirebaseRecyclerAdapter<ProductModel,MainActivity.ProductModelVH> firebaseproductRecycleAdapter ;
    RecyclerView rv_productDisplay;
    LinearLayoutManager nwlinearLayoutManager;
    ProgressBar newsprogressBar;




    public static class ProductModelVH extends RecyclerView.ViewHolder{

        public final TextView productName,productPrice;
        View mView;

        public ProductModelVH(View itemView) {
            super(itemView);
            this.mView = itemView;
            this.productName = (TextView) mView.findViewById(R.id.producttext);
            this.productPrice = (TextView) mView.findViewById(R.id.productprice);
            //this.productDesc = (TextView) mView.findViewById(R.id.lv_repatriation_stages);
            // this.newsDate = (TextView) mView.findViewById(R.id.lv_item_date);


        }

    }// End RepatriationRootFragModel class


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;

    public static final String PRODUCTS= "NewsModel";
    private final String recyclerViewTitleText[] = {"Fresh fruits", "Cerials",
            "Leather Bags", "Art and Jewelery", "Heavy Duty Duvet", "Fruits per Kgs", "Books for Kids",
            "Friuts in Large Scale", "Metal Equipments", "Fresh Fish Tilapia", "Peanut Butter", "Sandals"
    };

    private final int recyclerViewImages[] = {
            R.drawable.fruits, R.drawable.cerials, R.drawable.bagsretail, R.drawable.art,
            R.drawable.duvets, R.drawable.fruitskilos, R.drawable.books,
            R.drawable.fruitsretail, R.drawable.metals, R.drawable.fish,
            R.drawable.peanutbutter, R.drawable.sandals };


    private ArrayList<ProductData> prepareData() {

        ArrayList<ProductData> av = new ArrayList<>();
        //Loop through all product titles defined in string array e.g fruits,arts metals
        for (int i = 0; i < recyclerViewTitleText.length; i++) {
            /** create an instance of the class */
            ProductData productdata = new ProductData();
            /** Telling ProductData class instance where to get product Image and titles  */
            productdata.setrecyclerViewTitleText(recyclerViewTitleText[i]);
            productdata.setrecyclerViewImage(recyclerViewImages[i]);
            av.add(productdata);
        }
        return av;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_productDisplay =(RecyclerView) findViewById(R.id.rv_product_display);


        nwlinearLayoutManager = new LinearLayoutManager(this);
        nwlinearLayoutManager.setStackFromEnd(true);

        dbref = FirebaseDatabase.getInstance().getReference();
        //newsprogressBar = (ProgressBar) findViewById(R.id.newsprogress_bar);
        //newsprogressBar.setVisibility(View.VISIBLE);

        firebaseproductRecycleAdapter = new FirebaseRecyclerAdapter<ProductModel, ProductModelVH>(
                ProductModel.class,
                R.layout.products_display,
                ProductModelVH.class,
                dbref.child(PRODUCTS)) {

            @Override
            protected void populateViewHolder(ProductModelVH viewHolder, final ProductModel model, final int position) {
                viewHolder.productName.setText(model.getProductText());
                //viewHolder.productPrice.setText(model.getProductImg());
                viewHolder.productPrice.setText(model.getProductPrice());
                //viewHolder.newsDate.setText(DateUtils.getRelativeTimeSpanString((long) model.getTimestamp()));
                newsprogressBar.setVisibility(View.GONE);
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.w(TAG, "You clicked on " + position);
                        //firebasenewsRecycleAdapter.getRef(position).removeValue();
                        //openNewsDetailActivity(model.getNewsHead(), model.getNewsBody(), model.getNewsorganization());
                    }
                });
            }};


        initRecyclerViews();
        initInstances();

    }


    private void initInstances() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.welcome, R.string.welcome);
        drawerLayout.addDrawerListener(drawerToggle);
        //Initializing NavigationView
        navigation = (NavigationView) findViewById(R.id.navigation_view);
        //setting Navigation View Item Selected Listener to handle the item click of the navigatio menu
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nv_item_login :
                        //Do some thing here
                        break;
                    case R.id.nv_item_sellItem:
                        Intent sellItemIntent = new Intent( MainActivity.this,UploadImage.class);
                        startActivity(sellItemIntent);
                        break;
                    case R.id.nv_item_aboutApp:

                        break;
                }
                return false;
            }
        });
    }

    private void initRecyclerViews() {

        /** Inflate link the recycler view layout file with code
         * Set properties such as size and display format */
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_product_display);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext() , 2);
//        LinearLayoutManager mLayoutManager
//                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        /**Populate adapter with product data   */
        ArrayList<ProductData> av = prepareData();
        RV_DataAdapter mAdapter = new RV_DataAdapter(getApplicationContext(), av);
        mRecyclerView.setAdapter(mAdapter);

    mRecyclerView.addOnItemTouchListener(
            new RV_ItemClickListener(getApplicationContext(), new RV_ItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int i) {
            switch (i) {
                case 0:
                    Toast.makeText(view.getContext(), "position= " + i, Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(view.getContext(), "position= " + i, Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(view.getContext(), "position= " + i, Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(view.getContext(), "position= " + i, Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    Toast.makeText(view.getContext(), "position= " + i, Toast.LENGTH_LONG).show();
                    break;
                case 5:
                    Toast.makeText(view.getContext(), "position= " + i, Toast.LENGTH_LONG).show();
                    break;

            }
        }
    })
            );//Ends addOnItemTouchListener

    }//Ends initRecycleViews method



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }



}
