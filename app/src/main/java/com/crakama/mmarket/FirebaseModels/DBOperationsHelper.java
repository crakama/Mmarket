package com.crakama.mmarket.FirebaseModels;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by User on 10/20/2016.
 */

public class DBOperationsHelper {

    Boolean savedproductDetails;
    DatabaseReference dbref;


    /**
     * NoticeBoardModel REFERENCE
     */

    public DBOperationsHelper(DatabaseReference db){
        this.dbref = db;
    }




    public Boolean saveProductDetails(ProductModel productModel){
        if(productModel == null){
            savedproductDetails = false;
        }else{
            try {
                dbref.child("ProductModel").push().setValue(productModel);
                savedproductDetails = true;
            } catch (DatabaseException e) {

                e.printStackTrace();
                savedproductDetails = false;
            }
        }
        return savedproductDetails;
    }
}
