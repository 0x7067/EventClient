package com.pedro.eventsapp.ui.features.eventdetail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.pedro.eventsapp.R
import com.pedro.eventsapp.ui.features.eventdetail.EventDetailFragmentDirections.actionCheckin
import kotlinx.android.synthetic.main.fragment_event_detail.*

class EventDetailFragment : Fragment() {

    private val args: EventDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        renderEvent()
    }

    private fun renderEvent() {
        eventTitle.text = args.title
        eventImage.load(args.imageURL)
        eventPrice.text = "R$${args.price}"
        eventDescription.text = args.description
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buttonShare.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, args.title)
                putExtra(Intent.EXTRA_TEXT, args.description)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        buttonCheckin.setOnClickListener {
            val directions = actionCheckin(args.eventID)
            findNavController().navigate(directions)
        }
    }
}