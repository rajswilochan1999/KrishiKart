package com.Krishi.krishikart;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_post, container, false);

        getActivity().setTitle("Post");

        tabLayout=root.findViewById(R.id.tabs);
        viewPager=root.findViewById(R.id.viewpager);
        //getSupportActionBar().setElevation(0);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getChildFragmentManager());
        adapter.add(new AllpostFragment(),"All Post");
        adapter.add(new MypostFragment(),"My Post");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return root;
    }
    public class ViewPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragments=new ArrayList<>();
        ArrayList<String> strings=new ArrayList<>();
        public ViewPagerAdapter(FragmentManager fm){
            super(fm);
        }
        @Override
        public Fragment getItem(int i){
            switch (i){
                case 0:return new AllpostFragment();
                case 1:return new MypostFragment();
                default:return null;
            }
        }
        @Override
        public int getCount(){
            return fragments.size();
        }
        public void add(Fragment fr,String str){
            fragments.add(fr);
            strings.add(str);
        }
        @Nullable
        @Override
        public CharSequence getPageTitle(int position){
            return strings.get(position);
        }
    }
}
