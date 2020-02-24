package com.example.testapplication.utils

import android.content.res.Resources

object Utils {

    fun dpToPx(dp: Int): Int {
        return (Resources.getSystem().displayMetrics.density * dp).toInt()
    }
}