package com.example.journalappfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    JournalAdapter journalAdapter;
    ArrayList<FeedModel> feedList;
    RecyclerView userRecyclerView;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String username;
    private DocumentReference friendRef;
    private CollectionReference collectionReference=db.collection("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        userRecyclerView=findViewById(R.id.userRecyclerView);
        userRecyclerView.setHasFixedSize(true);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent=getIntent();
       username= intent.getStringExtra("username");


        collectionReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //Looping through all  the documents in collection
                        String data="";
                        for(QueryDocumentSnapshot snapshots:queryDocumentSnapshots){
                            Log.v("Jai Shree Ram",snapshots.getString("username"));
                            if(snapshots.getString("username").equals(username)){
                                if(snapshots.getString("password").equals("abcd")){
                                    String titlee=snapshots.getString("title");
                                    String thoughtss=snapshots.getString("thoughts");
                                    String base64=snapshots.getString("imageview");
                                    FeedModel model=new FeedModel();
                                    model.setTitle(titlee);
                                    model.setThoughts(thoughtss);
                                    model.setImageview(base64);
                                    model.setUsername(username);
                                    model.setPassword("abcd");
                                    feedList.add(model);
                                    journalAdapter.notifyDataSetChanged();
                                }
                            }
                            //below code for step8
                            //  Tranfroming snapshot into objects
                            //Each document is now a object of type friend



                        }
                    }
                });


//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        String storedValue = sharedPreferences.getString("username", "defaultValue");
        Toast.makeText(this, "hi"+username, Toast.LENGTH_SHORT).show();
      //  loadFeed();
        feedList=new ArrayList<>();
        for (int i = 0; i < feedList.size() ; i++) {
            Log.i("ji",feedList.get(i).getTitle());
        }
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
            //Toast.makeText(this, username+" ", Toast.LENGTH_SHORT).show();
            intent.putExtra("username",username);
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
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String storedValue = sharedPreferences.getString("key", "defaultValue"); // Replace "key" and "defaultValue"

            friendRef=db.collection("Users").document(storedValue);
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
//                                byte[] imageData = Base64.decode(base64, Base64.DEFAULT);
//
//// Convert the byte array back into a Bitmap
//                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                                FeedModel model=new FeedModel();
                                model.setTitle(title);
                                model.setThoughts(thoughts);
                                model.setUsername(username);
                                model.setImageview(base64);
                                model.setPassword("abcd");
                                feedList.add(model);
                                Toast.makeText(UserActivity.this, title+" , "+thoughts, Toast.LENGTH_SHORT).show();
                                journalAdapter.notifyDataSetChanged();
                            }
                        }
                    });

        }
    }
}