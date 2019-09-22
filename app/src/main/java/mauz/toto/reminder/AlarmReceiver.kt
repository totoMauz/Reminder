package mauz.toto.reminder

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat


class AlarmReceiver : BroadcastReceiver {
    companion object {
        const val TOKEN = "AlarmReceiver"
    }

    constructor() : super()

    override fun onReceive(p0: Context?, p1: Intent?) {
        buildNotification(p0, p1)
    }

    private fun buildNotification(p0: Context?, p1: Intent?) {
        Log.v(TOKEN, "build notification")

        val notificationManager =
            p0?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val mBuilder = NotificationCompat.Builder(p0, "intent group")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("intent group")
            .setContentText("Some message if defined")
            .setSound(soundUri)

        notificationManager.notify(0, mBuilder.build())
    }


}

