package mauz.toto.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mauz.toto.reminder.MaintainReminderActivity.Companion.INTENTS
import java.io.File
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
        (viewAdapter as TemplateAdapter).onItemClick = { reminder: Reminder ->
            triggerAlarm(reminder)
        }
        (viewAdapter as TemplateAdapter).onLongItemClick = { reminder: Reminder ->
            startActionMode(TemplateActionModeCallback(reminder), ActionMode.TYPE_FLOATING)
            true
        }
        recyclerView = findViewById<RecyclerView>(R.id.rvTemplates).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
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

            viewAdapter?.notifyDataSetChanged()
            saveTemplates()
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

    private fun saveTemplates() {
        Log.v(TOKEN, "Save persisted templates")
        try {
            val fos = openFileOutput(
                TemplateActivity.FILE_NAME,
                Context.MODE_PRIVATE
            )

            for (template in ITEMS) {
                fos.write("$template.name;$template.duration\n".toByteArray())
            }
            fos.close()
        } catch (e: Exception) {
            err(
                TOKEN,
                this.applicationContext,
                getString(R.string.msgSaveTemplateError),
                e
            )
        }
    }

    private fun loadTemplates() {
        Log.v(TOKEN, "Load persisted templates")

        val templates = File(filesDir, TemplateActivity.FILE_NAME)

        if (templates.isFile && templates.canRead()) {
            ITEMS.clear()
            val lines = File(filesDir, TemplateActivity.FILE_NAME).readLines()
            for (line in lines) {
                val parts = line.split(";")
                val name = parts[0]
                val duration = parts[1].toInt()
                ITEMS.add(Reminder(name, duration))
            }

            viewAdapter?.notifyDataSetChanged()
        } else {
            err(TOKEN, this.applicationContext, getString(R.string.msgLoadTemplateError))
        }
    }
}
