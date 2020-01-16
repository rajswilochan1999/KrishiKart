package com.Krishi.krishikart;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyHolder>{

    Context context;
    ArrayList<PojoLinear> pojoLinears;

    public HomeAdapter(Context context,ArrayList<PojoLinear> pojoLinears){
        this.context=context;
        this.pojoLinears=pojoLinears;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){

        View view= LayoutInflater.from(context).inflate(R.layout.homecard_view,viewGroup,false);
        MyHolder myHolder=new MyHolder(view);
        return myHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder,int i){

        myHolder.username.setText(pojoLinears.get(i).getUsername());
        myHolder.phonenumber.setText(pojoLinears.get(i).getPhonenumber());
        Glide.with(context).load(Uri.parse(pojoLinears.get(i).getProductimage())).into(myHolder.productimage);
        myHolder.productname.setText(pojoLinears.get(i).getProductname());
        myHolder.productquantity.setText(pojoLinears.get(i).getProductquantity());
        myHolder.pricerange.setText(pojoLinears.get(i).getPricerange());
    }
    @Override
    public int getItemCount(){
        return pojoLinears.size();
    }
    public static class MyHolder extends RecyclerView.ViewHolder{

        TextView username,productname,phonenumber,productquantity,pricerange;
        ImageView productimage;

        public MyHolder(@NonNull View itemView){

            super(itemView);

            username=itemView.findViewById(R.id.username);
            productname=itemView.findViewById(R.id.productname);
            productimage=itemView.findViewById(R.id.productimage);
            phonenumber=itemView.findViewById(R.id.phonenumber);
            productquantity=itemView.findViewById(R.id.productquantity);
            pricerange=itemView.findViewById(R.id.pricerange);
        }
    }
}

