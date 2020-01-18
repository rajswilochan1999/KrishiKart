package com.Krishi.krishikart;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class MypostFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    PostAdapter postAdapter;
    ArrayList<PostPojolinear> list=new ArrayList<>();

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();

    ProgressDialog progressDialog;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference().child("images");
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Post");

    public MypostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_allpost, container, false);
        //linearlist();
        recyclerView=root.findViewById(R.id.postrecyclerview);
        recyclerView.hasFixedSize();
        layoutManager=new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        try {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                        PostPojolinear pojoLinear = postSnapshot.getValue(PostPojolinear.class);
                        if(pojoLinear.getUuid().equals(currentUser.getUid().toString()))
                                list.add(pojoLinear);
                    }
                    Collections.reverse(list);
                    postAdapter=new PostAdapter(root.getContext(),list);
                    recyclerView.setAdapter(postAdapter);
                    progressDialog.dismiss();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    progressDialog.dismiss();
                    Toast.makeText(root.getContext(), "Failed "+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e ) {
            progressDialog.dismiss();
            Toast.makeText(root.getContext(), "Failed!! "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return root;
    }
    /*public void linearlist(){
        list.add(new PostPojolinear("","Team","Today","bcuyewfboewyfbw",""));
        list.add(new PostPojolinear("","Team","Today","ewubfuwebfiu",""));
        list.add(new PostPojolinear("","Team","Today","bcuyewfboewyfbw",""));
        list.add(new PostPojolinear("","Team","Today","bcuyewfboewyfbw",""));
        list.add(new PostPojolinear("","Team","Today","bcuyewfboewyfbw",""));
        list.add(new PostPojolinear("","Team","Today","bcuyewfboewyfbw",""));
        list.add(new PostPojolinear("","Team","Today","bcuyewfboewyfbw",""));
        list.add(new PostPojolinear("","Team","Today","bcuyewfboewyfbw",""));
    }*/
}
