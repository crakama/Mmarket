package com.crakama.mmarket.FirebaseModels;

import com.squareup.picasso.RequestCreator;

/**
 * Created by User on 10/18/2016.
 */

public class ProductModel {

    private String productText;
    private String productPrice;

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    private String productUrl;


    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }



    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }



}
