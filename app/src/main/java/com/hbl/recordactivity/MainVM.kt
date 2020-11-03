package com.hbl.recordactivity

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MainVM : ViewModel() {
    val userInfos: MediatorLiveData<List<UserInfo>> = MediatorLiveData()

    init {
        viewModelScope.launch {
            var allUserInfo = UserService.getAllUserInfo()
            userInfos.addSource(allUserInfo, Observer { userInfos.value = it })
        }
    }

    fun saveUserInfo(userInfo: UserInfo) {
        viewModelScope.launch {
            UserService.saveUserInfo(userInfo)
        }
    }

    fun userLogin(userInfo: UserInfo) {
        viewModelScope.launch {
            UserService.login(userInfo)
        }
    }

}