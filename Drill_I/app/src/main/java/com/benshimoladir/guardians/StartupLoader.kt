package com.benshimoladir.guardians


import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.benshimoladir.guardians.databinding.FragmentStartupLoaderBinding
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import androidx.core.os.HandlerCompat
import java.util.logging.Handler


class StartupLoader : Fragment() {
    private lateinit var bannerImage: ImageView
    private var resumeDelay: Long = 0

    private var _binding: FragmentStartupLoaderBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bannerImage = view.findViewById(R.id.load_banner)
        animateBanner(bannerImage)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartupLoaderBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun animateBanner(image: ImageView) {
        val scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up)
        val shaker = AnimationUtils.loadAnimation(context, R.anim.shake_image)
        val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)

        scaleUp.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                image.startAnimation(shaker)
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })

        shaker.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                image.startAnimation(fadeOut)
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })

        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                moveToNextFragment()
            }
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
        })

        image.startAnimation(scaleUp)

    }

    private fun moveToNextFragment(){
        findNavController().navigate(R.id.action_startupLoader_to_movieInfo)
    }
}