package com.example.towerdefense.controller

import com.example.towerdefense.MainActivity
import com.example.towerdefense.view.MenuView
import com.example.towerdefense.view.SelectorView

class SelectorController(private val app: MainActivity) : SelectorControllerListener {
    private var mView : SelectorView = SelectorView(app, this)

    override fun switchToGame() {
        app.showGame()
    }
}