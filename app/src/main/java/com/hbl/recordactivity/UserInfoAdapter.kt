package com.hbl.recordactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.hbl.recordactivity.databinding.ItemViewUserinfoBinding

class UserInfoAdapter(var userInfo: List<UserInfo>) :
    RecyclerView.Adapter<UserInfoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var context = parent.context
        var inflate =
            LayoutInflater.from(context).inflate(R.layout.item_view_userinfo, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mBinding!!.tvName.text = userInfo[position].username
    }

    override fun getItemCount(): Int {
        return userInfo.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mBinding = DataBindingUtil.bind<ItemViewUserinfoBinding>(view)
    }
}