package mauz.toto.reminder

import android.content.Context
import android.util.Log
import android.widget.Toast

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

private fun makeToast(applicationContext: Context, msg: String) {
    Toast.makeText(
        applicationContext,
        msg,
        Toast.LENGTH_SHORT
    ).show()
}