package com.Krishi.krishikart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PhoneActivity extends AppCompatActivity {
    EditText phone;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        phone = (EditText) findViewById(R.id.phon);
        btn=(Button)findViewById(R.id.signin);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String mobile = phone.getText().toString().trim();

                if(mobile.isEmpty() || mobile.length() < 10){
                    phone.setError("Enter a valid mobile");
                    phone.requestFocus();
                    return;
                }

                Intent intent = new Intent(PhoneActivity.this, VerifyphoneActivity.class);
                intent.putExtra("mobile", mobile);
                finish();
                startActivity(intent);
            }
        });
    }
}
