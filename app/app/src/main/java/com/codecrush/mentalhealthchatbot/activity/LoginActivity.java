package com.codecrush.mentalhealthchatbot.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.codecrush.mentalhealthchatbot.ApiData;
import com.codecrush.mentalhealthchatbot.intrface.InternetCheckInterface;
import com.codecrush.mentalhealthchatbot.helper.MethodHelper;
import com.codecrush.mentalhealthchatbot.R;
import com.codecrush.mentalhealthchatbot.helper.RetrofitHelper;
import com.codecrush.mentalhealthchatbot.intrface.SuccessResponseCallback;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity
{
    EditText ETUsername,ETPassword;
    CardView CVSginIn;
    TextView TVSiginUp,TVForget,TVSignIn;
    String userName,password;

    ProgressBar PBLogin;

    boolean hasResponseBeenHandled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Define ID
        ETUsername=findViewById(R.id.et_username);
        ETPassword=findViewById(R.id.et_password);
        CVSginIn=findViewById(R.id.cv_signin);
        TVSiginUp=findViewById(R.id.tv_signup);
        TVForget=findViewById(R.id.tv_forgot);
        TVSignIn=findViewById(R.id.tv_signin);
        PBLogin=findViewById(R.id.pb_login);


        SharedPreferences userprefrence = getSharedPreferences("user", MODE_PRIVATE);

        if (userprefrence.contains("_id"))
        {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }


        TVSiginUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent signUP=new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUP);
            }
        });



        CVSginIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                userName=ETUsername.getText().toString();
                password=ETPassword.getText().toString();
                forLogin();
            }
        });

    }

    private void forLogin()
    {
        MethodHelper.checkInternetConditionByOpenAgain(LoginActivity.this, new InternetCheckInterface() {
            @Override
            public void onSuccessInternetCallback()
            {
                ApiData apiData= RetrofitHelper.instanceOfRetrofit(getResources().getString(R.string.urlauth));

                //Call<JsonObject> call=apiData.forLogin(userName,password);

                String formData ="&email=" + userName +
                        "&password=" + password/*+
                        "&notificationToken=" + MethodHelper.getNotificationToken()*/;

                Call<JsonObject> call = apiData.forLogin(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), formData));

                if (TVSignIn.getVisibility() == View.VISIBLE && PBLogin.getVisibility() == View.GONE)
                {
                    TVSignIn.setVisibility(View.GONE);
                    PBLogin.setVisibility(View.VISIBLE);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
                        {
                            MethodHelper.getResponseCondition(response,LoginActivity.this,PBLogin,TVSignIn,hasResponseBeenHandled,new SuccessResponseCallback<JsonObject>()
                            {
                                @Override
                                public void onSuccessfullResponse(JsonObject Object)
                                {
                                    if (!hasResponseBeenHandled)
                                    {
                                        hasResponseBeenHandled=true;

                                        JsonObject jsonObject=Object.get("user").getAsJsonObject();

                                        SharedPreferences userprefrence = getSharedPreferences("user", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = userprefrence.edit();

                                        editor.putString("username",userName);
                                        editor.putString("password",password);
                                        editor.putString("_id",jsonObject.get("_id").getAsString());
                                        editor.putString("accessToken",Object.get("accessToken").getAsString());
                                        editor.putString("refreshToken",Object.get("refreshToken").getAsString());

                                        editor.commit();

                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();

                                        //Toast.makeText(LoginActivity.this,"Login Successfully", Toast.LENGTH_LONG).show();

                                    }

                                }

                                @Override
                                public void onFailureResponse(JsonObject Object)
                                {
                                    Toast.makeText(LoginActivity.this,"Login Unsuccessfully", Toast.LENGTH_LONG).show();

                                    TVSignIn.setVisibility(View.VISIBLE);
                                    PBLogin.setVisibility(View.GONE);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t)
                        {
                            if (!hasResponseBeenHandled)
                            {
                                hasResponseBeenHandled=true;


                                MethodHelper.getFailureReason(t,LoginActivity.this);

                                TVSignIn.setVisibility(View.VISIBLE);
                                PBLogin.setVisibility(View.GONE);

                            }
                        }
                    });
                }

            }
        });
    }
}
