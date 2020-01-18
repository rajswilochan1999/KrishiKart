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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;

public class NewpostActivity extends AppCompatActivity {

    private Button btnChoose, btnUpload;
    private CropImageView imageView;
    private EditText chatdes;
private TextView username;

    private boolean isChanged = false;
    private Uri filePath=null;
    private final int PICK_IMAGE_REQUEST = 71;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference2;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();
    Calendar calendar= Calendar.getInstance();
    SimpleDateFormat currretdate=new SimpleDateFormat("dd, MM, yyyy");
    String saveCurrentDate=currretdate.format(calendar.getTime());

    SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss");
    String saveCurrentTime=currentTime.format(calendar.getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpost);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        storageReference = storage.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Post");
        //databaseReference2 = FirebaseDatabase.getInstance().getReference().child(currentUser.getUid().toString());
        chatdes=(EditText) findViewById(R.id.postmsg);
        btnChoose = (Button) findViewById(R.id.imagepost);
        btnUpload = (Button) findViewById(R.id.sendpost);
        imageView = (CropImageView) findViewById(R.id.cropImageView);
        username=(TextView)findViewById(R.id.postusername);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Add Post");
        /*databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if(postSnapshot.getValue().toString().equals(currentUser.getUid().toString())) {
                        final String username=postSnapshot.child("username").getValue().toString();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(NewpostActivity.this, "Failed "+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
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
                btnChoose.setText("Image selected");
                //imageView.setImageUriAsync(filePath);

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
                                    String name = "Kalpesh";
                                    PostPojolinear pojoLinear = new PostPojolinear("", name, saveCurrentDate,message,imageURL.toString(),saveCurrentTime,currentUser.getUid().toString());

                                    String postid = databaseReference.push().getKey();

                                    databaseReference.child(postid).setValue(pojoLinear);
                                    //Intent intent = new Intent(NewpostActivity.this, MainActivity.class);
                                    finish();
                                    //startActivity(intent);
                                    onBackPressed();
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
            PostPojolinear pojoLinear = new PostPojolinear("", name, saveCurrentDate,message,"",saveCurrentTime,currentUser.getUid().toString());
            String postid = databaseReference.push().getKey();

            databaseReference.child(postid).setValue(pojoLinear);
            //Intent intent = new Intent(NewpostActivity.this, MainActivity.class);
            finish();
            //startActivity(intent);
            onBackPressed();
            Toast.makeText(NewpostActivity.this, "Uploaded!", Toast.LENGTH_SHORT).show();
        }
    }
    /*public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;
    }*/
}
