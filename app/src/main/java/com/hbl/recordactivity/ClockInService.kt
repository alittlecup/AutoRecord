package com.hbl.recordactivity

import retrofit2.Response
import retrofit2.http.*

interface ClockInService {

    @GET("ncov/wap/default/index")
    suspend fun loadCurrentUserInfo(@Header("Cookie") cookie: String): Response<String>

    @POST("ncov/wap/default/save")
    @FormUrlEncoded
    suspend fun clockInRemote(
        @FieldMap map: Map<String, @JvmSuppressWildcards Any>,
        @Header("Cookie") cookie: String
    ): Response<String>

    @POST("uc/wap/login/check")
    @FormUrlEncoded
    suspend fun loginUser(
        @FieldMap map: Map<String, String>,
        @Header("Cookie") cookie: String
    ): Response<String>

    @GET("uc/wap/login/check")
    suspend fun loadLoginCookie(): Response<String>
}