package com.hbl.recordactivity

import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ClockInService {

    @GET("ncov/wap/default/index")
    fun loadCurrentUserInfo(): Flow<String>

    @POST("ncov/wap/default/save")
    fun clockInRemote(@Body jsonObject: JSONObject): Flow<String>

    @POST("uc/wap/login/check")
    fun loginUser(@Body jsonObject: JSONObject): Flow<String>
}