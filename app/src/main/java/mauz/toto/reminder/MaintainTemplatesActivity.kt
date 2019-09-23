package mauz.toto.reminder

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
            val lines = File(filesDir, TemplateActivity.fileName).readLines()
            for (line in lines) {
                val parts = line.split(";")
                val name = parts[0]
                val duration = parts[1].toLong()
                ITEMS.add(Reminder(name, duration))
            }
        } else {
            Toast.makeText(
                this.applicationContext,
                getString(R.string.txtLoadTemplateError),
                Toast.LENGTH_SHORT
            ).show()
            Log.e(TOKEN, getString(R.string.txtLoadTemplateError))
        }
    }
}
