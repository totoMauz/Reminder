package mauz.toto.reminder

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MaintainTemplatesActivity : AppCompatActivity() {
    companion object {
        const val TOKEN = "MaintainTemplates"
        val ITEMS: MutableList<Reminder> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintain_templates)
        loadTemplates()
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

            val textView = findViewById<TextView>(R.id.lblContent).apply {
                text = File(filesDir, TemplateActivity.fileName).readText()
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
