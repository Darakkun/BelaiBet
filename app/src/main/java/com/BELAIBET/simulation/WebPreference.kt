package com.BELAIBET.simulation

import android.content.Context
import android.content.SharedPreferences

object WebPreference {
    val kuki = "COOKI"

    fun getPref(context: Context): SharedPreferences = context.getSharedPreferences("bykmeker", Context.MODE_PRIVATE)

    private inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.cokies
        get() = getString(kuki, "xyko")
        set(value) {
            editMe {
                it.putString(kuki, value)
            }
        }

    var SharedPreferences.url
        get() = getString("URL", "")
        set(value) {
            editMe {
                it.putString("URL", value)
            }
        }

}