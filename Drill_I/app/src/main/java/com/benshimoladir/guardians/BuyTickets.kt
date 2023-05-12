package com.benshimoladir.guardians

import android.app.DatePickerDialog
import android.content.DialogInterface

import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.benshimoladir.guardians.databinding.FragmentBuyTicketsBinding

class BuyTickets : Fragment() {
    private var _binding : FragmentBuyTicketsBinding? = null
    private val binding get() = _binding!!

    private var selectedDates = false
    private var year = 0
    private var month = 0
    private var day = 0

    private lateinit var cinemaCarousel: ViewPager
    private val cinemaPictures = listOf(R.drawable.cinema_glilot,
                                        R.drawable.cinema_krayot,
                                        R.drawable.vip_cinema)


    private lateinit var spinner: Spinner
    private val cinemaTheatre = arrayOf(R.string.cinema_glilot,
                                         R.string.cinema_krayot,
                                         R.string.cinema_vip)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cinemaCarousel = view.findViewById(R.id.cinema_carousel)
        cinemaCarousel.adapter = ImageCarouselAdapter(requireContext(), cinemaPictures)

//
//        spinner = view.findViewById(R.id.spinner)
//        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, cinemaTheatre)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyTicketsBinding.inflate(inflater, container, false)

        binding.orderNowBtn.setOnClickListener {
            findNavController().navigate(R.id.action_buyTickets_to_purchaseSummary)
        }

        binding.ticketsBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_buyTickets_pop)
        }

        binding.pickDateButton.setOnClickListener {
            val c = Calendar.getInstance()
            val listener = DatePickerDialog.OnDateSetListener { _, p1, p2, p3 ->
                year = p1
                month = p2
                day = p3
                selectedDates = true
            }
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                listener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}