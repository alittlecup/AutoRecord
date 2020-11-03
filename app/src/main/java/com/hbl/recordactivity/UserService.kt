package com.hbl.recordactivity

import androidx.lifecycle.LiveData
import androidx.room.Room
import com.hbl.recordactivity.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object UserService {
    private val clockInService: ClockInService =
        RemoteRepository.createService(ClockInService::class.java)
    private val database = Room
        .databaseBuilder(MyApplication.instance, AppDatabase::class.java, "picplus_user.db")
        .fallbackToDestructiveMigration()
        .build()
    private val userDao = database.userInfoDao()


    suspend fun login(userInfo: UserInfo) {
        withContext(Dispatchers.IO) {
            var availableCookie = loadAvailableCookie()

            var eaisess = availableCookie.first.split(";")[0]
            var response = clockInService.loginUser(
                mapOf(
                    "username" to userInfo.schoolNum,
                    "password" to userInfo.password
                ), eaisess
            )
            if (response.isSuccessful) {
                userInfo.eaiSess = availableCookie.first.split(";")[0].split("=")[1]
                userInfo.eaiSessTime = availableCookie.first.split(";")[1].split("=")[1]
                userInfo.uukey = availableCookie.second.split(";")[0].split("=")[1]
                userInfo.uukeyTime = availableCookie.second.split(";")[1].split("=")[1]
                userDao.insertUserInfo(userInfo)
            }
        }


    }

    suspend fun saveUserInfo(userInfo: UserInfo) {
        return withContext(Dispatchers.IO) {
            userDao.insertUserInfo(userInfo)
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