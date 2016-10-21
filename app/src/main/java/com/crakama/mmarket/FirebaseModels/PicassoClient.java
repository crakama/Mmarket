package com.crakama.mmarket.FirebaseModels;

import android.content.Context;
import android.widget.ImageView;

import com.crakama.mmarket.R;
import com.squareup.picasso.Picasso;

/**
 * Created by User on 10/20/2016.
 */

public class PicassoClient {

Context ctx;

    public PicassoClient(Context ctx) {
        this.ctx = ctx;
    }


    public static  void downloadProductImage(Context context, String imageurl, ImageView imageView){
        if(imageurl != null && imageurl.length() > 0){
            Picasso.with(context).load(imageurl).placeholder(R.mipmap.defaultplaceholder).into(imageView);
        }else{
            Picasso.with(context).load(R.mipmap.defaultplaceholder).into(imageView);
        }
    }
}
