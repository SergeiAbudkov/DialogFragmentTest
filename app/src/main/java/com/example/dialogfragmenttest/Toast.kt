package com.example.dialogfragmenttest

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


fun Fragment.showToast(@StringRes mesRes: Int) {
    showToastFun(requireContext(), mesRes)
}

fun AppCompatActivity.showToast(@StringRes mesRes: Int) {
    showToastFun(this, mesRes)
}


fun showToastFun(context:Context, @StringRes mesRes: Int) {
    Toast.makeText(context, mesRes, Toast.LENGTH_SHORT).show()
}