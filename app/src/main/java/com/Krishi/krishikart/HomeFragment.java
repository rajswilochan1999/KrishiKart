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
    StorageReference storageRef = storage.getReference().child("Product Images");
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Products");

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root=inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=root.findViewById(R.id.homerecyclerview);

        getActivity().setTitle("Home");

        //linearlist();
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

                        //PojoLinear pojoLinear = postSnapshot.getValue(PojoLinear.class);
                        String image=postSnapshot.child("image").getValue().toString();
                        String pname=postSnapshot.child("pname").getValue().toString();
                        String quantity=postSnapshot.child("quantity").getValue().toString();
                        String price=postSnapshot.child("price").getValue().toString();
                        list.add(new PojoLinear(image,pname,"kks","2672771267",quantity,price));
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
    /*public void linearlist(){
        list.add(new PojoLinear("","Mango","team","1234567812","100","1067"));
        list.add(new PojoLinear("","wheat","team","1234567812","100","1067"));

        list.add(new PojoLinear("","Mango","team","1234567812","100","1067"));

        list.add(new PojoLinear("","Mango","team","1234567812","100","1067"));

        list.add(new PojoLinear("","Mango","team","1234567812","100","1067"));

        list.add(new PojoLinear("","Mango","team","1234567812","100","1067"));

        list.add(new PojoLinear("","Mango","team","1234567812","100","1067"));
    }*/
}
