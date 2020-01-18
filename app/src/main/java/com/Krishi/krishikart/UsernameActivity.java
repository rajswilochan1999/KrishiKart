package com.Krishi.krishikart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsernameActivity extends AppCompatActivity {

    String mobile;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(currentUser.getUid().toString());
    EditText user,mobil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
        mobil=(EditText) findViewById(R.id.m);
        user=(EditText) findViewById(R.id.u);
        mobil.setText(mobile);
        databaseReference.child("mobile").setValue(mobile);
        Button button = (Button) findViewById(R.id.adduser);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String u=user.getText().toString();
                databaseReference.child("username").setValue(u);
                Intent intent=new Intent(UsernameActivity.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
