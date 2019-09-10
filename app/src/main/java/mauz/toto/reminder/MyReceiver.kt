package mauz.toto.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast


class MyReceiver : BroadcastReceiver {
    constructor() : super()

    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.v("MyReceiver", "start onReceive")
        Toast.makeText(p0, "Alarm", Toast.LENGTH_SHORT).show()
        Log.v("MyReceiver", "end onReceive")
    }

}

