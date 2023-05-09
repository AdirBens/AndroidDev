package com.benshimoladir.guardians

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.helper.widget.Carousel
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.benshimoladir.guardians.databinding.FragmentMovieInfoBinding

class MovieInfo : Fragment() {

    private var _binding : FragmentMovieInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageCarousel: ViewPager
    private val imageList = listOf(R.drawable.scen_1, R.drawable.scen_2, R.drawable.scen_3,
        R.drawable.scen_4, R.drawable.scen_5)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageCarousel = view.findViewById(R.id.image_carousel)
        imageCarousel.adapter = ImageCarouselAdapter(requireContext(), imageList)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieInfoBinding.inflate(inflater, container, false)

        binding.gotBuyTicketsBTN.setOnClickListener {
            findNavController().navigate(R.id.action_movieInfo_to_buyTickets2)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}