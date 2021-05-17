package com.tp3.crowdingestg.api


import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface EndPoints {
    @GET("/myslim/api/scoreboard")
    fun getscoreaboard(): Call<List<scoreboard>>

    @FormUrlEncoded
    @POST("/myslim/api/user")
    fun postLogin(
            @Field("name") name: String?,
            @Field("password") password: String?): retrofit2.Call<OutputPost>


    @FormUrlEncoded
    @POST("/myslim/api/register")
    fun postRegister(
            @Field("name") name: String?,
            @Field("password") password: String?
    ): Call<OutputPost>
}