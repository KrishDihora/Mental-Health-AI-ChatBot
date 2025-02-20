package com.codecrush.mentalhealthchatbot.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codecrush.mentalhealthchatbot.ApiData;
import com.codecrush.mentalhealthchatbot.R;
import com.codecrush.mentalhealthchatbot.adapter.RecyclerMessageAdapter;
import com.codecrush.mentalhealthchatbot.helper.MethodHelper;
import com.codecrush.mentalhealthchatbot.helper.RetrofitHelper;
import com.codecrush.mentalhealthchatbot.intrface.InternetCheckInterface;
import com.codecrush.mentalhealthchatbot.intrface.SuccessResponseCallback;
import com.codecrush.mentalhealthchatbot.model.BotMessageDataModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity
{
    ImageView IVBack, IVSend;
    EditText ETMSG;
    RecyclerView RVChatMessage;
    int isFirstSend=-1;
    boolean hasResponseBeenHandled=false;
    boolean hasResponseBeenHandledSecond=false;
    String conversationID;

    ArrayList<BotMessageDataModel.Message> arr_msg_data = new ArrayList<>();
    RecyclerMessageAdapter adapter;
    String message;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        RVChatMessage = findViewById(R.id.rv_chat_message);
        IVBack= findViewById(R.id.iv_back);
        IVSend = findViewById(R.id.iv_send);
        ETMSG = findViewById(R.id.et_msg);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        RVChatMessage.setLayoutManager(linearLayoutManager);

        Intent getFormChatFragment=getIntent();
        arr_msg_data.add(new BotMessageDataModel.Message(getFormChatFragment.getStringExtra("message"),getFormChatFragment.getStringExtra("time"),MethodHelper.getuserName(ChatActivity.this)));
        ETMSG.setText(getFormChatFragment.getStringExtra("message"));
        adapter = new RecyclerMessageAdapter(ChatActivity.this, arr_msg_data);
        RVChatMessage.setAdapter(adapter);
        sendFirstMessage();



        IVBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });


        IVSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                message=ETMSG.getText().toString();

                Date det=new Date();
                SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                /*if (isFirstSend==-1)
                {
                    isFirstSend=1;
                    sendFirstMessage();
                }
                else
                {*/
                    sendMessageAfterFirst();
                //}
                arr_msg_data.add(new BotMessageDataModel.Message(ETMSG.getText().toString(),time.format(det),MethodHelper.getuserName(ChatActivity.this)));

                ETMSG.setText("");
            }
        });


        adapter = new RecyclerMessageAdapter(ChatActivity.this, arr_msg_data);
        RVChatMessage.setAdapter(adapter);

    }

    private void sendFirstMessage()
    {
        MethodHelper.checkInternetConditionByTryAgain(ChatActivity.this, new InternetCheckInterface()
        {
            @Override
            public void onSuccessInternetCallback()
            {
                ApiData apiData= RetrofitHelper.instanceOfRetrofit(getResources().getString(R.string.urlchat));
                Call<BotMessageDataModel.Message> call=apiData.sendFirstMessage(MethodHelper.getuserName(ChatActivity.this),ETMSG.getText().toString(),MethodHelper.get_ID(ChatActivity.this),MethodHelper.getName(ChatActivity.this));
                call.enqueue(new Callback<BotMessageDataModel.Message>()
                {
                    @Override
                    public void onResponse(Call<BotMessageDataModel.Message> call, Response<BotMessageDataModel.Message> response)
                    {
                        if (!hasResponseBeenHandled)
                        {
                            hasResponseBeenHandled=true;

                            MethodHelper.getResponseConditionWithoutPT(response,ChatActivity.this,new SuccessResponseCallback<BotMessageDataModel.Message>(){
                                @Override
                                public void onSuccessfullResponse(BotMessageDataModel.Message Object)
                                {
                                    conversationID=Object.getConversationId();
                                    arr_msg_data.add(new BotMessageDataModel.Message(Object.getMessage(),Object.getCreatedAt(),Object.getSender()));
                                    adapter.notifyDataSetChanged();
                                    RVChatMessage.smoothScrollToPosition(adapter.getItemCount() - 1);
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<BotMessageDataModel.Message> call, Throwable t)
                    {
                        if (!hasResponseBeenHandled)
                        {
                            hasResponseBeenHandled=true;

                            MethodHelper.getFailureReason(t,ChatActivity.this);
                        }
                    }
                });
            }
        });



    }

    private void sendMessageAfterFirst()
    {
        MethodHelper.checkInternetConditionByTryAgain(ChatActivity.this, new InternetCheckInterface()
        {
            @Override
            public void onSuccessInternetCallback()
            {
                ApiData apiData= RetrofitHelper.instanceOfRetrofit(getResources().getString(R.string.urlchat));
                Call<BotMessageDataModel.Message> call=apiData.sendMessageAfterFirst(conversationID,message,MethodHelper.get_ID(ChatActivity.this));
                call.enqueue(new Callback<BotMessageDataModel.Message>()
                {
                    @Override
                    public void onResponse(Call<BotMessageDataModel.Message> call, Response<BotMessageDataModel.Message> response)
                    {
                        if (!hasResponseBeenHandledSecond)
                        {
                            hasResponseBeenHandledSecond=true;

                            MethodHelper.getResponseConditionWithoutPT(response,ChatActivity.this,new SuccessResponseCallback<BotMessageDataModel.Message>(){
                                @Override
                                public void onSuccessfullResponse(BotMessageDataModel.Message Object)
                                {
                                    arr_msg_data.add(new BotMessageDataModel.Message(Object.getMessage(),Object.getCreatedAt(),Object.getSender()));
                                    adapter.notifyDataSetChanged();
                                    RVChatMessage.smoothScrollToPosition(adapter.getItemCount() - 1);

                                    hasResponseBeenHandledSecond=false;
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<BotMessageDataModel.Message> call, Throwable t)
                    {
                        if (!hasResponseBeenHandledSecond)
                        {
                            hasResponseBeenHandledSecond=true;

                            MethodHelper.getFailureReason(t,ChatActivity.this);
                        }
                    }
                });
            }
        });
    }


}