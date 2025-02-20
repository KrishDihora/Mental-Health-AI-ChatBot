package com.codecrush.mentalhealthchatbot.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codecrush.mentalhealthchatbot.R;

public class EmergencyFragment extends Fragment
{

    View view;
    TextView TVContact,TVContact1,TVContact2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (view!=null)
        {
            return view;
        }
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_emergency, container, false);

        TVContact=view.findViewById(R.id.tv_contact);
        TVContact1=view.findViewById(R.id.tv_contact1);
        TVContact2=view.findViewById(R.id.tv_contact2);

        TVContact.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "14416"));
                startActivity(intent);
            }
        });

        TVContact1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "18008914416"));
                startActivity(intent);
            }
        });

        TVContact2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "18005990019"));
                startActivity(intent);
            }
        });

        return view;

    }
}