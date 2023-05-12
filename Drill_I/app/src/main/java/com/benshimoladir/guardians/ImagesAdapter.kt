package com.benshimoladir.guardians

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter

class ImagesAdapter(private val context: Context, private val imageList: List<ImageItem>) : PagerAdapter() {

    override fun getCount(): Int {
        return imageList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.image_layout, null)

        setNextImage(view, imageList[position])
        container.addView(view)

        return view
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


    private fun setNextImage(view: View, image: ImageItem) {
        val imageSection = view.findViewById<ImageView>(R.id.image_carousel_image)
        val imageLabel = view.findViewById<TextView>(R.id.image_carousel_label)

        imageSection.setImageResource(image.imageSrc)
        imageLabel.text = context.getString(image.imageLabel)
    }
}