package com.Krishi.krishikart;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class editProfile extends AppCompatActivity {

    public static final int RESULT_LOAD_IMAGE=1;
    private CircleImageView ProfileImage;
    EditText editName,editPhone,editAddress;
    Button saveChange;
    DatabaseReference reff;
    User user=new User();
    private static final int PICK_IMAGE=100;
    Uri imageUri;
    FirebaseAuth mauth;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==PICK_IMAGE){
            imageUri=data.getData();
            ProfileImage.setImageURI(imageUri);
            StorageReference ref= FirebaseStorage.getInstance().getReference().child("profilePictures/").child(mauth.getUid());
            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {




                }
            });

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_edit);
        editName = findViewById(R.id.editName);
        editAddress = findViewById(R.id.editAddress);
        editPhone = findViewById(R.id.editPhone);
        saveChange = findViewById(R.id.saveChanges);
        ProfileImage = (CircleImageView) findViewById(R.id.profile);

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery,PICK_IMAGE);
              }
        });


        reff = FirebaseDatabase.getInstance().getReference().child("User");
        saveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.setName(editName.getText().toString());
                user.setAddress(editAddress.getText().toString().trim());
                user.setPhone(editPhone.getText().toString().trim());
                reff.push().setValue(user);
                Toast.makeText(editProfile.this, "data inserted Successfully", Toast.LENGTH_SHORT).show();


            }
        });
    }
}

