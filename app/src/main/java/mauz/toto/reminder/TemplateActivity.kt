package mauz.toto.reminder

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TemplateActivity : AppCompatActivity() {
    companion object {
        const val fileName = "templates"
        const val TOKEN = "NewTemplate"
        const val MIN_TO_MILLI: Long = 60000
        const val HOUR_TO_MILLI: Long = 3600000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_template)
    }

    private fun transformDuration(): Long {
        val durationString = findViewById<TextView>(R.id.txtDuration).text.toString()

        if (durationString.contains(':')) {
            val durationParts = durationString.split(':')
            if (durationParts.size > 2) {
                return -1
            }

            if (durationParts[0].length > 2 || durationParts[1].length > 2) {
                return -1
            }

            return (durationParts[0].toLong() * HOUR_TO_MILLI) + (durationParts[1].toLong() * MIN_TO_MILLI)
        }
        return durationString.toLong() * MIN_TO_MILLI
    }

    fun saveTemplate(view: View) {
        Log.v(TOKEN, "Save templates")

        val name = findViewById<TextView>(R.id.txtName).text.toString()
        if (name.isBlank()) {
            dbg(TOKEN, this.applicationContext, getString(R.string.msgInvalidNameInput))

            findViewById<TextView>(R.id.txtName).requestFocus()
            return
        }

        val duration = transformDuration()
        if (duration <= 0) {
            dbg(TOKEN, this.applicationContext, getString(R.string.msgInvalidDurationInput))

            findViewById<TextView>(R.id.txtDuration).requestFocus()
            return
        }

        try {
            val fos = openFileOutput(fileName, Context.MODE_PRIVATE + Context.MODE_APPEND)
            fos.write("$name;$duration\n".toByteArray())
            fos.close()
        } catch (e: Exception) {
            err(TOKEN, this.applicationContext, getString(R.string.msgSaveTemplateError), e)
        }

        Toast.makeText(
            this.applicationContext,
            getString(R.string.msgSaveTemplateSuccess),
            Toast.LENGTH_SHORT
        ).show()
        this.finish()
    }
}
