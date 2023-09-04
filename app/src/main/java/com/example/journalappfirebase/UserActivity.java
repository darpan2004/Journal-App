package com.example.journalappfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    JournalAdapter journalAdapter;
    ArrayList<Users> feedList;
    RecyclerView userRecyclerView;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private DocumentReference friendRef=db.collection("Users").document("model");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        userRecyclerView=findViewById(R.id.userRecyclerView);
        userRecyclerView.setHasFixedSize(true);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
      //  loadFeed();
        feedList=new ArrayList<>();
        journalAdapter=new JournalAdapter(feedList,this);userRecyclerView.setAdapter(journalAdapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            // Handle the "Add" menu item click here.
            return true;
        }
        else {
            Intent intent = new Intent(UserActivity.this, FeedActivityy.class);
            startActivityForResult(intent,10);

            return true;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            // This means Activity B has finished and returned a result
            // You can perform your tasks here
            friendRef.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            //Lets display Retreived data in textview
                            if(documentSnapshot.exists()){
                                String title=documentSnapshot.getString("title");
                                String thoughts=documentSnapshot.getString("thoughts");
                                String base64=documentSnapshot.getString("imageview");

                                // Decode the Base64 string into a byte array
                                byte[] imageData = Base64.decode(base64, Base64.DEFAULT);

// Convert the byte array back into a Bitmap
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                                Users model=new Users();
                                model.setTitle(title);
                                model.setThoughts(thoughts);
                                model.setImageview(base64);
                                feedList.add(model);
                                Toast.makeText(UserActivity.this, title+" , "+thoughts, Toast.LENGTH_SHORT).show();
                                journalAdapter.notifyDataSetChanged();
                            }
                        }
                    });

        }
    }
}