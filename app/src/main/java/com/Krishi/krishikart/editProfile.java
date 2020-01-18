package com.Krishi.krishikart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class editProfile extends AppCompatActivity {

    EditText editName,editPhone,editAddress;
    Button saveChange;
    DatabaseReference reff;
    User user=new User();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_edit);
        editName=findViewById(R.id.editName);
        editAddress=findViewById(R.id.editAddress);
        editPhone=findViewById(R.id.editPhone);
        saveChange=findViewById(R.id.saveChanges);
//
//        String Name=editName.getText().toString();
//        String Address=editAddress.getText().toString();
//        String Phone=editPhone.getText().toString();



       reff= FirebaseDatabase.getInstance().getReference().child("User");
       saveChange.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               user.setName(editName.getText().toString());
               user.setAddress(editAddress.getText().toString().trim());
               user.setPhone(editPhone.getText().toString().trim());
               reff.push().setValue(user);
               Toast.makeText(editProfile.this,"data inserted Successfully",Toast.LENGTH_SHORT).show();

           }
       });
    }
}

