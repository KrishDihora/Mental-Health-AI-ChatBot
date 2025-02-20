package com.codecrush.mentalhealthchatbot.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codecrush.mentalhealthchatbot.ApiData;
import com.codecrush.mentalhealthchatbot.R;
import com.codecrush.mentalhealthchatbot.activity.ChatActivity;
import com.codecrush.mentalhealthchatbot.helper.MethodHelper;
import com.codecrush.mentalhealthchatbot.helper.RetrofitHelper;
import com.codecrush.mentalhealthchatbot.intrface.InternetCheckInterface;
import com.codecrush.mentalhealthchatbot.intrface.SuccessResponseCallback;
import com.codecrush.mentalhealthchatbot.model.BotMessageDataModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.easebuzz.payment.kit.PWECouponsActivity;
import datamodels.PWEStaticDataModel;

public class ConsultDoctorFragment extends Fragment
{
    View view;
    RecyclerView RVDoctorList;

    TextView TVBasicConsultNow,TVStandardConsultNow,TVPremiumConsultNow;
    boolean hasResponse=false;
    String accessKey,mode;
    private ActivityResultLauncher<Intent> pweActivityResultLauncher;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (view!=null)
        {
            return view;
        }

        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_consult_doctor, container, false);



        TVBasicConsultNow=view.findViewById(R.id.tv_basic_consult_now);
        TVStandardConsultNow=view.findViewById(R.id.tv_standard_consult_now);
        TVPremiumConsultNow=view.findViewById(R.id.tv_premium_consult_now);

        TVBasicConsultNow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getAccessKey(String.valueOf(99));
            }
        });

        TVStandardConsultNow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getAccessKey(String.valueOf(199));
            }
        });

        TVPremiumConsultNow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getAccessKey(String.valueOf(299));
            }
        });

        return view;
    }

    private void getAccessKey(String Amount)
    {
        MethodHelper.checkInternetConditionByTryAgain(getContext(), new InternetCheckInterface()
        {
            @Override
            public void onSuccessInternetCallback()
            {
                ApiData apiData= RetrofitHelper.instanceOfRetrofit(getResources().getString(R.string.urlpayment));
                Call<JsonObject> call=apiData.getAccessKey("8780111310",MethodHelper.getuserName(getContext()),"doctor","product",Amount,"1","1");

                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
                    {
                        if (!hasResponse)
                        {
                            hasResponse=true;
                            MethodHelper.getResponseConditionWithoutPT(response,getContext(),new SuccessResponseCallback<JsonObject>(){
                                @Override
                                public void onSuccessfullResponse(JsonObject Object)
                                {
                                    accessKey=Object.get("key").toString();
                                    accessKey=accessKey.substring(1,accessKey.length()-1);

                                    mode=Object.get("mode").getAsString();

                                    Intent intentProceed = new Intent(getContext(), PWECouponsActivity.class);
                                    intentProceed.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // This is mandatory flag
                                    intentProceed.putExtra("access_key",accessKey);
                                    intentProceed.putExtra("pay_mode",mode);
                                    pweActivityResultLauncher.launch(intentProceed);
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t)
                    {
                        if (!hasResponse)
                        {
                            hasResponse=true;

                            MethodHelper.getFailureReason(t,getContext());
                        }
                    }
                });
            }
        });

        pweActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent data = result.getData();
                if (data != null) {
                    String payment_result = data.getStringExtra("result");
                    String payment_response = data.getStringExtra("payment_response");
                    try {
                        // Handle response here
                    }catch (Exception e){
                        // Handle exception here
                    }
                }
            }
        });
    }
}