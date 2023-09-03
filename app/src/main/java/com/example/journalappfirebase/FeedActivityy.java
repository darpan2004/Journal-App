package com.example.journalappfirebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.BitmapDrawableKt;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.firestore.auth.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class FeedActivityy extends AppCompatActivity {
   public static ArrayList<FeedModel> list;

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
        list=new ArrayList<>();

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
                Bitmap bitmap=((BitmapDrawable)postImageView.getDrawable()).getBitmap();
                //FeedModel model=new FeedModel(titleEditText.getText().toString(),thoughtsEditText.getText().toString(),bitmap);
           //     list.add(model);
                Intent intent=new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("title",titleEditText.getText().toString());
                intent.putExtra("thoughts",thoughtsEditText.getText().toString());
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                intent.putExtra("imageBytes", byteArray);
                intent. putExtra("BitmapImage", bitmap);
                startActivity(intent);

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