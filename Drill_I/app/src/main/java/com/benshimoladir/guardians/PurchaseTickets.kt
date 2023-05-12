package com.benshimoladir.guardians

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import android.text.style.TtsSpan.DateBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnDragListener
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener
import com.benshimoladir.guardians.databinding.FragmentPurchaseTicketsBinding
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PurchaseTickets : Fragment() {
    private var _binding : FragmentPurchaseTicketsBinding? = null
    private val binding get() = _binding!!

    private lateinit var cinemaCarousel: ViewPager
    private val cinemasList = listOf(
        ImageItem(R.drawable.tickets_cinema_glilot, R.string.purchaseTickets_theatre_glilot),
        ImageItem(R.drawable.tickets_cinema_vip, R.string.purchaseTickets_theatre_idc),
        ImageItem(R.drawable.tickets_cinema_krayot, R.string.purchaseTickets_theatre_krayot))

    private var numAdultsTickets: Int = 0
    private var numKidsTickets: Int = 0
    private var theatreChosen: Int = 0
    private lateinit var screeningDate: Date
    private var selectedDates = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPurchaseTicketsBinding.inflate(inflater, container, false)

        binding.orderNowBtn.setOnClickListener {
            theatreChosen = binding.cinemaCarousel.currentItem

            if (isTicketsNumberValid() && isDateValid()) {
                val bundle = bundleOf(Pair("adults", numAdultsTickets), Pair("kids", numKidsTickets),
                    Pair("date", screeningDate.toString()), Pair("cinema", theatreChosen)
                )
                findNavController().navigate(R.id.action_purchaseTickets_to_purchaseSummary, bundle)
            }
        }

        binding.ticketsBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_purchaseTickets_pop)
        }

        binding.numberOfKids.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                numKidsTickets = progress
                binding.numberOfKidsLegend.text = progress.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.numberOfAdults.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                numAdultsTickets = progress
                binding.numberOfAdultsLegend.text = progress.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.pickDateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                calendar.set(year, month, day)
                screeningDate = calendar.time
                binding.pickDateButton.text = screeningDate.toString()
                selectedDates = true
            }
            val datePickerDialog = DatePickerDialog(requireContext(), listener,
            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cinemaCarousel = view.findViewById(R.id.cinema_carousel)
        cinemaCarousel.adapter = ImagesAdapter(requireContext(), cinemasList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isTicketsNumberValid(): Boolean {
        if (numKidsTickets == 0 && numAdultsTickets == 0) {
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.purchaseTickets_exceptions_must_choose_tickets)
                .setPositiveButton(R.string.purchaseTickets_exceptions_try_again) { _,_ -> }
                .create().show()
            return false
        }

        else if (numKidsTickets > 0 && numAdultsTickets <= 0) {
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.purchaseTickets_exceptions_kids_companion)
                .setPositiveButton(R.string.purchaseTickets_exceptions_try_again) { _,_ -> }
                .create().show()
            return false
        }

        else if (numKidsTickets >= 2 * numAdultsTickets) {
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.purchaseTickets_exceptions_mental_sake)
                .setPositiveButton(R.string.purchaseTickets_exceptions_try_again) { _,_ -> }
                .create().show()
            return false
        }
        return true
    }

    private fun isDateValid(): Boolean {
        if (!selectedDates) {
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.purchaseTickets_exceptions_choose_date)
                .setPositiveButton(R.string.purchaseTickets_exceptions_try_again) { _,_ -> }
                .create().show()
            return false
        }
        else if (screeningDate < Calendar.getInstance().time) {
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.purchaseTickets_exceptions_cant_choose_past)
                .setPositiveButton(R.string.purchaseTickets_exceptions_try_again) { _,_ -> }
                .create().show()
            return false
        }
        return true
    }

}