package mauz.toto.reminder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReminderAdapter(private val items: List<Reminder>) :
    RecyclerView.Adapter<ReminderAdapter.MyViewHolder>() {
    class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.reminder_view, parent, false) as TextView
        return MyViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = items[position].toString()
    }

    override fun getItemCount() = items.size
}