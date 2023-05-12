package com.benshimoladir.guardians

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.benshimoladir.guardians.databinding.FragmentOpenLoaderBinding

class OpenLoader : Fragment() {
    private var _binging : FragmentOpenLoaderBinding? = null
    private val binding get() = _binging!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binging = FragmentOpenLoaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val banner: ImageView = view.findViewById(R.id.loader_banner)
        animateBanner(banner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binging = null
    }

    /**
     * Apply animation-chain on given image.
     * Animations - (scale-up) -> (shake) -> (fade-out)
     *
     * Args:
     * - image - an ImageView instance to be animated
     *
     * Returns: None.
     * */
    private fun animateBanner(image: ImageView) {
        val scaleUp = AnimationUtils.loadAnimation(context, R.anim.anim_scale_up)
        val shake = AnimationUtils.loadAnimation(context, R.anim.anim_shake)
        val fadeOut = AnimationUtils.loadAnimation(context, R.anim.anim_fade_out)

        scaleUp.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                image.startAnimation(shake)
            }
        })

        shake.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                image.startAnimation(fadeOut)
            }
        })

        fadeOut.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                moveNextFragment()
            }
        })

        image.startAnimation(scaleUp)
    }

    private fun moveNextFragment() {
        findNavController().navigate(R.id.action_openLoader_to_movieInfo)
    }
}
