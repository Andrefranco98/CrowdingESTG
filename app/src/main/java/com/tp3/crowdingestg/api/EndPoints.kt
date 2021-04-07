package com.tp3.crowdingestg.api


import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface EndPoints {
    @FormUrlEncoded
    @POST("/myslim/api/user")
    fun postLogin(
        @Field("name") name: String?,
        @Field("password") password: String?): retrofit2.Call<OutputPost>
}