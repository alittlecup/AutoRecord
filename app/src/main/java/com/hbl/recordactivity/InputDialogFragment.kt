package com.hbl.recordactivity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.hbl.recordactivity.databinding.DialogInputUserinfoBinding


class InputDialogFragment : DialogFragment() {
    lateinit var mBinding: DialogInputUserinfoBinding
    val mainVM by activityViewModels<MainVM>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_input_userinfo, container, false)
        initWindow()
        return mBinding.root
    }

    private fun initWindow() {
        val win = dialog!!.window!!
        // 一定要设置Background，如果不设置，window属性设置无效
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val dm = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(dm)

        val params = win.attributes
        params.gravity = Gravity.CENTER
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.horizontalMargin = 30f
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        win.attributes = params
    }

    override fun onStart() {
        super.onStart()
        initWindow()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.save.setOnClickListener {
            saveUserInfo()
        }
    }

    private fun saveUserInfo() {
        var name = mBinding.nameEdit.text.toString().trim()
        var number = mBinding.numEdit.text.toString().trim()
        var password = mBinding.passwordEdit.text.toString().trim()

        var userInfo = UserInfo(name, number, password)
        mainVM.saveUserInfo(userInfo)
        dismiss()
    }
}