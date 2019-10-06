package mauz.toto.reminder

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.ActionMode
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mauz.toto.reminder.MaintainTemplatesActivity.Companion.EXTRA_REMINDER

class MaintainReminderActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var handler = Handler()

    companion object {
        const val TOKEN = "MaintainReminder"
        val INTENTS: MutableList<Intent> = ArrayList()
    }

    override fun onResume() {
        super.onResume()
        handler.post(runnableCode)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnableCode)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintain_reminder)

        viewManager = LinearLayoutManager(this)
        viewAdapter = ReminderAdapter(INTENTS)
        (viewAdapter as ReminderAdapter).apply {
            onItemClick = { intent: Intent ->
                cancelAlarm(intent)
            }
        }
        recyclerView = findViewById<RecyclerView>(R.id.rvReminder).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun cancelAlarm(intent: Intent) {
        Log.v(TOKEN, "cancel reminder")
        makeToast(this.applicationContext, getString(R.string.msgCancelReminder, intent.getStringExtra(EXTRA_REMINDER)))

        PendingIntent.getBroadcast(
            this.applicationContext, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        ).cancel()

        val position = INTENTS.indexOf(intent)
        INTENTS.removeAt(position)
        viewAdapter.notifyDataSetChanged()
    }

    private val runnableCode = object : Runnable {
        override fun run() {
            Log.d(TOKEN, "Update UI tick")
            viewAdapter.notifyDataSetChanged()
            handler.postDelayed(this, 1000)
        }
    }
}
