package com.Krishi.krishikart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    FirebaseAuth auth=FirebaseAuth.getInstance();
                    FirebaseUser currentUser = auth.getCurrentUser();
                    if(currentUser==null){
                        Intent intent = new Intent(SplashActivity.this,PhoneActivity.class);
                        finish();
                        startActivity(intent);
                    }
                    else{
                        Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                }
            }, 4000);
        }
    }