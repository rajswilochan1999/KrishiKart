package com.Krishi.krishikart;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;


public class editProfile extends AppCompatActivity {

    private CircleImageView ProfileImage;
    EditText editName,editPhone,editAddress;
    Button saveChange;
    DatabaseReference reff;
    User user=new User();
    private static final int REQUEST_CODE=23;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE && resultCode ==RESULT_OK){

            if(data!=null){
                Uri uri=data.getData();

                Toast.makeText(this,"Uri"+uri,Toast.LENGTH_LONG).show();
                Toast.makeText(this,"Path"+uri.getPath(),Toast.LENGTH_SHORT).show();

            }

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_edit);
        editName=findViewById(R.id.editName);
        editAddress=findViewById(R.id.editAddress);
        editPhone=findViewById(R.id.editPhone);
        saveChange=findViewById(R.id.saveChanges);
        ProfileImage=(CircleImageView)findViewById(R.id.profile);

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("images/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,REQUEST_CODE);

            }
        });




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

