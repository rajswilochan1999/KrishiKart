package com.Krishi.krishikart;



import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MenActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    public static final String TAG="MyTag";
    private TextView mOutputText;
    private Button mBtnSubscribe;
    private Button mBtnUnSubscribe;

    //this is declared as default text
    String text="Vegetables";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_men);

        mOutputText=findViewById(R.id.tv_output);
        mBtnSubscribe=findViewById(R.id.btn_sub);
        mBtnUnSubscribe=findViewById(R.id.btn_unsub);

//this is for spinner


        Spinner spinner=findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.numbers,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);







        // FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        //use if menifest me u4universe....lecture 5 ,4:43
        // is neeche k code ka overide se leke getintent tk wala lecture 9 m nhi h

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {

                    if(task.isSuccessful()){
                        String token=task.getResult().getToken();
                        Log.d(TAG,"onComplete: Token" +token);
                        mOutputText.setText("Token generated");

                    }
                    else {
                        mOutputText.setText("Token generation failed");
                    }
                });


        if(getIntent()!=null && getIntent().hasExtra("key1")){
            mOutputText.setText(" ");

            for(String key:getIntent().getExtras().keySet()){
                Log.d(TAG,"onCreate: Key" +key+" Data: "+getIntent().getExtras().getString(key));
                mOutputText.append(getIntent().getExtras().getString(key)+"\n");

            }
        }

        mBtnSubscribe.setOnClickListener(view-> FirebaseMessaging.getInstance().subscribeToTopic(text)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                        Toast.makeText(this, " "+text+"Topic Subscribed", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, " "+text+"Topic Subscrption failed", Toast.LENGTH_SHORT).show();
                }));

        mBtnUnSubscribe.setOnClickListener(view-> FirebaseMessaging.getInstance().unsubscribeFromTopic(text)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                        Toast.makeText(this, " "+text+"Topic UnSubscribed", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, " "+text+"Topic UnSubscrption failed", Toast.LENGTH_SHORT).show();
                }));





    }















    //for spinner below two metjods are and main activity implements adapter.something


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text=parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),"you selected"+text+"Topic",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        Toast.makeText(parent.getContext(),"you selected nothing"+text+"Topic is by default",Toast.LENGTH_SHORT).show();

    }















}

