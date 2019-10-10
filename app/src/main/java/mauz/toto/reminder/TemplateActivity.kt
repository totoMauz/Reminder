package mauz.toto.reminder

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import mauz.toto.reminder.MaintainTemplatesActivity.Companion.EXTRA_DURATION
import mauz.toto.reminder.MaintainTemplatesActivity.Companion.EXTRA_REMINDER


class TemplateActivity : AppCompatActivity() {
    companion object {
        const val TOKEN = "NewTemplate"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_template)

        val btnSave = findViewById<Button>(R.id.btnSave)
        btnSave.isEnabled = false
        val txtDuration = findViewById<TextView>(R.id.txtDuration)
        val txtName = findViewById<TextView>(R.id.txtName)

        txtName.addTextChangedListener(afterTextChanged = { nameString ->
            if (nameString != null && nameString.isBlank()) {
                txtName.error = getString(R.string.msgInvalidNameInput)
                btnSave.isEnabled = false
            } else {
                txtName.error = null
                btnSave.isEnabled = txtDuration.error == null
            }
        })

        txtDuration.addTextChangedListener(afterTextChanged = { durationString ->
            if (durationString != null && durationString.contains(':')) {
                val durationParts = durationString.split(':')
                if (durationParts.size > 2 || durationParts[0].length > 2 || durationParts[1].length > 2) {
                    txtDuration.error = getString(R.string.msgInvalidDurationInput, txtDuration.text)
                    btnSave.isEnabled = false
                } else {
                    txtDuration.error = null
                    btnSave.isEnabled = txtName.error == null
                }
            }
        })
    }

    override fun onResume() {
        if (intent != null) {
            val name = intent.getStringExtra(EXTRA_REMINDER)
            if (name != null) {
                findViewById<TextView>(R.id.txtName).text = name

                val duration = intent.getIntExtra(EXTRA_DURATION, 0)

                val reminder = Reminder(name, duration)
                findViewById<TextView>(R.id.txtDuration).text = reminder.getDuration()
            }
        }

        super.onResume()
    }

    private fun transformDuration(): Int {
        val durationString = findViewById<TextView>(R.id.txtDuration).text.toString()

        if (durationString.contains(':')) {
            val durationParts = durationString.split(':')
            return (durationParts[0].toInt() * HOUR_TO_MINUTE) + (durationParts[1].toInt())
        }
        return durationString.toInt()
    }

    fun saveTemplate(view: View) {
        Log.v(TOKEN, "Save templates")

        val duration = transformDuration()
        if (duration <= 0) {
            dbg(
                TOKEN,
                this.applicationContext,
                getString(
                    R.string.msgInvalidDurationInput,
                    findViewById<TextView>(R.id.txtDuration).text
                )
            )

            findViewById<TextView>(R.id.txtDuration).requestFocus()
            return
        }

        val name = findViewById<TextView>(R.id.txtName).text.toString()
        appendReminder(applicationContext, Reminder(name, duration))

        makeToast(applicationContext, getString(R.string.msgSaveTemplateSuccess, name))
        this.finish()
    }
}
