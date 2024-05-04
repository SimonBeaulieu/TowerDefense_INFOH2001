package com.example.towerdefense.view

import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.towerdefense.R
import com.example.towerdefense.controller.SelectorMenuListener

class SelectorView(private val app : AppCompatActivity, private val mController : SelectorMenuListener) {
    private lateinit var imageMap1 : ImageView

    init {
        initImages()
    }

    private fun initImages() {
        imageMap1 = app.findViewById(R.id.imageMap1)
        imageMap1.setOnClickListener { onClickImageMap1(imageMap1) }
    }

    fun onClickImageMap1(view: View) {
        mController.switchToGame()
    }
}