package mauz.toto.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    companion object {
        const val TOKEN = "AlarmReceiver"
    }

    private var alarmReceiver: AlarmReceiver? = null

    fun triggerAlarm(_view: View) {
        Log.v(TOKEN, "start triggerAlarm")
        val formatter = SimpleDateFormat("HH:mm:ss")

        val calendar: Calendar = Calendar.getInstance()
        textView.text = formatter.format(calendar.timeInMillis)

        val intent = Intent(this.applicationContext, AlarmReceiver::class.java)
        val alarmIntent =
            PendingIntent.getBroadcast(
                this.applicationContext,
                192837,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager
        calendar.add(Calendar.SECOND, 5)
        alarmManager.set(AlarmManager.RTC, calendar.timeInMillis, alarmIntent)

        Toast.makeText(
            this.applicationContext,
            formatter.format(calendar.timeInMillis),
            Toast.LENGTH_SHORT
        ).show()
        Log.v(TOKEN, "end triggerAlarm")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        alarmReceiver = AlarmReceiver()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
