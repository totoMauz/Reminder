package mauz.toto.reminder

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem

class TemplateActionModeCallback(val reminder: Reminder) : ActionMode.Callback {
    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.template_delete -> {
                MaintainTemplatesActivity.ITEMS.remove(reminder)
                mode.finish()
                true
            }
            R.id.template_edit -> {
                // TODO open activity with names and flag
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