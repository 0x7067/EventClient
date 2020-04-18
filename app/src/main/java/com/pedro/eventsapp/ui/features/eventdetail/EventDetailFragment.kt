package com.pedro.eventsapp.ui.features.eventdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.pedro.eventsapp.R
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

        eventTitle.text = args.title
        eventImage.load(args.imageURL)
        eventPrice.text = "R$${args.price}"
        eventDescription.text = args.description
    }
}