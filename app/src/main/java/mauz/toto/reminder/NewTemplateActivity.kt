package mauz.toto.reminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class NewTemplateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_template)
    }

    fun newTemplate(view: View) {
       // Reminder(findViewById<TextView>(R.id.txtName).text, findViewById<TextView>(R.id.txtDuration).text)

    }
}
