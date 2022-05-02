package com.yeonberry.weatherapp.util

import android.content.Context

fun dpToPx(context: Context, dp: Int): Float {
    return dp * context.resources.displayMetrics.density
}