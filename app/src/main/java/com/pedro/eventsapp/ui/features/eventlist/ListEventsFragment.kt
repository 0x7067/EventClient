package com.pedro.eventsapp.ui.features.eventlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.pedro.eventsapp.R
import com.pedro.eventsapp.data.EventItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_event.view.*

class ListEventsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }
}

class Adapter(private val eventList: MutableList<EventItem>) : RecyclerView.Adapter<Adapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_event,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val event = eventList[position]
        holder.itemView.eventName.text = event.title

        holder.itemView.eventImage.load(event.image)

    }

    fun setEventList(eventList: List<EventItem>) {
        this.eventList.addAll(eventList)
        notifyDataSetChanged()
    }

    class ItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer

    override fun getItemCount(): Int {
        return eventList.size
    }
}