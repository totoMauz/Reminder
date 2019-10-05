package mauz.toto.reminder

import android.content.Intent
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import mauz.toto.reminder.MaintainTemplatesActivity.Companion.EXTRA_DURATION
import mauz.toto.reminder.MaintainTemplatesActivity.Companion.EXTRA_REMINDER

class TemplateActionModeCallback(val reminder: Reminder, private val templateActivity: MaintainTemplatesActivity) : ActionMode.Callback {
    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.template_delete -> {
                templateActivity.deleteTemplate(reminder)
                mode.finish()
                true
            }
            R.id.template_edit -> {
                templateActivity.deleteTemplate(reminder)

                val intent = Intent(templateActivity.applicationContext, TemplateActivity::class.java)
                intent.putExtra(EXTRA_REMINDER, reminder.name)
                intent.putExtra(EXTRA_DURATION, reminder.duration)
                templateActivity.startActivityForResult(intent, 1)
                mode.finish()
                true
            }
            else -> false
        }
    }

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        val inflater = mode.menuInflater
        inflater?.inflate(R.menu.template_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode) {}
}