package com.example.journalappfirebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.BitmapDrawableKt;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class FeedActivityy extends AppCompatActivity {

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    EditText titleEditText,thoughtsEditText;
    Button  saveButton;
    boolean importt =true;
    private static final int PICK_IMAGE = 1;
    ImageView postImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_activityy);
        postImageView=findViewById(R.id.postImageView);
        titleEditText=findViewById(R.id.titleEditText);
        thoughtsEditText=findViewById(R.id.thoughtsEditText);
        saveButton=findViewById(R.id.saveButton);

        postImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(importt) {
                    openGallery(view);
                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=titleEditText.getText().toString();
                String thoughts=thoughtsEditText.getText().toString();
                Bitmap bitmap=((BitmapDrawable)postImageView.getDrawable()).getBitmap();
                Intent intent=getIntent();

                FeedModel model=new FeedModel();
                model.setTitle(title);
                model.setThoughts(thoughts);
                model.setPassword("abcd");
                model.setUsername( intent.getStringExtra("username"));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                byte[] imageData = baos.toByteArray();

                String base64Image = Base64.encodeToString(imageData, Base64.DEFAULT);
                model.setImageview(base64Image);
                db.collection("Users")
                        .document(title)
                        .set(model)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            //   Toast.makeText(FeedActivityy.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        });
                Intent resultIntent = new Intent();
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("key", title); // Replace "key" and "value" with your data
                editor.apply();
                setResult(RESULT_OK, resultIntent);
                finish();


                //FeedModel model=new FeedModel(titleEditText.getText().toString(),thoughtsEditText.getText().toString(),bitmap);
           //     list.add(model);
//                Intent intent=new Intent(getApplicationContext(), UserActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                intent.putExtra("title",titleEditText.getText().toString());
//                intent.putExtra("thoughts",thoughtsEditText.getText().toString());
////                ByteArrayOutputStream stream = new ByteArrayOutputStream();
////                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
////                byte[] byteArray = stream.toByteArray();
////                intent.putExtra("imageBytes", byteArray);
//                intent. putExtra("BitmapImage", bitmap);
//
//                startActivity(intent);

            }
        });

    }
    public void openGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
        importt=false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK && data!=null){
            Uri selectedIMageUri=data.getData();
            postImageView.setImageURI(selectedIMageUri);
        }
    }
}