package com.Krishi.krishikart;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyHolder>{

    Context context;
    ArrayList<PostPojolinear> pojoLinears;
    Calendar calendar= Calendar.getInstance();

    public PostAdapter(Context context, ArrayList<PostPojolinear> pojoLinears){
        this.context=context;
        this.pojoLinears=pojoLinears;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){

        MyHolder myHolder;
        //View nview=LayoutInflater.from(context).inflate(R.layout.newpostcard_view,viewGroup,false);
        View view= LayoutInflater.from(context).inflate(R.layout.postcard_view,viewGroup,false);
        myHolder=new MyHolder(view);
        return myHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder,int i){

        myHolder.postusername.setText(pojoLinears.get(i).getPostusername());
        myHolder.postmessage.setText(pojoLinears.get(i).getPostmessage());
        myHolder.postdate.setText(pojoLinears.get(i).getPostdate());
        myHolder.posttime.setText(pojoLinears.get(i).getPosttime());
        //myHolder.postimage.setMaxHeight(10);
        //myHolder.postimage.setMaxWidth(10);
        Glide.with(context).load(Uri.parse(pojoLinears.get(i).getPostimage())).into(myHolder.postimage);
        Glide.with(context).load(Uri.parse(pojoLinears.get(i).getCircularimage())).into(myHolder.circularimage);
    }
    @Override
    public int getItemCount(){
        return pojoLinears.size();
    }
    public static class MyHolder extends RecyclerView.ViewHolder{

        TextView postusername,postdate,postmessage,posttime;
        CircleImageView circularimage;
        ImageView postimage;

        public MyHolder(@NonNull View itemView){

            super(itemView);

            postusername=itemView.findViewById(R.id.postusername);
            postdate=itemView.findViewById(R.id.postdate);
            postimage=itemView.findViewById(R.id.postimage);
            postmessage=itemView.findViewById(R.id.postmessage);
            circularimage=itemView.findViewById(R.id.circularimage);
            posttime=itemView.findViewById(R.id.posttime);
        }
    }
}
