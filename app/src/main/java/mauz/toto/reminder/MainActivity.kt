package mauz.toto.reminder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    companion object {
        const val TOKEN = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(TOKEN, "initialize main activity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.v(TOKEN, "menu opened")
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_new_template -> {
                Log.v(TOKEN, "initialize new template activity")
                val intent = Intent(this, TemplateActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_list_reminder -> {
                Log.v(TOKEN, "initialize maintain reminder activity")
                val intent = Intent(this, MaintainReminderActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_list_templates -> {
                Log.v(TOKEN, "initialize maintain templates activity")
                val intent = Intent(this, MaintainTemplatesActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
