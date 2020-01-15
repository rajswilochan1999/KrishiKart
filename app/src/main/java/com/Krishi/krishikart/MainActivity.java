package com.Krishi.krishikart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        bottomNavigationView=findViewById(R.id.bottomnavigation);
        frameLayout=findViewById(R.id.framelayout);
        bottomNavigationView.setOnNavigationItemSelectedListener(listView);
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
                    //fragment = new PostFragment();
                    //switchFragment(fragment);
                    //return true;
                    break;
                case R.id.Add:
                    //fragment = new AddFragment();
                    //switchFragment(fragment);
                    return true;
                case R.id.profile:
                    //fragment = new ProfileFragment();
                    //switchFragment(fragment);
                    return true;
            }
            return false;
        }
    };
}
