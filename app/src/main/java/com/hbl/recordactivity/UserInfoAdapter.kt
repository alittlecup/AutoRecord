package com.hbl.recordactivity

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hbl.recordactivity.databinding.ItemViewUserinfoBinding

class UserInfoAdapter(var userInfo: List<UserInfo>) :
    RecyclerView.Adapter<UserInfoAdapter.ViewHolder>() {

    var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var context = parent.context
        var inflate =
            LayoutInflater.from(context).inflate(R.layout.item_view_userinfo, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var user = userInfo[position]
        var mBinding = holder.mBinding!!
        mBinding.tvName.text = user.username
        mBinding.tvSchoolNum.text = user.schoolNum
        mBinding.container.setBackgroundResource(
            if (position % 2 == 0) R.color.lightGrey else R.color.darkGrey
        )
        mBinding.container.setOnClickListener {
            onClickListener?.onClockClick(position)
        }

        mBinding.imgLogin.setOnClickListener {
            onClickListener?.onLoginClick(position)
        }

        mBinding.imgClock.setOnClickListener {
            onClickListener?.onClockClick(position)
        }

        mBinding.container.setOnClickListener {
            onClickListener?.onItemClick(position)
        }
        mBinding.imgLogin.setImageResource(if (user.isLogin()) R.drawable.ic_login_tint else R.drawable.ic_login)
        mBinding.imgClock.setImageResource(if (user.todayClock()) R.drawable.ic_clock_tint else R.drawable.ic_clock)

    }

    override fun getItemCount(): Int {
        return userInfo.size
    }


    interface OnClickListener {
        fun onLoginClick(position: Int)
        fun onClockClick(position: Int)
        fun onItemClick(position: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mBinding = DataBindingUtil.bind<ItemViewUserinfoBinding>(view)
    }
}