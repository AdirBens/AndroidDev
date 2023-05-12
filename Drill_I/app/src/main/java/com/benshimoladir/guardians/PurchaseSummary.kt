package com.benshimoladir.guardians

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.benshimoladir.guardians.databinding.FragmentPurchaseSummaryBinding
import com.google.android.material.snackbar.Snackbar

class PurchaseSummary : Fragment() {
    private var _binding: FragmentPurchaseSummaryBinding? = null
    private val binding get() = _binding!!

    private val adultTicketPrice = 20
    private val kidTicketPrice = 15
    private val vipExtra = 5

    private var adults = 0
    private var kids = 0
    private var cinema = -1
    private var date : String? = ""
    private var totalPrice = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPurchaseSummaryBinding.inflate(inflater, container, false)

        adults = requireArguments().getInt("adults")
        kids = requireArguments().getInt("kids")
        cinema = requireArguments().getInt("cinema")
        date = requireArguments().getString("date")
        totalPrice = calcTotalPrice(kids, adults, cinema)

        binding.ticketsBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_purchaseSummary_pop)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val payButton = binding.payButton
        payButton.setOnClickListener {
            payButton.text = getString(R.string.purchaseSummary_thank_you)
            payButton.setBackgroundResource(R.color.happy_green)
            payButton.isClickable = false
            Snackbar.make(view, R.string.purchaseSummary_dontNeedToPay, 5000).show()
        }

        binding.summaryTotalPrice.text = totalPrice.toString()
        binding.summaryScreeningDate.text = date.toString().subSequence(0, 10)
        binding.summaryAdultsPrice.text = adults.toString()
        binding.summaryKidsPrice.text = kids.toString()
        binding.summaryCinema.text = getCinemaName(cinema)

        if (cinema == 1) {
            binding.summaryVipBadge.visibility = VISIBLE
            binding.vipExtraBox.visibility = VISIBLE
            binding.summaryVipExtra.text = calcVipExtra(kids, adults).toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun calcTotalPrice(numKids: Int, numAdults: Int, cinema: Int): Int {
        var totalPrice = 0
        val adultsTotal = numAdults * adultTicketPrice
        val kidsTotal = numKids * kidTicketPrice

        if (cinema == 1) {
            totalPrice += calcVipExtra(numKids, numAdults)
        }

        totalPrice += adultsTotal + kidsTotal

        return totalPrice
    }

    private fun calcVipExtra(numKids: Int, numAdults: Int): Int {
        return vipExtra * (numKids + numAdults)
    }

    private fun getCinemaName(cinemaId: Int): String {
        when (cinema) {
            0 -> return getString(R.string.purchaseTickets_theatre_glilot)
            1 -> return getString(R.string.purchaseTickets_theatre_idc)
            2 -> return getString(R.string.purchaseTickets_theatre_krayot)
            else -> return getString(R.string.purchaseTickets_theatre_glilot)
        }
    }
}