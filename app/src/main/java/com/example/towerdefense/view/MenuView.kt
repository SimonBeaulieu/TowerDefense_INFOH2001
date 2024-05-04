package com.example.towerdefense.view

import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.towerdefense.R
import com.example.towerdefense.controller.MenuViewListener

class MenuView(private val app : AppCompatActivity, private val mController : MenuViewListener) {
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