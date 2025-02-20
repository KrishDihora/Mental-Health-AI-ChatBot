package com.codecrush.mentalhealthchatbot;

import com.codecrush.mentalhealthchatbot.model.BotMessageDataModel;
import com.google.gson.JsonObject;
import com.squareup.okhttp.ResponseBody;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiData
{
    /*@FormUrlEncoded
    @POST("signup")
    Call<JsonObject> forSignUp(
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("email") String email,
            @Field("password") String password
    );*/


    @POST("signup")
    Call<JsonObject> forSignUp(@Body RequestBody body);

    /*@FormUrlEncoded
    @POST("login")
    Call<JsonObject> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );*/

    @POST("login")
    Call<JsonObject> forLogin(@Body RequestBody body);

    @FormUrlEncoded
    @POST("new-conversation")
    Call<BotMessageDataModel.Message> sendFirstMessage(@Field("userType") String Email,
                                                       @Field("message") String Message,
                                                       @Field("userId") String _ID,
                                                       @Field("name") String Name
    );

    @FormUrlEncoded
    @POST("start-chat")
    Call<BotMessageDataModel.Message> sendMessageAfterFirst(@Field("conversationId") String ConversationID,
                                                            @Field("message") String Message,
                                                            @Field("userId") String _ID
    );

    @FormUrlEncoded
    @POST("get-conversation")
    Call<JsonObject> getPastChatTitle(@Field("userId") String _ID);


    @FormUrlEncoded
    @POST("initpaytmp.php")
    Call<JsonObject> getAccessKey(@Field("number") String number,
                                  @Field("email") String email,
                                  @Field("firstname") String name,
                                  @Field("productinfo") String productName,
                                  @Field("amount") String amount,
                                  @Field("purchase_id") String purchaseId,
                                  @Field("purchase_type") String purchaseType
    );






}
