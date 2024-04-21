package com.example.towerdefense.controller

import com.example.towerdefense.MainActivity
import com.example.towerdefense.view.MenuView

// MenuController.kt
class MenuController(private val app: MainActivity) : MenuControllerListener {
    //private var mMenu : Menu = Menu()
    private var mMenuView : MenuView = MenuView(app, this as MenuControllerListener)

    override fun switchToGame() {
        app.showGame()
    }
}