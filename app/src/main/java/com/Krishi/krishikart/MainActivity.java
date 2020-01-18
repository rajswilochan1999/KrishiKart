package com.Krishi.krishikart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;

    Fragment fragment = null;
    FragmentTransaction fragmentTransaction;

    private void switchFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomnavigation);
        frameLayout = findViewById(R.id.framelayout);
        fragment = new HomeFragment();
        switchFragment(fragment);
        bottomNavigationView.setOnNavigationItemSelectedListener(listView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=this.getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(this,PhoneActivity.class);
                finish();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener listView=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.Home:
                    fragment = new HomeFragment();
                    switchFragment(fragment);
                    return true;
                case R.id.post:
                    fragment = new PostFragment();
                    switchFragment(fragment);
                    return true;
                case R.id.Add:
                    fragment = new AddProductFragment();
                    switchFragment(fragment);
                    return true;
                case R.id.profile:
                    //fragment = new ProfileFragment();
                    //switchFragment(fragment);
                    return true;
            }
            return false;
        }
    };
    public void floatingfunction(View view){
        Intent intent=new Intent(MainActivity.this,NewpostActivity.class);
        startActivity(intent);
    }
}
