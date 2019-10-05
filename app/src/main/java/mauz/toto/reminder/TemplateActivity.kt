package mauz.toto.reminder

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TemplateActivity : AppCompatActivity() {
    companion object {
        const val TOKEN = "NewTemplate"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_template)
    }

    private fun transformDuration(): Int {
        val durationString = findViewById<TextView>(R.id.txtDuration).text.toString()

        if (durationString.contains(':')) {
            val durationParts = durationString.split(':')
            if (durationParts.size > 2) {
                return -1
            }

            if (durationParts[0].length > 2 || durationParts[1].length > 2) {
                return -1
            }

            return (durationParts[0].toInt() * HOUR_TO_MINUTE) + (durationParts[1].toInt())
        }
        return durationString.toInt()
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
            dbg(TOKEN, this.applicationContext, getString(R.string.msgInvalidDurationInput, findViewById<TextView>(R.id.txtDuration).text.toString()))

            findViewById<TextView>(R.id.txtDuration).requestFocus()
            return
        }

        appendReminder(applicationContext, Reminder(name, duration))

        makeToast(applicationContext, getString(R.string.msgSaveTemplateSuccess, name) )
        this.finish()
    }
}
