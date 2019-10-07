package mauz.toto.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mauz.toto.reminder.MaintainReminderActivity.Companion.INTENTS
import java.util.*
import kotlin.collections.ArrayList

class MaintainTemplatesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    companion object {
        const val TOKEN = "MaintainTemplates"
        const val EXTRA_REMINDER = "EXTRA_REMINDER"
        const val EXTRA_TIME = "EXTRA_TIME"
        const val EXTRA_DURATION = "EXTRA_DURATION"
        val ITEMS: MutableList<Reminder> = ArrayList()
    }

    private val id = generateSequence(0) { it + 1 }

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
        viewAdapter = TemplateAdapter(ITEMS)

        (viewAdapter as TemplateAdapter).apply {
            onItemClick = { reminder: Reminder ->
                triggerAlarm(reminder)
            }
            onLongItemClick = { reminder: Reminder ->
                startActionMode(TemplateActionModeCallback(reminder, this@MaintainTemplatesActivity), ActionMode.TYPE_PRIMARY)
                true
            }
        }
        recyclerView = findViewById<RecyclerView>(R.id.rvTemplates).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    fun deleteTemplate(reminder: Reminder) {
        ITEMS.remove(reminder)
        writeReminder(applicationContext, ITEMS)
        if(::viewAdapter.isInitialized) {
            viewAdapter.notifyDataSetChanged()
        }
    }

    private fun triggerAlarm(reminder: Reminder) {
        Log.v(TOKEN, "instantiate ${reminder.name}")
        makeToast(this.applicationContext, "Started ${reminder.name}")

        val oldPosition = ITEMS.indexOf(reminder)
        if (oldPosition > 0) {
            // move to beginning of least to have most recently used templates first
            ITEMS.removeAt(oldPosition)
            ITEMS.add(0, reminder)

            viewAdapter.notifyDataSetChanged()
            writeReminder(applicationContext, ITEMS)
        }

        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, reminder.duration)

        val intent = Intent(this.applicationContext, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_REMINDER, reminder.name)
        intent.putExtra(EXTRA_TIME, calendar.timeInMillis)

        val alarmIntent =
            PendingIntent.getBroadcast(
                this.applicationContext,
                id.iterator().next(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC, calendar.timeInMillis, alarmIntent)
        INTENTS.add(intent)
    }

    private fun loadTemplates() {
        ITEMS.clear()
        ITEMS.addAll(readReminder(applicationContext))
        if (::viewAdapter.isInitialized) {
            viewAdapter.notifyDataSetChanged()
        }
    }
}
