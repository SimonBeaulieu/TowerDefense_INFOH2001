package com.example.towerdefense.view

import android.view.View
import android.widget.ImageView
import com.example.towerdefense.MainActivity
import com.example.towerdefense.R
import com.example.towerdefense.controller.MenuControllerListener
import com.example.towerdefense.controller.SelectorControllerListener

class SelectorView(private val app : MainActivity, private val mListener : SelectorControllerListener) {
    private lateinit var imageMap1 : ImageView

    init {
        initImages()
    }

    private fun initImages() {
        imageMap1 = app.findViewById(R.id.imageMap1)
        imageMap1.setOnClickListener { onClickImageMap1(imageMap1) }
    }

    fun onClickImageMap1(view: View) {
        mListener.switchToGame()
    }
}