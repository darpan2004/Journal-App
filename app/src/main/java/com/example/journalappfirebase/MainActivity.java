package com.example.journalappfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    EditText usernameEditText,passwordEditText;
    TextView warningTextView;
    Button loginButton,createAccountButton;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private DocumentReference userRef=db.collection("Users").document("User1");
    private CollectionReference collectionReference=db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameEditText=findViewById(R.id.usernameEditText);
        passwordEditText=findViewById(R.id.passwordEditText);
        loginButton=findViewById(R.id.loginButton);
        createAccountButton=findViewById(R.id.createAccountButton);
        warningTextView=findViewById(R.id.warningTextView);
        warningTextView.setVisibility(View.INVISIBLE);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(),CreateAccountActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=usernameEditText.getText().toString().trim();
                String password=passwordEditText.getText().toString().trim();

                if(username.equals("") || password.equals("")){
                    warningTextView.setVisibility(View.VISIBLE);
                }
                else {

                    collectionReference.get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    //Looping through all  the documents in collection


                                    for(QueryDocumentSnapshot snapshots:queryDocumentSnapshots){
                                        if(snapshots.getString("username")!=null){
                                        // Log.v("Jai Shree Ram",snapshots.getString("username"));
                                        if(snapshots.getString("username").equals(username)){

                                         //   Toast.makeText(MainActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                                            if(snapshots.getString("password").equals(password)){
                                                Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                                                intent.putExtra("username",username);
                                                // Get the SharedPreferences object
                                                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

// Edit the SharedPreferences to save the username
                                                SharedPreferences.Editor editor = preferences.edit();


                                                editor.putString("username", username);
                                                editor.apply();
                                                startActivityForResult(intent, 1);
                                                warningTextView.setVisibility(View.INVISIBLE);
                                            }
                                            else{
                                                Toast.makeText(MainActivity.this, "Wrong password for the username: "+username, Toast.LENGTH_SHORT).show();
                                            }}
                                        else{
                                            Toast.makeText(MainActivity.this, "No account with the following username ", Toast.LENGTH_SHORT).show();
                                        }
                                        }
                                        //below code for step8
                                        //  Tranfroming snapshot into objects
                                        //Each document is now a object of type friend

//                                        Users user=snapshots.toObject(Users.class);
//
//                                        //ADding object data to string
//                                        data+="Name :"+friend.getName()+", email: "+friend.getEmail();

                                    }

                                }
                            });

                }
            }
        });
    }
}