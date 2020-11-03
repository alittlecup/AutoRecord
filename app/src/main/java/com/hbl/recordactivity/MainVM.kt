package com.hbl.recordactivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainVM : ViewModel() {
    public var userInfos = MutableLiveData<List<UserInfo>>()

    init {
        viewModelScope.launch {
            userInfos.value = UserService.getAllUserInfo()
        }
    }

    fun saveUserInfo(userInfo: UserInfo) {
        viewModelScope.launch {
            UserService.saveUserInfo(userInfo)
            var userInfo = UserService.getAllUserInfo()
            userInfos.value = userInfo

        }
    }

}