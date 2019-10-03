package mauz.toto.reminder

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import mauz.toto.reminder.MaintainTemplatesActivity.Companion.EXTRA_REMINDER


class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val TOKEN = "AlarmReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        buildNotification(context, intent)
    }

    private fun buildNotification(context: Context?, intent: Intent?) {
        Log.v(TOKEN, "build notification")

        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        var title = context.getString(R.string.app_name)
        if (intent != null) {
            title = intent.getStringExtra(EXTRA_REMINDER)
        }

        val mBuilder = NotificationCompat.Builder(context, "intent group")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(context.getString(R.string.msgNotificationText))
            .setSound(soundUri)

        notificationManager.notify(0, mBuilder.build())
    }
}

