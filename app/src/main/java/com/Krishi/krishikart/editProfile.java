package com.Krishi.krishikart;

import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class editProfile extends AppCompatActivity {
Context context;
    public static final int RESULT_LOAD_IMAGE=1;
    private CircleImageView ProfileImage;
    EditText editName,editPhone,editAddress;
    Button saveChange;
    DatabaseReference reff;
    User user=new User();
    private static final int PICK_IMAGE=100;
    Uri imageUri;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser currentUser=auth.getCurrentUser();
    String imageUrl;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==PICK_IMAGE){
            imageUri=data.getData();
            ProfileImage.setImageURI(imageUri);
            final StorageReference ref= FirebaseStorage.getInstance().getReference().child("profile").child(currentUser.getUid().toString());
            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                   ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                       @Override
                       public void onSuccess(Uri uri) {

                           imageUrl=uri.toString();

                       }
                   });


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
                Glide.with(context).load(Uri.parse(user.getImageurl()));
                reff.push().setValue(user);
                Toast.makeText(editProfile.this, "data inserted Successfully", Toast.LENGTH_SHORT).show();


            }
        });
    }
}

