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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    HomeAdapter homeAdapter;
    ArrayList<PojoLinear> list=new ArrayList<>();

    ProgressDialog progressDialog;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference().child("images");
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Post");

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root=inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=root.findViewById(R.id.homerecyclerview);
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

                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                        PojoLinear pojoLinear = postSnapshot.getValue(PojoLinear.class);
                        list.add(pojoLinear);
                    }
                    homeAdapter=new HomeAdapter(getContext(),list);
                    recyclerView.setAdapter(homeAdapter);
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

}
