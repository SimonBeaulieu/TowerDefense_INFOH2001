package com.example.towerdefense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.towerdefense.controller.GameController
import com.example.towerdefense.controller.MenuController

class MainActivity : AppCompatActivity() {
    private lateinit var container: ConstraintLayout
    private lateinit var gameRoot : ConstraintLayout
    private lateinit var menuRoot : ConstraintLayout

    private lateinit var gameController: GameController
    private lateinit var menuController: MenuController

    fun showGame() {
        gameRoot.visibility = View.VISIBLE
        menuRoot.visibility = View.GONE

        gameController.enableDisplay = true
        gameController.showView()
    }

    fun showMenu() {
        menuRoot.visibility = View.VISIBLE
        gameRoot.visibility = View.GONE

        gameController.enableDisplay = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Container of subviews
        container = findViewById(R.id.container)

        // Load XML
        loadXML()

        // Controllers creation
        gameController = GameController(this)
        menuController = MenuController(this)

        // Enable menu
        showMenu()
    }

    private fun loadXML() {
        val inflater = LayoutInflater.from(this)

        // Inflate XML
        val temp1 = inflater.inflate(R.layout.activity_game, null)
        gameRoot = temp1.findViewById(R.id.gameRoot)

        val temp2 = inflater.inflate(R.layout.activity_menu, null)
        menuRoot = temp2.findViewById(R.id.menuRoot)

        // Add root elements of each XML in the mainActivity
        addContentView(gameRoot, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        addContentView(menuRoot, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
    }
}
