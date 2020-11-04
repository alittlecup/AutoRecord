package com.hbl.recordactivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.hbl.recordactivity.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.commons.text.StringEscapeUtils
import java.time.LocalDate

object UserService {
    private val clockInService: ClockInService =
        RemoteRepository.createService(ClockInService::class.java)
    private val database = Room
        .databaseBuilder(MyApplication.instance, AppDatabase::class.java, "picplus_user.db")
        .fallbackToDestructiveMigration()
        .build()
    private val userDao = database.userInfoDao()


    suspend fun login(userInfo: UserInfo): Boolean {
        return withContext(Dispatchers.IO) {
            var availableCookie = loadAvailableCookie()
            var eaisess = availableCookie.first.split(";")[0]
            var response = clockInService.loginUser(
                mapOf(
                    "username" to userInfo.schoolNum,
                    "password" to userInfo.password
                ), eaisess
            )
            var result = if (response.isSuccessful) {
                userInfo.eaiSess = availableCookie.first.split(";")[0].split("=")[1]
                userInfo.eaiSessTime = availableCookie.first.split(";")[1].split("=")[1]
                userInfo.uukey = availableCookie.second.split(";")[0].split("=")[1]
                userInfo.uukeyTime = availableCookie.second.split(";")[1].split("=")[1]
                userDao.insertUserInfo(userInfo)
                true
            } else {
                false
            }
            result
        }
    }


    suspend fun saveUserInfo(userInfo: UserInfo) {
        return withContext(Dispatchers.IO) {
            userDao.insertUserInfo(userInfo)
        }
    }

    suspend fun postClockRemote(userInfo: UserInfo) {
        var loadUserRemoteInfo = loadUserRemoteInfo(userInfo)
        if (loadUserRemoteInfo != null) {

            val mapType = object : TypeToken<Map<String, Any>>() {}.type
            var clockInRemote = clockInService.clockInRemote(
                map = Gson().fromJson(loadUserRemoteInfo, mapType),
                cookie = userInfo.getCookie()
            )
            if (StringUtils.toJsonObject(clockInRemote.body()!!).get("e").asInt == 0) {
                userInfo.clockDay = LocalDate.now().toString()
                userDao.insertUserInfo(userInfo)
            }
        }
    }

    suspend fun loadUserRemoteInfo(userInfo: UserInfo): JsonObject? {
        return withContext(Dispatchers.IO) {
            var userInfo = userDao.findByName(userInfo.username)
            var response = clockInService.loadCurrentUserInfo(userInfo.getCookie())
            var body = StringEscapeUtils.unescapeJava(response.body())
            if (!body.isNullOrEmpty()) {
                if (!StringUtils.hasFlag(body)) {
                    return@withContext StringUtils.generatePostJson(body)
                } else {
                    userInfo.clockDay = LocalDate.now().toString()
                    userDao.insertUserInfo(userInfo)
                }
            }
            null
        }

    }


    suspend fun loadAvailableCookie(): Pair<String, String> {
        var loginPageResponse = clockInService.loadLoginCookie()
        var values = loginPageResponse.headers().values("Set-Cookie")
        return Pair(values[0], values[1])
    }

    suspend fun getAllUserInfo(): LiveData<List<UserInfo>> {
        return withContext(Dispatchers.IO) {
            userDao.getAll()
        }
    }

}

