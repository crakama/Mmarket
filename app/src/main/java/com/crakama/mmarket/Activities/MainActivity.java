package com.crakama.mmarket.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crakama.mmarket.FirebaseModels.PicassoClient;
import com.crakama.mmarket.FirebaseModels.ProductModel;
import com.crakama.mmarket.R;
import com.facebook.AccessToken;
import com.firebase.client.FirebaseError;
import com.firebase.client.utilities.Base64;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;
import com.facebook.FacebookSdk;
import com.google.firebase.database.ValueEventListener;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    // Set grid view items titles and images
    DatabaseReference dbref;
    FirebaseRecyclerAdapter<ProductModel,MainActivity.ProductModelVH> firebaseproductRecycleAdapter ;
    RecyclerView rv_productDisplay;
    LinearLayoutManager nwlinearLayoutManager;
    ProgressBar productprogressBar;


    private static final String TAG = "AndroidBash";
   // private Firebase myFirebaseRef;
    private FirebaseAuth mAuth;
    private TextView name;
    private TextView welcomeText;
    private Button changeButton;
    private Button revertButton;
    // To hold Facebook profile picture
    private ImageView profilePicture;

    public static class ProductModelVH extends RecyclerView.ViewHolder{

        public final TextView productName,productPrice,productDetails,productSeller,productSellerNo;
        public final ImageView productImg;
        View mView;

        public ProductModelVH(View itemView) {
            super(itemView);
            this.mView = itemView;
            this.productName = (TextView) mView.findViewById(R.id.producttext);
            this.productPrice = (TextView) mView.findViewById(R.id.productprice);
            this.productImg = (ImageView) mView.findViewById(R.id.productimage);
            this.productDetails = (TextView) mView.findViewById(R.id.productdetails);
            this.productSeller = (TextView) mView.findViewById(R.id.editTextProductseller);
            this.productSellerNo = (TextView) mView.findViewById(R.id.productdetails);
            //this.productDesc = (TextView) mView.findViewById(R.id.lv_repatriation_stages);
            // this.newsDate = (TextView) mView.findViewById(R.id.lv_item_date);


        }

    }// End RepatriationRootFragModel class


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;
    Context context;

    public static final String PRODUCTS= "ProductModel";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_productDisplay =(RecyclerView) findViewById(R.id.rv_product_display);

        nwlinearLayoutManager = new LinearLayoutManager(this);
        nwlinearLayoutManager.setStackFromEnd(true);

        dbref = FirebaseDatabase.getInstance().getReference();
        productprogressBar = (ProgressBar) findViewById(R.id.productprogress_bar);
        productprogressBar.setVisibility(View.VISIBLE);


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //Creates a reference for  your Firebase database
        //Add YOUR Firebase Reference URL instead of the following URL
        //myFirebaseRef = new Firebase("https://androidbashfirebaseupdat-bd094.firebaseio.com/users/");
        mAuth = FirebaseAuth.getInstance();
        initRecyclerViews();
        initInstances();

    }
    @Override
    protected void onStart() {
        super.onStart();
        //Get the uid for the currently logged in User from intent data passed to this activity


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
                        Intent loginIntent = new Intent( MainActivity.this,LoginActivity.class);
                        startActivity(loginIntent);
                        break;
                    case R.id.nv_item_register :
                        //Do some thing here
                        Intent regIntent = new Intent( MainActivity.this,SignUpActivity.class);
                        startActivity(regIntent);
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
       // mRecyclerView.setHasFixedSize(true);
        nwlinearLayoutManager = new LinearLayoutManager(MainActivity.this);
        nwlinearLayoutManager.setStackFromEnd(false);
       // RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext() , 2);
//        LinearLayoutManager mLayoutManager
//                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(nwlinearLayoutManager);

        /**Populate adapter with product data   */
        firebaseproductRecycleAdapter = new FirebaseRecyclerAdapter<ProductModel, ProductModelVH>(
                ProductModel.class,
                R.layout.products_display,
                ProductModelVH.class,
                dbref.child(PRODUCTS)) {

            @Override
            protected void populateViewHolder(ProductModelVH viewHolder, final ProductModel model, final int position) {

                viewHolder.productName.setText(model.getProductText());
                PicassoClient.downloadProductImage(MainActivity.this,model.getProductUrl(),viewHolder.productImg);
                viewHolder.productPrice.setText(model.getProductPrice());
                //viewHolder.productDetails.setText(model.getProductDetails());

                productprogressBar.setVisibility(View.GONE);
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.w(TAG, "You clicked on " + position);
                        //firebasenewsRecycleAdapter.getRef(position).removeValue();
                        openNewsDetailActivity(model.getProductText(), model.getProductPrice(),
                                model.getProductDetails(), model.getProductSellerName(),model.getProductSellerNo(),model.getProductUrl());


                    }
                });
            }

            private void openNewsDetailActivity(String...details) {
                Intent newsIntent = new Intent(MainActivity.this, ProductDetails.class);
                newsIntent.putExtra("NAME_KEY", details[0]);
                newsIntent.putExtra("DESC_KEY", details[2]);
                newsIntent.putExtra("PRICE_KEY", details[1]);
                newsIntent.putExtra("SELLER_KEY", details[3]);
                newsIntent.putExtra("MOBILE_KEY", details[4]);
                newsIntent.putExtra("IMG_URL_KEY", details[5]);

                startActivity(newsIntent);
            }

        };

        mRecyclerView.setLayoutManager(nwlinearLayoutManager);
        mRecyclerView.setAdapter(firebaseproductRecycleAdapter);



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
    public void onOrderAction(MenuItem mi) {
        // handle click here
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
        /**   int id = item.getItemId();

         //noinspection SimplifiableIfStatement
         if (id == R.id.action_settings) {
         return true;
         }
         return super.onOptionsItemSelected(item);*/

        // Handle presses on the action bar items
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_account) {
//            Intent shareIntent = new Intent(MainActivity.this, UpdateNews.class);
//            startActivity(addcampIntent);
            return true;
        } else if (id == R.id.home) {
            onBackPressed();
            return true;

        }else if (id == R.id.action_logout) {
            mAuth.signOut();
            finish();
        }

        else if (id == R.id.action_sale) {
            Intent sellItemIntent = new Intent(MainActivity.this, UploadImage.class);
            startActivity(sellItemIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                Base64.InputStream input = (Base64.InputStream) connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }



}
