package mauz.toto.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class MaintainTemplatesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    companion object {
        const val TOKEN = "MaintainTemplates"
        const val REMINDER_EXTRA = "EXTRA_REMINDER"
        val ITEMS: MutableList<Reminder> = ArrayList()
    }

    fun goToNewReminder(view: View) {
        Log.v(TOKEN, "initialize new template instance")
        val intent = Intent(this, TemplateActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        loadTemplates()
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintain_templates)
        loadTemplates()

        viewManager = LinearLayoutManager(this)
        viewAdapter = ReminderAdapter(ITEMS)
        (viewAdapter as ReminderAdapter).onItemClick = { reminder: Reminder ->
            triggerAlarm(reminder)
        }
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun triggerAlarm(reminder: Reminder) {
        Log.v(TOKEN, "instantiate $reminder.name")

        val intent = Intent(this.applicationContext, AlarmReceiver::class.java)
        val alarmIntent =
            PendingIntent.getBroadcast(
                this.applicationContext,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        intent.putExtra(REMINDER_EXTRA, reminder)

        val alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager

        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, reminder.duration)

        alarmManager.set(AlarmManager.RTC, calendar.timeInMillis, alarmIntent)
    }

    private fun loadTemplates() {
        Log.v(TOKEN, "Load persisted templates")

        val templates = File(filesDir, TemplateActivity.fileName)

        if (templates.isFile && templates.canRead()) {
            ITEMS.clear()
            val lines = File(filesDir, TemplateActivity.fileName).readLines()
            for (line in lines) {
                val parts = line.split(";")
                val name = parts[0]
                val duration = parts[1].toInt()
                ITEMS.add(Reminder(name, duration))
            }
            if (::viewAdapter.isInitialized)
                viewAdapter.notifyDataSetChanged()
        } else {
            err(TOKEN, this.applicationContext, getString(R.string.msgLoadTemplateError))
        }
    }
}
