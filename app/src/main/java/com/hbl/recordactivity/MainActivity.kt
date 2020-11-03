package com.hbl.recordactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hbl.recordactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityMainBinding
    val mainVM by viewModels<MainVM>()
    val adapter = UserInfoAdapter(listOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.fab.setOnClickListener {
            addUserInfo()
        }
        mainVM.userInfos.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                mBinding.tvEmpty.visibility = View.VISIBLE
            } else {
                mBinding.tvEmpty.visibility = View.INVISIBLE
                adapter.userInfo = it
                adapter.notifyDataSetChanged()
            }
        })
        adapter.onClickListener = object : UserInfoAdapter.OnClickListener {
            override fun onLoginClick(position: Int) {
                var userInfo = mainVM.userInfos.value?.get(position)
                if (userInfo != null) {
                    if (!userInfo.isLogin()) {
                        mainVM.userLogin(userInfo)
                    }
                }
            }

            override fun onClockClick(position: Int) {

            }

            override fun onItemClick(position: Int) {

            }

        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mBinding.recyclerview.adapter = adapter
        mBinding.recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun addUserInfo() {
        var inputDialogFragment = InputDialogFragment()
        inputDialogFragment.show(supportFragmentManager, "userinfo")
    }
}