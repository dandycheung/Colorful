package io.multimoon.colorful

import android.content.Context
import android.graphics.Color
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat

class ColorfulColor(private val hexColor: String) {

    constructor(context: Context, @ColorRes colorRes: Int) : this("#${Integer.toHexString(ContextCompat.getColor(context, colorRes))}")

    private val int: Int by lazy {
        Color.parseColor(hexColor)
    }

    fun asInt() = int
    fun asHex() = hexColor
}