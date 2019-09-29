package mauz.toto.reminder

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ReminderAdapter(private val items: List<Intent>, private val time: List<Long>) :
    RecyclerView.Adapter<ReminderAdapter.MyViewHolder>() {
    var onItemClick: ((Intent) -> Unit)? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.lblReminderName)
        val textViewRemainingDuration: TextView = view.findViewById(R.id.lblRemainingDuration)

        init {
            view.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reminder_view, parent, false) as View

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val reminder =
            items[position].extras?.getParcelable<Reminder>(MaintainTemplatesActivity.REMINDER_EXTRA)

        val calendar: Calendar = Calendar.getInstance()
        var diff = time[position] - calendar.timeInMillis

        val hours = diff / HOUR_TO_MILLIS
        diff -= hours * HOUR_TO_MILLIS
        val minutes = diff / MINUTE_TO_MILLIS

        holder.textViewName.text = reminder?.name
        holder.textViewRemainingDuration.text = "-${formatTime(hours, minutes)}"
    }

    override fun getItemCount() = items.size
}