package com.example.towerdefense.controller

import com.example.towerdefense.MainActivity
import com.example.towerdefense.view.MenuView

// MenuController.kt
class MenuController(private val app: MainActivity) : MenuControllerListener {
    private var mView : MenuView = MenuView(app, this)

    override fun switchToSelector() {
        app.showSelector()
    }
}