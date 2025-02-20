package com.codecrush.mentalhealthchatbot.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codecrush.mentalhealthchatbot.ApiData;
import com.codecrush.mentalhealthchatbot.R;
import com.codecrush.mentalhealthchatbot.activity.ChatActivity;
import com.codecrush.mentalhealthchatbot.adapter.RecyclerMessageAdapter;
import com.codecrush.mentalhealthchatbot.helper.MethodHelper;
import com.codecrush.mentalhealthchatbot.helper.RetrofitHelper;
import com.codecrush.mentalhealthchatbot.intrface.InternetCheckInterface;
import com.codecrush.mentalhealthchatbot.intrface.SuccessResponseCallback;
import com.codecrush.mentalhealthchatbot.model.BotMessageDataModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatFragment extends Fragment
{
    View view;
    RecyclerView RVChatMessage;
    EditText ETMSG;
    ImageView IVSend;
    ProgressBar PBRVChatMessage;
    TextView TVDataNotFoundRVChatMessage;
    ArrayList<BotMessageDataModel.Message> arr_msg_data = new ArrayList<>();
    RecyclerMessageAdapter adapter;
    int isFirstSend=-1;
    boolean hasResponseBeenHandled=false;
    boolean hasResponseBeenHandledSecond=false;
    String conversationID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (view!=null)
        {
            return view;
        }

        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_chat, container, false);

        ETMSG = view.findViewById(R.id.et_msg);
        IVSend = view.findViewById(R.id.iv_send);
        RVChatMessage = view.findViewById(R.id.rv_chat_message);
        PBRVChatMessage=view.findViewById(R.id.pb_rv_chat_message);
        TVDataNotFoundRVChatMessage=view.findViewById(R.id.tv_data_not_found_rv_chat_message);


//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        RVChatMessage.setLayoutManager(linearLayoutManager);

        IVSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*if (isFirstSend==-1)
                {
                    isFirstSend=1;
                    sendFirstMessage();
                }
                else
                {
                    sendMessageAfterFirst();
                }*/

                Date det=new Date();
                SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                Intent chat=new Intent(getActivity(), ChatActivity.class);
                chat.putExtra("message",ETMSG.getText().toString());
                chat.putExtra("time",time.format(det));
                getContext().startActivity(chat);

                ETMSG.setText("");
            }
        });

        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RVChatMessage.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerMessageAdapter(getContext(), arr_msg_data);
        RVChatMessage.setAdapter(adapter);*/

        return view;
    }

    /*private void sendFirstMessage()
    {
        MethodHelper.checkInternetConditionByTryAgain(getContext(), new InternetCheckInterface()
        {
            @Override
            public void onSuccessInternetCallback()
            {
                ApiData apiData= RetrofitHelper.instanceOfRetrofit(getResources().getString(R.string.urlchat));
                Call<BotMessageDataModel.Message> call=apiData.sendFirstMessage(MethodHelper.getuserName(getContext()),ETMSG.getText().toString(),MethodHelper.get_ID(getContext()),MethodHelper.getName(getContext()));
                call.enqueue(new Callback<BotMessageDataModel.Message>()
                {
                    @Override
                    public void onResponse(Call<BotMessageDataModel.Message> call, Response<BotMessageDataModel.Message> response)
                    {
                        if (!hasResponseBeenHandled)
                        {
                            hasResponseBeenHandled=true;

                            MethodHelper.getResponseConditionWithoutPT(response,getContext(),new SuccessResponseCallback<BotMessageDataModel.Message>(){
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

                            MethodHelper.getFailureReason(t,getContext());
                        }
                    }
                });
            }
        });



    }*/

    /*private void sendMessageAfterFirst()
    {
        MethodHelper.checkInternetConditionByTryAgain(getContext(), new InternetCheckInterface()
        {
            @Override
            public void onSuccessInternetCallback()
            {
                ApiData apiData= RetrofitHelper.instanceOfRetrofit(getResources().getString(R.string.urlchat));
                Call<BotMessageDataModel.Message> call=apiData.sendMessageAfterFirst(conversationID,ETMSG.getText().toString(),MethodHelper.get_ID(getContext()));
                call.enqueue(new Callback<BotMessageDataModel.Message>()
                {
                    @Override
                    public void onResponse(Call<BotMessageDataModel.Message> call, Response<BotMessageDataModel.Message> response)
                    {
                        if (!hasResponseBeenHandledSecond)
                        {
                            hasResponseBeenHandledSecond=true;

                            MethodHelper.getResponseConditionWithoutPT(response,getContext(),new SuccessResponseCallback<BotMessageDataModel.Message>(){
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

                            MethodHelper.getFailureReason(t,getContext());
                        }
                    }
                });
            }
        });
    }*/


}