package mauz.toto.reminder

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MaintainReminderActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    companion object {
        const val TOKEN = "MaintainReminder"
        val INTENTS: MutableList<Intent> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintain_reminder)

        viewManager = LinearLayoutManager(this)
        viewAdapter = ReminderAdapter(INTENTS)
        (viewAdapter as ReminderAdapter).onItemClick = { intent: Intent ->
            cancelAlarm(intent)
        }
        recyclerView = findViewById<RecyclerView>(R.id.rvReminder).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun cancelAlarm(intent: Intent) {
        Log.v(TOKEN, "cancel reminder")
        makeToast(this.applicationContext, getString(R.string.msgCancelReminder))

        PendingIntent.getBroadcast(
            this.applicationContext, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        ).cancel()

        val position = INTENTS.indexOf(intent)
        INTENTS.removeAt(position)
        viewAdapter.notifyDataSetChanged()
    }
}
