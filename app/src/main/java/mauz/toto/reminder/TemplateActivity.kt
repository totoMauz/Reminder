package mauz.toto.reminder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

data class Reminder(val name: String, val duration: Long)

class TemplateActivity : AppCompatActivity() {
    companion object {
        const val fileName = "templates"
        const val TOKEN = "NewTemplate"
        const val MIN_TO_MILLI = 60000
        const val HOUR_TO_MILLI = 3600000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_template)
    }

    fun saveTemplate(view: View) {
        Log.v(TOKEN, "Save templates")

        try {

            val name = findViewById<TextView>(R.id.txtName).text.toString()
            val durationString = findViewById<TextView>(R.id.txtDuration).text.toString()

            val duration: Long
            if (durationString.contains(':')) {
                val durationParts = durationString.split(':')

                if (durationParts.size > 2) {
                    Toast.makeText(
                        this.applicationContext,
                        getString(R.string.txtInvalidDurationInput),
                        Toast.LENGTH_SHORT
                    ).show()
                    findViewById<TextView>(R.id.txtDuration).requestFocus()
                    return
                }

                if (durationParts[0].length > 2 || durationParts[1].length > 2) {
                    Toast.makeText(
                        this.applicationContext,
                        getString(R.string.txtInvalidDurationInput),
                        Toast.LENGTH_SHORT
                    ).show()
                    findViewById<TextView>(R.id.txtDuration).requestFocus()
                    return
                }

                duration =
                    (durationParts[0].toLong() * HOUR_TO_MILLI) + (durationParts[1].toLong() * MIN_TO_MILLI)
            } else {
                duration = durationString.toLong() * MIN_TO_MILLI
            }

            val reminder = Reminder(name, duration)

            val fos = openFileOutput(fileName, Context.MODE_PRIVATE + Context.MODE_APPEND)
            fos.write(reminder.toString().toByteArray())
            fos.close()

            Toast.makeText(
                this.applicationContext,
                getString(R.string.txtSaveTemplateSuccess),
                Toast.LENGTH_SHORT
            ).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                this.applicationContext,
                getString(R.string.txtSaveTemplateError),
                Toast.LENGTH_SHORT
            ).show()

            Log.v(TOKEN, getString(R.string.txtSaveTemplateError) + " " + e.printStackTrace())
        }
    }
}
