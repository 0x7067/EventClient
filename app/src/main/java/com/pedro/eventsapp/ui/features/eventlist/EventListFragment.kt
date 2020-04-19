package com.pedro.eventsapp.ui.features.eventlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.pedro.eventsapp.R
import com.pedro.eventsapp.api.EventsClientImpl
import com.pedro.eventsapp.data.EventItem
import com.pedro.eventsapp.data.network.ResponseHandlerImpl
import com.pedro.eventsapp.data.network.Status
import com.pedro.eventsapp.ui.features.eventlist.EventListFragmentDirections.actionEventListToEventDetail
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_event_list.*
import kotlinx.android.synthetic.main.item_event.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class EventListFragment : Fragment(), CoroutineScope by MainScope() {

    private lateinit var viewModel: EventListViewModel
    private val adapter = Adapter(mutableListOf(), this::goToEventDetail)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(EventListViewModel::class.java)
        viewModel.eventsClient = EventsClientImpl
        viewModel.responseHandler = ResponseHandlerImpl

        val linearLayoutManager = LinearLayoutManager(requireContext())

        loadEvents(linearLayoutManager)
    }

    private fun loadEvents(linearLayoutManager: LinearLayoutManager) {
        launch {
            val eventsResource = viewModel.fetchEventsList()
            when (eventsResource.status) {
                Status.SUCCESS -> {
                    val events = eventsResource.data
                    renderEventList(linearLayoutManager, events!!)
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), eventsResource.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun renderEventList(
        linearLayoutManager: LinearLayoutManager,
        events: List<EventItem>
    ) {
        eventsRecyclerView.layoutManager = linearLayoutManager
        eventsRecyclerView.adapter = adapter
        adapter.setEventList(events)
    }

    private fun goToEventDetail(
        title: String,
        imageURL: String,
        price: String,
        description: String
    ) {
        val directions = actionEventListToEventDetail(title, imageURL, price, description)
        findNavController().navigate(directions)
    }
}

class Adapter(
    private var eventList: List<EventItem>,
    val action: (String, String, String, String) -> Unit
) : RecyclerView.Adapter<Adapter.ItemViewHolder>() {

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

        holder.itemView.setOnClickListener {
            action(event.title, event.image, event.price.toString(), event.description)
        }
    }

    fun setEventList(eventList: List<EventItem>) {
        this.eventList = eventList
        notifyDataSetChanged()
    }

    class ItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer

    override fun getItemCount(): Int {
        return eventList.size
    }
}