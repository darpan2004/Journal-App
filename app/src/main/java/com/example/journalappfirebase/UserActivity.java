package com.example.journalappfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    JournalAdapter journalAdapter;
    ArrayList<FeedModel> feedList;
    RecyclerView userRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        userRecyclerView=findViewById(R.id.userRecyclerView);
        userRecyclerView.setHasFixedSize(true);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadFeed();

    }
    public void loadFeed(){
        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String thoughts=intent.getStringExtra("thoughts");
        //byte[] byteArray =intent.getByteArrayExtra("imageBytes");
        Bitmap bitmap = (Bitmap) intent. getParcelableExtra("BitmapImage");
        feedList=new ArrayList<>();
        journalAdapter=new JournalAdapter(feedList,this);
        userRecyclerView.setAdapter(journalAdapter);
        FeedModel model=new FeedModel(title,thoughts,bitmap);
        feedList.add(model);
        journalAdapter.notifyDataSetChanged();
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
            startActivity(new Intent(getApplicationContext(),FeedActivityy.class));
            Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

}