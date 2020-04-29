package com.pedro.eventsapp.ui.features.eventcheckin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.pedro.eventsapp.R
import com.pedro.eventsapp.api.EventsClientImpl
import com.pedro.eventsapp.data.CheckinRequest
import com.pedro.eventsapp.data.network.ResponseHandlerImpl
import com.pedro.eventsapp.data.network.Status
import kotlinx.android.synthetic.main.dialog_event_checkin.*
import kotlinx.android.synthetic.main.fragment_event_detail.buttonCheckin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class EventCheckinFragment : DialogFragment(), CoroutineScope by MainScope() {

    private lateinit var viewModel: EventCheckinViewModel
    private val args: EventCheckinFragmentArgs by navArgs()

    override fun onStart() {
        super.onStart()
        dialog?.window?.run {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.setCanceledOnTouchOutside(true)
        return inflater.inflate(R.layout.dialog_event_checkin, container)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(EventCheckinViewModel::class.java)
        viewModel.eventsClient = EventsClientImpl
        viewModel.responseHandler = ResponseHandlerImpl

        buttonCheckin.setOnClickListener {
            if (inputName.text.isNotEmpty() && inputEmail.text.isNotEmpty()) {
                val checkinRequest =
                    CheckinRequest(
                        args.eventID,
                        inputName.text.toString(),
                        inputEmail.text.toString()
                    )
                postCheckin(checkinRequest)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Campo não pode estar em branco",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun postCheckin(checkinRequest: CheckinRequest) {
        launch {
            val eventsResource = viewModel.postCheckin(checkinRequest)
            when (eventsResource.status) {
                Status.SUCCESS -> {
                    Toast.makeText(
                        requireContext(),
                        "Check-in realizado com sucesso",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        eventsResource.message,
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }
    }
}