package com.crakama.mmarket.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.crakama.mmarket.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class UploadImage extends AppCompatActivity {

    private Button mUploadImg;
    private ImageView mImageView;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;
    EditText editTextPName;
    String productname;
    private static final int CAMERA_REQUEST_CODE = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        storageReference = FirebaseStorage.getInstance().getReference();

        mUploadImg = (Button) findViewById(R.id.btnUploadImg);
        mImageView = (ImageView) findViewById(R.id.imgView);
        editTextPName = (EditText) findViewById(R.id.editTextProductImage);

        productname = editTextPName.getText().toString();
        progressDialog = new ProgressDialog(this);
        mUploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Imgintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(Imgintent, CAMERA_REQUEST_CODE);
            }
        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            //Bitmap photo = (Bitmap) data.getExtras().get("data");
//            //mImageView.setImageBitmap(photo);
//            Uri uri = data.getData();
//            StorageReference  filepath = storageReference.child("Photos").child(uri.getLastPathSegment());
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(UploadImage.this, "SUCCESS", Toast.LENGTH_LONG).show();
//                }
//            });
//
//
//        }
//
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            //set the progress dialog
            progressDialog.setMessage("Uploding image...");
            progressDialog.show();

            //get the camera image
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] databaos = baos.toByteArray();

            //set the image into imageview
            mImageView.setImageBitmap(bitmap);
          //String img = "fire"

            //Firebase storage folder where you want to put the images
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();

            //name of the image file (add time to have different files to avoid rewrite on the same file)
            StorageReference imagesRef = storageRef.child(productname + new Date().getTime());

             //TO DO
            //send this name to database
            //upload image
            UploadTask uploadTask = imagesRef.putBytes(databaos);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(UploadImage.this, "Sending failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    // handle success
                     progressDialog.dismiss();
                    }
            } );}
    }
}

// Save the activity state when it's going to stop.
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putParcelable("picUri", picUri);
//    }
//
//    // Recover the saved state when the activity is recreated.
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        picUri= savedInstanceState.getParcelable("picUri");
//
//    }

