package com.crakama.mmarket;

/**
 * Created by cate.rakama@gmail.com on 8/29/2016.
 */
public class ProductData {


    private String recyclerViewTitleText;
    private int recyclerViewImage;

    public int getRecyclerViewPrice() {
        return recyclerViewPrice;
    }

    public void setRecyclerViewPrice(int recyclerViewPrice) {
        this.recyclerViewPrice = recyclerViewPrice;
    }

    private int recyclerViewPrice;

    public String getrecyclerViewTitleText() {
        return recyclerViewTitleText;
    }

    public void setrecyclerViewTitleText(String recyclerViewtTitleText) {
        this.recyclerViewTitleText = recyclerViewtTitleText;
    }

    public int getrecyclerViewImage() {
        return recyclerViewImage;
    }

    public void setrecyclerViewImage(int recyclerViewImage) {
        this.recyclerViewImage = recyclerViewImage;
    }
}