package com.example.auctionleb;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.testdatabaseimport.Fragments.Favorite_fragment;
import com.example.testdatabaseimport.Fragments.Home_fragment;
import com.example.testdatabaseimport.Fragments.Profile_fragment;
import com.example.testdatabaseimport.Fragments.Settings_fragment;
import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private BubbleTabBar bubbleTabBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = new Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        bubbleTabBar = findViewById(R.id.bubbleTabBar);
//        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_fragment()).addToBackStack(null).commit();

        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                Fragment selectedFragment = new Fragment();
                switch (i) {
                    case (R.id.nav_home):
                        selectedFragment = new Home_fragment();
                        break;
                    case (R.id.nav_favorites):
                        selectedFragment = new Favorite_fragment();
                        break;
                    case (R.id.nav_settings):
                        selectedFragment = new Settings_fragment();
                        break;
                    case (R.id.nav_profile):
                        selectedFragment = new Profile_fragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();

            }
        });


    }

    //    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    Fragment selectedFragment = null;
//                    switch (item.getItemId()) {
//
//                        case (R.id.nav_home):
//                            selectedFragment = new Home_fragment();
//                            break;
//                        case (R.id.nav_favorites):
//                            selectedFragment = new Favorite_fragment();
//                            break;
//                        case (R.id.nav_settings):
//                            selectedFragment = new Settings_fragment();
//                            break;
//                    }
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
//                    return true;
//                }
//            };


}