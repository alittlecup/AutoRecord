package com.hbl.recordactivity

import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface ClockInService {

    @GET("ncov/wap/default/index")
    suspend fun loadCurrentUserInfo(): Response<String>

    @POST("ncov/wap/default/save")
    suspend fun clockInRemote(@Body jsonObject: JSONObject): Response<String>

    @POST("uc/wap/login/check")
    @FormUrlEncoded
    suspend fun loginUser(
        @FieldMap map: Map<String, String>,
        @Header("Cookie") cookie: String
    ): Response<String>

    @GET("uc/wap/login/check")
    suspend fun loadLoginCookie(): Response<String>
}