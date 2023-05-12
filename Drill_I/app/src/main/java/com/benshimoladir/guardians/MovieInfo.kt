package com.benshimoladir.guardians

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.benshimoladir.guardians.databinding.FragmentMovieInfoBinding

/**
 *
 * */
class MovieInfo : Fragment() {
    private var _binding: FragmentMovieInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageCarousel: ViewPager
    private val imagesList = listOf(ImageItem(R.drawable.info_scene_1, R.string.movieInfo_scene_1),
                                    ImageItem(R.drawable.info_scene_2, R.string.movieInfo_scene_2),
                                    ImageItem(R.drawable.info_scene_3, R.string.movieInfo_scene_3),
                                    ImageItem(R.drawable.info_scene_4, R.string.movieInfo_scene_4),
                                    ImageItem(R.drawable.info_scene_5, R.string.movieInfo_scene_5))


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieInfoBinding.inflate(inflater, container, false)

        binding.gotBuyTicketsBTN.setOnClickListener {
            findNavController().navigate(R.id.action_movieInfo_to_purchaseTickets)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageCarousel = view.findViewById(R.id.image_carousel)
        imageCarousel.adapter = ImagesAdapter(requireContext(), imagesList)

        print(imageCarousel.currentItem)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}