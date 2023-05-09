package com.benshimoladir.guardians

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.benshimoladir.guardians.databinding.FragmentBuyTicketsBinding

class BuyTickets : Fragment() {
    private var _binding : FragmentBuyTicketsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentBuyTicketsBinding.inflate(inflater, container, false)

        binding.ticketsBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_buyTickets_pop)
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}