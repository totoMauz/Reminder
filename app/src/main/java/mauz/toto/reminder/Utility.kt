package mauz.toto.reminder

import android.content.Context
import android.util.Log
import android.widget.Toast

const val HOUR_TO_MILLIS = 3600000
const val HOUR_TO_MINUTE = 60
const val MINUTE_TO_MILLIS = 60000
const val SECONDS_TO_MILLIS = 1000

fun err(token: String, applicationContext: Context, msg: String) {
    makeToast(applicationContext, msg)

    Log.e(token, msg)
}

fun err(token: String, applicationContext: Context, msg: String, error: Exception) {
    makeToast(applicationContext, msg)

    Log.e(token, msg + " " + error.printStackTrace())
}

fun dbg(token: String, applicationContext: Context, msg: String) {
    makeToast(applicationContext, msg)

    Log.d(token, msg)
}

fun makeToast(applicationContext: Context, msg: String) {
    Toast.makeText(
        applicationContext,
        msg,
        Toast.LENGTH_SHORT
    ).show()
}

fun formatTime(hours: Number, minutes: Number, seconds: Number = -1): String {
    if (seconds == -1) {
        return "${padNumber(hours)}:${padNumber(minutes)}"
    }
    return "${padNumber(hours)}:${padNumber(minutes)}:${padNumber(seconds)}"
}

private fun padNumber(num: Number): String {
    return num.toString().padStart(2, '0')
}