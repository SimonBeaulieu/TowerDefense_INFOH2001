package com.example.towerdefense.view

import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.towerdefense.R
import com.example.towerdefense.controller.MenuControllerListener

class MenuView(private val app : AppCompatActivity, private val mController : MenuControllerListener) {
    private lateinit var buttonStart : Button

    init {
        initButtons()
    }

    private fun initButtons() {
        buttonStart = app.findViewById(R.id.buttonStart)
        buttonStart.setOnClickListener { onClickButtonStart(buttonStart) }
    }
    fun onClickButtonStart(view: View) {
        mController.switchToSelector()
    }
}