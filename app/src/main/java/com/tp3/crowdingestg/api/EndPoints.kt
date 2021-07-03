package com.tp3.crowdingestg.api


import retrofit2.Call
import retrofit2.http.*

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

    @GET("myslim/api/user_location_reward/{id}")
    fun rewarduser_location(
            @Path("id") id: Int
    ): Call<OutputPost>
}