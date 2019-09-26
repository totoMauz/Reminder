package mauz.toto.reminder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class MaintainTemplatesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    companion object {
        const val TOKEN = "MaintainTemplates"
        val ITEMS: MutableList<Reminder> = ArrayList()
    }

    fun goToNewReminder(view: View) {
        Log.v(TOKEN, "initialize new template instance")
        val intent = Intent(this, TemplateActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintain_templates)
        loadTemplates()

        viewManager = LinearLayoutManager(this)
        viewAdapter = ReminderAdapter(ITEMS)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
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
                val duration = parts[1].toLong()
                ITEMS.add(Reminder(name, duration))
            }
            if(::viewAdapter.isInitialized)
                viewAdapter.notifyDataSetChanged()
        } else {
            err(TOKEN, this.applicationContext, getString(R.string.msgLoadTemplateError))
        }
    }
}
