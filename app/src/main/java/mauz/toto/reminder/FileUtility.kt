package mauz.toto.reminder

import android.content.Context
import android.util.Log
import java.io.File

const val FILE_NAME = "templates"
const val TOKEN = "NewTemplate"

fun appendReminder(context: Context, reminder: Reminder) {
    Log.v(TOKEN, "Append single template")
    try {
        val fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE + Context.MODE_APPEND)
        fos.write("${reminder.name};${reminder.duration}\n".toByteArray())
        fos.close()
    } catch (e: Exception) {
        err(TOKEN, context, context.getString(R.string.msgSaveTemplateError, reminder.name), e)
    }
}

fun writeReminder(context: Context, reminder: MutableList<Reminder>) {
    Log.v(TOKEN, "Save persisted templates")
    try {
        val fos = context.openFileOutput(
            FILE_NAME,
            Context.MODE_PRIVATE
        )

        for (template in reminder) {
            fos.write("${template.name};${template.duration}\n".toByteArray())
        }
        fos.close()
    } catch (e: Exception) {
        err(
            TOKEN,
            context,
            context.getString(R.string.msgSaveTemplatesError),
            e
        )
    }
}

fun readReminder(context: Context): MutableList<Reminder> {
    Log.v(TOKEN, "Load persisted templates")

    val reminder = ArrayList<Reminder>()

    val templates = File(context.filesDir, FILE_NAME)

    if (templates.isFile && templates.canRead()) {
        val lines = File(context.filesDir, FILE_NAME).readLines()
        for (line in lines) {
            val parts = line.split(";")
            val name = parts[0]
            val duration = parts[1].toInt()
            reminder.add(Reminder(name, duration))
        }
    } else {
        err(
            MaintainTemplatesActivity.TOKEN,
            context,
            context.getString(R.string.msgLoadTemplateError)
        )
    }

    return reminder
}