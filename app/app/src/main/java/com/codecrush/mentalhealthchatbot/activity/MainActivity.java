package com.codecrush.mentalhealthchatbot.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.codecrush.mentalhealthchatbot.R;
import com.codecrush.mentalhealthchatbot.fragment.ChatFragment;
import com.codecrush.mentalhealthchatbot.fragment.ConsultDoctorFragment;
import com.codecrush.mentalhealthchatbot.fragment.EmergencyFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    ImageView IVChatList,IVLogout;
    FrameLayout FrameLayout;
    BottomNavigationView bottomNavbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IVChatList = findViewById(R.id.iv_chat_list);
        IVLogout = findViewById(R.id.iv_logout);
        bottomNavbar = findViewById(R.id.navigation);
        FrameLayout=findViewById(R.id.framelayout);

        SharedPreferences userprefrence = getSharedPreferences("user", MODE_PRIVATE);

        if (!userprefrence.contains("_id"))
        {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }


        IVLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences userprefrence = getSharedPreferences("user", MODE_PRIVATE);
                SharedPreferences.Editor editor = userprefrence.edit();

                Map<String,?> allEntries=userprefrence.getAll();

                for (Map.Entry<String ,?> entry:allEntries.entrySet())
                {
                    String key=entry.getKey();
                    editor.remove(key);

                }
                editor.commit();

                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();

            }
        });

        IVChatList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent pastChatIntent = new Intent(getApplicationContext(), PastChatActivity.class);
                startActivity(pastChatIntent);

            }
        });

        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {

                int id=item.getItemId();

                if(id==R.id.chat)
                {
                    loadFragment(new ChatFragment(),1);
                }
                else if (id==R.id.consult_doctor)
                {
                    loadFragment(new ConsultDoctorFragment(),1);
                }
                else if (id==R.id.emergency)
                {
                    loadFragment(new EmergencyFragment(),1);
                }


                return true;
            }
        });
        bottomNavbar.setSelectedItemId(R.id.chat);


    }

    public void loadFragment(Fragment fragment, int flag) {
        FragmentManager fm = getSupportFragmentManager();

        // Use a tag to identify the fragment in the back stack
        String fragmentTag = fragment.getClass().getSimpleName();
        //Log.d("TAGss", fragmentTag);
        Fragment existingFragment = fm.findFragmentByTag(fragmentTag);

        // Only add the fragment if it is not already in the back stack
        if (existingFragment == null || !existingFragment.isAdded())
        {
            FragmentTransaction ft = fm.beginTransaction();

            ft.replace( R.id.framelayout, fragment, fragmentTag);
            ft.addToBackStack(fragmentTag);
            ft.commit();
        }
        else
        {
            //---
        }


    }

    @Override
    public void onBackPressed()
    {
        FragmentManager fm = getSupportFragmentManager();

        if(fm.getBackStackEntryCount() > 1)
        {
            fm.popBackStackImmediate();

            // Get the currently displayed fragment and update the BottomNavigationView
            Fragment currentFragment = fm.findFragmentById(R.id.framelayout);
            //Log.d("TAG", "onBackPressed: "+ R.id.framelayout);

            if (currentFragment instanceof ChatFragment)
            {
                bottomNavbar.setSelectedItemId(R.id.chat);
                //((HomeFragment) currentFragment).handleOnBackPressed();
            }
            else if (currentFragment instanceof ConsultDoctorFragment)
            {
                bottomNavbar.setSelectedItemId(R.id.consult_doctor);
                //((NewsFragment) currentFragment).handleOnBackPressed();
            }
            else if (currentFragment instanceof EmergencyFragment)
            {
                bottomNavbar.setSelectedItemId(R.id.emergency);
                //((FreeContentFragment) currentFragment).handleOnBackPressed(fm);
            }
            else
            {
                //---
            }

        }
        else if (fm.getBackStackEntryCount() == 1)
        {
            finish();
        }
        else
        {
            finish();
            super.onBackPressed();
        }
    }
}