package com.codecrush.mentalhealthchatbot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codecrush.mentalhealthchatbot.R;

import java.util.ArrayList;

public class RecyclerPastChatAdapter extends RecyclerView.Adapter<RecyclerPastChatAdapter.ViewHolder> {

    Context context;
    ArrayList<String> Strings;

    public RecyclerPastChatAdapter(Context context, ArrayList<String> Strings){
        this.context=context;
        this.Strings=Strings;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v =  LayoutInflater.from(context).inflate(R.layout.layout_past_chat, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final String temp= Strings.get(position);

        holder.et_name.setText(Strings.get(position));
        

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {

        return Strings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView et_name, docid;
        ImageView img;
        public ViewHolder(View itemview){
            super(itemview);
            et_name=itemview.findViewById(R.id.et_name);

        }

    }




}
