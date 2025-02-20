/*
package com.codecrush.mentalhealthchatbot;

import android.content.Context;
import android.util.Log;

import com.codecrush.mentalhealthchatbot.ApiData;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper
{
    public static ApiData instanceOfRetrofit(String url)
    {

        */
/*HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("API Log", message);  // Use Log.d to print logs
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);*//*


        */
/*OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("AUTH", MethodsHelper.getjwt())
                                .header("VERSION",String.valueOf(MethodsHelper.getCurrentVersionCode()))
                                .method(original.method(), original.body())
                                .build();
                        *//*
*/
/*Log.d("Version", String.valueOf(MethodsHelper.getCurrentVersionCode()));*//*
*/
/*

                        return chain.proceed(request);
                    }
                })
                //.addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();*//*



        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                //.client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return  retrofit.create(ApiData.class);
    }

}
*/

package com.codecrush.mentalhealthchatbot.helper;

import android.util.Log;

import com.codecrush.mentalhealthchatbot.ApiData;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    public static ApiData instanceOfRetrofit(String url) {

        // Create a logging interceptor for Retrofit
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("API Log", message);  // Use Log.d to print logs
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);  // Set logging level to BODY

        // Create a custom header interceptor
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        };

        // Build OkHttpClient with both interceptors
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor)  // Add logging interceptor
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        // Build and return Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiData.class);
    }
}
