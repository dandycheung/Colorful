package io.multimoon.colorful

import android.app.Application
import android.content.Context
import android.util.Log

var mInstance: ColorfulDelegate? = null
val primaryThemeKey: String = "primary_theme"
val accentThemeKey: String = "accent_theme"
val darkThemeKey: String = "dark_theme"
val customThemeKey: String = "custom_theme"
val translucentKey: String = "translucent"

fun Colorful(): ColorfulDelegate {
    mInstance?.let { return it }
    throw Exception("Colorful has not been initialized! Please call initColorful(app:Application) in your application class before working with Colorful!")
}

fun initColorful(app: Application, defaults: Defaults = Defaults(ThemeColor.INDIGO, ThemeColor.RED, true, true)) {
    val time: Long = System.currentTimeMillis()
    val prefs = app.getSharedPreferences("io.multimoon.colorful.colorvals", Context.MODE_PRIVATE)

    var primary: ThemeColorInterface = ThemeColorInterface.parse(prefs.getString(primaryThemeKey, defaults.primaryColor.themeName))
    var accent: ThemeColorInterface = ThemeColorInterface.parse(prefs.getString(accentThemeKey, defaults.accentColor.themeName))

    mInstance = ColorfulDelegate(
            primary,
            accent,
            prefs.getBoolean(darkThemeKey, defaults.useDarkTheme),
            prefs.getBoolean(translucentKey, defaults.translucent),
            prefs.getInt(customThemeKey, defaults.customTheme))

    Log.d("COLORFUL", "Colorful init in " + (System.currentTimeMillis() - time) + " milliseconds!")
}

internal fun applyEdits(context: Context, primaryColor: ThemeColorInterface, accentColor: ThemeColorInterface, darkTheme: Boolean, translucent: Boolean, customTheme: Int = 0) {
    val prefs = context.getSharedPreferences("io.multimoon.colorful.colorvals", Context.MODE_PRIVATE)
    prefs.edit()
            .putBoolean(darkThemeKey, darkTheme)
            .putString(primaryThemeKey, primaryColor.themeName)
            .putString(accentThemeKey, accentColor.themeName)
            .putBoolean(translucentKey, translucent)
            .apply()
    if (customTheme != 0)
        prefs.edit().putInt(customThemeKey, customTheme).apply()
    else
        prefs.edit().remove(customThemeKey).apply()
    mInstance = ColorfulDelegate(primaryColor, accentColor, darkTheme, translucent)
}

internal fun resetPrefs(context: Context) {
    context.getSharedPreferences("io.multimoon.colorful.colorvals", Context.MODE_PRIVATE).edit().clear().apply()
}