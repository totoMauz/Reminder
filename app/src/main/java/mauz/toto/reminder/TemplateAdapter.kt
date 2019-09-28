package mauz.toto.reminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TemplateAdapter(private val items: List<Reminder>) :
    RecyclerView.Adapter<TemplateAdapter.MyViewHolder>() {
    var onItemClick: ((Reminder) -> Unit)? = null

    inner class MyViewHolder : RecyclerView.ViewHolder {
        val textViewName: TextView
        val textViewDuration: TextView

        constructor(view: View) : super(view) {
            textViewName = view.findViewById(R.id.lblTemplateName)
            textViewDuration = view.findViewById(R.id.lblTemplateDuration)

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
            .inflate(R.layout.template_view, parent, false) as View

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textViewName.text = items[position].name
        holder.textViewDuration.text = items[position].getDuration()
    }

    override fun getItemCount() = items.size
}