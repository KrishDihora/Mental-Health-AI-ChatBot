package com.codecrush.mentalhealthchatbot.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codecrush.mentalhealthchatbot.ApiData;
import com.codecrush.mentalhealthchatbot.R;
import com.codecrush.mentalhealthchatbot.adapter.RecyclerPastChatAdapter;
import com.codecrush.mentalhealthchatbot.helper.MethodHelper;
import com.codecrush.mentalhealthchatbot.helper.RetrofitHelper;
import com.codecrush.mentalhealthchatbot.intrface.InternetCheckInterface;
import com.codecrush.mentalhealthchatbot.intrface.SuccessResponseCallback;
import com.codecrush.mentalhealthchatbot.model.BotMessageDataModel;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PastChatActivity extends AppCompatActivity {

    RecyclerView rv_pastchat;
    ArrayList<String> arrdata = new ArrayList<>();
    ImageView iv_back;
    boolean hasResponseBeenHandled=false;
    RecyclerPastChatAdapter adapter;
    ProgressBar PBRVPastChat;
    TextView TVDataNotFoundRVPastChat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_chat);

        iv_back=findViewById(R.id.back);

        rv_pastchat=findViewById(R.id.rv_past_chat);
        PBRVPastChat=findViewById(R.id.pb_rv_past_chat);
        TVDataNotFoundRVPastChat=findViewById(R.id.tv_data_not_found_rv_past_chat);

        rv_pastchat.setLayoutManager(new LinearLayoutManager(this));

        getPastChatTitle();

        adapter= new RecyclerPastChatAdapter(this,arrdata);
        rv_pastchat.setAdapter(adapter);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void getPastChatTitle()
    {
        MethodHelper.checkInternetConditionByTryAgain(PastChatActivity.this, new InternetCheckInterface()
        {
            @Override
            public void onSuccessInternetCallback()
            {
                Log.d("TAGggggggggggg", MethodHelper.get_ID(PastChatActivity.this));

                ApiData apiData= RetrofitHelper.instanceOfRetrofit(getResources().getString(R.string.urlchat));

                Call<JsonObject> call=apiData.getPastChatTitle(MethodHelper.get_ID(PastChatActivity.this));
                call.enqueue(new Callback<JsonObject>()
                {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
                    {
                        if (!hasResponseBeenHandled)
                        {
                            hasResponseBeenHandled=true;

                            MethodHelper.getResponseCondition(response,PastChatActivity.this,PBRVPastChat,TVDataNotFoundRVPastChat,hasResponseBeenHandled,new SuccessResponseCallback<JsonObject>()
                            {
                                @Override
                                public void onSuccessfullResponse(JsonObject Object)
                                {
                                    Log.d("TAG", String.valueOf(Object.get("conversations").getAsJsonArray().size()));

                                    for (int i=0;i<Object.get("conversations").getAsJsonArray().size();i++)
                                    {
                                        arrdata.add(Object.get("conversations").getAsJsonArray().get(i).getAsJsonObject().get("messages").getAsJsonArray().get(0).getAsJsonObject().get("message").getAsString());
                                        adapter.notifyDataSetChanged();
                                    }

                                   }
                                });
                           }
                        }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t)
                    {
                        if (!hasResponseBeenHandled)
                        {
                            hasResponseBeenHandled=true;

                            MethodHelper.getFailureReason(t,PastChatActivity.this);

                            PBRVPastChat.setVisibility(View.GONE);
                            TVDataNotFoundRVPastChat.setVisibility(View.VISIBLE);
                        }
                    }

                });
            }
        });
    }
}