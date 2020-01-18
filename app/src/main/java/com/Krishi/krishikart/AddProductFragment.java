package com.Krishi.krishikart;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_SHORT;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment {


    private String CategoryName, Description , Price , Pname,saveCurrentDate,saveCurrentTime;
    private Button AddNewProductButton;
    private ImageView InputProductImage;
    private EditText InputProductName,InputProductDescription,InputProductPrice;
    private static final int GalleryPick=1;
    private Uri ImageUri;
    private String productRandomKey,downloadImageuRL;
    private StorageReference ProductImageRef;

    private ProgressDialog loadingBar;

    private DatabaseReference ProductRef;
    View  root;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();


    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_add_product, container, false);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_admin_add_new_product);

        // CategoryName = getIntent().getExtras().get("category").toString();
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");

        AddNewProductButton = (Button) root.findViewById(R.id.add_new_product);
        InputProductImage = (ImageView) root.findViewById(R.id.select_product_image);
        InputProductName = (EditText) root.findViewById(R.id.product_name);
        InputProductDescription = (EditText) root.findViewById(R.id.product_description);
        InputProductPrice = (EditText) root.findViewById(R.id.product_price);
        loadingBar = new ProgressDialog(this.getContext());
        //Toast.makeText(this,CategoryName,Toast.LENGTH_SHORT).show();
        //Toast.makeText(this,"Welcome admin",Toast.LENGTH_SHORT).show();

        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });


        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });
        return root;
    }

    private void OpenGallery() {
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPick && resultCode==RESULT_OK &&data!=null)
        {
            ImageUri=data.getData();
            InputProductImage.setImageURI(ImageUri);
        }
    }

    private void ValidateProductData()
    {
        Description=InputProductDescription.getText().toString();
        Price=InputProductPrice.getText().toString();
        Pname=InputProductName.getText().toString();

        if(ImageUri==null)
        {
            Toast.makeText(this.getContext(),"Product image is mandatory , It should resemble with the original product ...", LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Description))
        {
            Toast.makeText(this.getContext(),"Please write product category......", LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Price))
        {
            Toast.makeText(this.getContext(),"Please write product price......", LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Pname))
        {
            Toast.makeText(this.getContext(),"Please write product name......", LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation() {
        loadingBar.setTitle("Adding New Product");
        loadingBar.setMessage("Dear Kissan Bhai ,Please wait while we are adding your produce... .");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat currretdate=new SimpleDateFormat("MMM, dd, yyyy");
        saveCurrentDate=currretdate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());
        productRandomKey=saveCurrentDate+saveCurrentTime;

        final StorageReference filePath=ProductImageRef.child(ImageUri.getLastPathSegment()+ productRandomKey+".jpg");
        final UploadTask uploadTask=filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message=e.toString();
                Toast.makeText(root.getContext(),"Error"+message, LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(root.getContext(),"product Image uploaded successfully ", LENGTH_SHORT).show();

                Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()) {
                            throw task.getException();
                        }
                        downloadImageuRL=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()) {
                            downloadImageuRL=task.getResult().toString();
                            Toast.makeText(root.getContext(), "Got the product image Url successfully..", LENGTH_SHORT).show();

                            SaveProductInfoToDatabse();
                        }
                    }
                });
            }
        });


    }

    private void SaveProductInfoToDatabse() {
        HashMap<String, Object> productMap=new HashMap<>();
        productMap.put("date",saveCurrentDate);
        productMap.put("uuid",currentUser.getUid().toString());
        productMap.put("time",saveCurrentTime);
        productMap.put("quantity",Description);
        productMap.put("image",downloadImageuRL);
        //productMap.put("quantity",CategoryName);
        productMap.put("price",Price);
        productMap.put("pname",Pname);

        ProductRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    loadingBar.dismiss();
                    Toast.makeText(root.getContext(), "Product is added successfully...", LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    getActivity().finish();
                    startActivity(intent);
                }
                else
                {
                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    getActivity().finish();
                    startActivity(intent);
                    loadingBar.dismiss();
                    String messsage=task.getException().toString();
                    Toast.makeText(root.getContext(),"Error"+messsage, LENGTH_SHORT);
                }
            }
        });

    }
}
