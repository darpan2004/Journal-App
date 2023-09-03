package com.example.journalappfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateAccountActivity extends AppCompatActivity {
    private TextView createNoteTextView;
    Button signUpButton;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private EditText emailEditTextt,usernameEditTextt,passwordEditTextt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        usernameEditTextt=findViewById(R.id.usernameEditTextt);
        emailEditTextt=findViewById(R.id.emailEditTextt);
        passwordEditTextt=findViewById(R.id.passwordEditTextt);
        signUpButton=findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    private void saveData() {
        String username=usernameEditTextt.getText().toString();
        String password=passwordEditTextt.getText().toString();
        String email=emailEditTextt.getText().toString();
        Users user=new Users();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        db.collection("Users")
                .document("user1")
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreateAccountActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}