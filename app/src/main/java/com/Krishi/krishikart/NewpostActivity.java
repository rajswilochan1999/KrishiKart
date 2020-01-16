package com.Krishi.krishikart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class NewpostActivity extends AppCompatActivity {

    private Button btnChoose, btnUpload;
    private CropImageView imageView;
    private EditText chatdes;

    private boolean isChanged = false;
    private Uri filePath=null;
    private final int PICK_IMAGE_REQUEST = 71;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpost);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Post");
        chatdes=(EditText) findViewById(R.id.postmsg);
        btnChoose = (Button) findViewById(R.id.imagepost);
        btnUpload = (Button) findViewById(R.id.sendpost);
        imageView = (CropImageView) findViewById(R.id.cropImageView);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Add Post");
        actionBar.setDisplayHomeAsUpEnabled(true);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void chooseImage() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(NewpostActivity.this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                filePath = result.getUri();
                imageView.setImageUriAsync(filePath);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
                Toast.makeText(this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void uploadImage() {

        if(chatdes.getText().toString().equals("") && filePath==null){
            Toast.makeText(NewpostActivity.this,"Please Write Something or Select Image",Toast.LENGTH_SHORT).show();
        }
        else if(filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/" + System.currentTimeMillis());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
                                @Override
                                public void onSuccess(Uri imageURL){
                                    progressDialog.dismiss();
                                    String message = chatdes.getText().toString().trim();
                                    String name = "kalpesh";
                                    PostPojolinear pojoLinear = new PostPojolinear("", name, "Today",message,imageURL.toString(),currentUser.getUid().toString());

                                    String postid = databaseReference.push().getKey();

                                    databaseReference.child(postid).setValue(pojoLinear);
                                    Intent intent = new Intent(NewpostActivity.this, MainActivity.class);
                                    finish();
                                    startActivity(intent);
                                    //onBackPressed();
                                    Toast.makeText(NewpostActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(NewpostActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
        else if(!chatdes.getText().toString().equals("")){
            String message = chatdes.getText().toString().trim();
            String name = "kalpesh";
            PostPojolinear pojoLinear = new PostPojolinear("", name, "Today",message,"",currentUser.getUid().toString());
            String postid = databaseReference.push().getKey();

            databaseReference.child(postid).setValue(pojoLinear);
            Intent intent = new Intent(NewpostActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
            Toast.makeText(NewpostActivity.this, "Uploaded!", Toast.LENGTH_SHORT).show();
        }
    }
    /*public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;
    }*/
}
