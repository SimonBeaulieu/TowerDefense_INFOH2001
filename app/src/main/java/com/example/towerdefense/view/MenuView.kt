package com.example.towerdefense.view

import android.view.View
import android.widget.Button
import com.example.towerdefense.MainActivity
import com.example.towerdefense.R
import com.example.towerdefense.controller.MenuControllerListener

class MenuView(private val app : MainActivity, private val mListener : MenuControllerListener) {
    private lateinit var buttonStart : Button

    init {
        initButtons()
    }

    private fun initButtons() {
        buttonStart = app.findViewById(R.id.buttonStart)
        buttonStart.setOnClickListener { onClickButtonStart(buttonStart) }
    }
    fun onClickButtonStart(view: View) {
        mListener.switchToGame()
    }
}