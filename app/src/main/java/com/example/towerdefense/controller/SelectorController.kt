package com.example.towerdefense.controller

import com.example.towerdefense.view.SelectorView

class SelectorController(private val app: MainActivity) : SelectorMenuListener {
    private var mView : SelectorView = SelectorView(app, this)

    override fun switchToGame() {
        app.showGame()
    }
}