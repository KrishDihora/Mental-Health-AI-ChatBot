package com.codecrush.mentalhealthchatbot.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/*import com.codecrush.pjeclass.R;
import com.codecrush.pjeclass.helpers.MethodsHelper;
import com.codecrush.pjeclass.models.MessageDataModel;*/
import com.codecrush.mentalhealthchatbot.R;
import com.codecrush.mentalhealthchatbot.helper.MethodHelper;
import com.codecrush.mentalhealthchatbot.model.BotMessageDataModel;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RecyclerMessageAdapter extends RecyclerView.Adapter<RecyclerMessageAdapter.ViewHolder> {

    Context context;
    ArrayList<BotMessageDataModel.Message> arr_msg_data;

    public RecyclerMessageAdapter(Context context, ArrayList<BotMessageDataModel.Message> arr_doubt_data) {
        this.context = context;
        this.arr_msg_data = arr_doubt_data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.message_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if (arr_msg_data.get(position).getSender()== MethodHelper.getuserName(context))
        {
            Log.d("email111",arr_msg_data.get(position).getSender());
            Log.d("email111",MethodHelper.getuserName(context));

            holder.cv_sent_message.setVisibility(View.VISIBLE);
            holder.cv_received_message.setVisibility(View.GONE);

            holder.tv_sent_message.setText(arr_msg_data.get(position).getMessage());
            holder.tv_sent_timestamp.setText(MethodHelper.getTime(arr_msg_data.get(position).getCreatedAt().toString()));


        }
        else
        {

            holder.cv_sent_message.setVisibility(View.GONE);
            holder.cv_received_message.setVisibility(View.VISIBLE);

            holder.tv_sender_name.setText("Bot");
            holder.tv_received_message.setText(arr_msg_data.get(position).getMessage());
            holder.tv_received_timestamp.setText(MethodHelper.getTime(arr_msg_data.get(position).getCreatedAt()));

            /*Log.d("adapter",arr_msg_data.get(position).getSender_name());
            Log.d("adapter",arr_msg_data.get(position).getContent());
            Log.d("adapter",MethodsHelper.MillisecondsToTime(arr_msg_data.get(position).getMsg_time()));*/

        }

    }



    @Override
    public int getItemCount() {
        return arr_msg_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_sender_name,tv_received_message,tv_received_timestamp;
        TextView tv_sent_message,tv_sent_timestamp;
        CardView cv_received_message,cv_sent_message;
        public ViewHolder(@NonNull View view) {
            super(view);

            tv_sender_name=view.findViewById(R.id.tv_sender_name);
            tv_received_message=view.findViewById(R.id.tv_received_message);
            tv_received_timestamp=view.findViewById(R.id.tv_received_timestamp);


            tv_sent_message=view.findViewById(R.id.tv_sent_message);
            tv_sent_timestamp=view.findViewById(R.id.tv_sent_timestamp);

            cv_sent_message=view.findViewById(R.id.card_sent_message);
            cv_received_message=view.findViewById(R.id.card_received_message);
        }
    }

    private String getInitials(String name) {
        String[] words = name.split("\\s+");
        String initials = "";
        for (String word : words) {
            if (!word.isEmpty()) {
                initials += word.charAt(0);  // Take the first character
            }
        }
        return initials.substring(0, Math.min(initials.length(), 2)).toUpperCase();  // Return first 2 initials
    }
}
