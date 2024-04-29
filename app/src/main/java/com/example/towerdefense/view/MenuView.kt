package com.example.towerdefense.view

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.towerdefense.R
import com.example.towerdefense.controller.MenuControllerListener

class MenuView(private val app : AppCompatActivity, private val mController : MenuControllerListener) {
    private lateinit var buttonStartGame : Button

    init {
        initButtons()
    }

    private fun initButtons() {
        buttonStartGame = app.findViewById(R.id.buttonStartGame)
        buttonStartGame.setOnClickListener { onClickButtonStart(buttonStartGame) }
    }
    fun onClickButtonStart(view: View) {
        mController.switchToSelector()
    }
}