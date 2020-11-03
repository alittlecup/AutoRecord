package com.hbl.recordactivity

import android.util.Log
import androidx.room.Room
import com.hbl.recordactivity.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import org.json.JSONObject

object UserService {
    val clockInService: ClockInService = RemoteRepository.createService(ClockInService::class.java)
    val database = Room
        .databaseBuilder(MyApplication.instance, AppDatabase::class.java, "picplus_user.db")
        .fallbackToDestructiveMigration()
        .build()
    val userDao = database.userInfoDao()


    fun isLogin(userName: String): Boolean {
        var userInfo = userDao.findByName(userName)
        return userInfo.isLogin()
    }

    suspend fun login(userInfo: UserInfo) {
        var jsonObject = JSONObject(
            mapOf(
                "username" to userInfo.username,
                "passward" to userInfo.passward
            )
        )
        withContext(Dispatchers.IO) {
            clockInService.loginUser(jsonObject)
                .catch {
                    Log.d("UserService", "login: error")
                }.collect {
                    Log.d("UserService", "login: $it")
                }

        }
    }

    suspend fun saveUserInfo(userInfo: UserInfo) {
        return withContext(Dispatchers.IO) {
            userDao.insertUserINfo(userInfo)

        }
    }

    suspend fun getAllUserInfo(): List<UserInfo> {
        return withContext(Dispatchers.IO) {
            userDao.getAll()
        }
    }
}