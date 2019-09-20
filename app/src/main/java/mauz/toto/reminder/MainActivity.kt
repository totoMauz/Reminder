package mauz.toto.reminder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    companion object {
        const val TOKEN = "AlarmReceiver"
    }

    // private var alarmReceiver: AlarmReceiver? = null

    /*
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
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(TOKEN, "initialize main activity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        /*
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        alarmReceiver = AlarmReceiver()
        */
    }

    fun goToNewReminder(view: View) {
        Log.v(TOKEN, "initialize new template instance")
        val intent = Intent(this, NewTemplateActivity::class.java)
        startActivity(intent)
    }

    private fun newTemplate() {
        Log.v(TOKEN, "initialize new template activity")
        val intent = Intent(this, NewTemplateActivity::class.java)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_new_template -> {
                newTemplate()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
