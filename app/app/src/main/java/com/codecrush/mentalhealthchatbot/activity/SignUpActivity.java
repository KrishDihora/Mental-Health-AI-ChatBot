package com.codecrush.mentalhealthchatbot.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class SignUpActivity extends AppCompatActivity
{
    EditText ETName,ETUserName,ETPassword;
    CardView CVSignUp;
    TextView TVSignIn,TVSignUp;
    String name,userName,password;
    ProgressBar PBSignUp;
    boolean hasResponseBeenHandled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Define ID
        ETName=findViewById(R.id.et_name);
        ETUserName=findViewById(R.id.et_username);
        ETPassword=findViewById(R.id.et_password);
        CVSignUp=findViewById(R.id.cv_signup);
        TVSignIn=findViewById(R.id.tv_signin);
        PBSignUp=findViewById(R.id.pb_signup);
        TVSignUp=findViewById(R.id.tv_signup);

        SharedPreferences userprefrence = getSharedPreferences("user", MODE_PRIVATE);

        if (userprefrence.contains("_id"))
        {
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            finish();
        }

        TVSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent login=new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });



        CVSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                name=ETName.getText().toString();
                userName=ETUserName.getText().toString();
                password=ETPassword.getText().toString();

                forSignUp();
            }
        });


    }

    private void forSignUp() {
        MethodHelper.checkInternetConditionByOpenAgain(SignUpActivity.this, new InternetCheckInterface() {
            @Override
            public void onSuccessInternetCallback() {
                ApiData apiData = RetrofitHelper.instanceOfRetrofit(getResources().getString(R.string.urlauth));

                String formData = "firstName=" + name +
                        "&lastName=" + "hhhh" +
                        "&email=" + userName +
                        "&password=" + password +
                        "&notificationToken=" + MethodHelper.getNotificationToken();

                Log.d("TAGToken", String.valueOf(MethodHelper.getNotificationToken()));

                /*RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), formData);*/

                /*Call<JsonObject> call = apiData.forSignUp(
                        ETName.getText().toString(),
                        "hhhh", // assuming you want to keep this as a placeholder for last name
                        ETUserName.getText().toString(),
                        ETPassword.getText().toString()
                );*/

                Call<JsonObject> call = apiData.forSignUp(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), formData));

                /*Log.d("TAG", ETName.getText().toString());
                Log.d("TAG", ETUserName.getText().toString());
                Log.d("TAG", ETPassword.getText().toString());*/

                if (TVSignUp.getVisibility() == View.VISIBLE && PBSignUp.getVisibility() == View.GONE)
                {
                    TVSignUp.setVisibility(View.GONE);
                    PBSignUp.setVisibility(View.VISIBLE);

                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            MethodHelper.getResponseCondition(response, SignUpActivity.this, PBSignUp, TVSignUp, hasResponseBeenHandled, new SuccessResponseCallback<JsonObject>() {
                                @Override
                                public void onSuccessfullResponse(JsonObject Object) {
                                    if (!hasResponseBeenHandled) {
                                        hasResponseBeenHandled = true;

                                        SharedPreferences userPreference = getSharedPreferences("user", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = userPreference.edit();

                                        editor.putString("name", name);
                                        editor.putString("username", userName);
                                        editor.putString("password", password);
                                        editor.commit();

                                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                        finish();
                                        //Toast.makeText(SignUpActivity.this, "SignUp Successfully", Toast.LENGTH_LONG).show();

                                    }
                                }

                                @Override
                                public void onFailureResponse(JsonObject Object)
                                {
                                    Toast.makeText(SignUpActivity.this, "SignUp Unsuccessfully", Toast.LENGTH_LONG).show();

                                    TVSignUp.setVisibility(View.VISIBLE);
                                    PBSignUp.setVisibility(View.GONE);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            if (!hasResponseBeenHandled)
                            {
                                hasResponseBeenHandled = true;

                                MethodHelper.getFailureReason(t, SignUpActivity.this);

                                TVSignUp.setVisibility(View.VISIBLE);
                                PBSignUp.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
    }

}