package com.example.towerdefense

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.gridlayout.widget.GridLayout
import android.widget.ImageView
import com.example.towerdefense.model.Game
import com.example.towerdefense.model.MapViewer
import com.example.towerdefense.model.GameMap
import com.example.towerdefense.model.Tiles

class MainActivity : AppCompatActivity() {
    private lateinit var gridLayoutMap : androidx.gridlayout.widget.GridLayout
    private lateinit var buttonStartWave : Button
    private lateinit var button : Button
    private lateinit var buttonArcher : ImageButton
    private lateinit var buttonRemove : ImageButton
    private var selectedTower : ImageButton? = null

    private val game : Game = Game()
    private val mapGame : GameMap = GameMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //buttonStartWave.findViewById<Button>(R.id.buttonStartWave)
        initMapGrid()
        buttonArcher = findViewById<ImageButton>(R.id.buttonArcher)
        buttonRemove = findViewById<ImageButton>(R.id.removeButton)
        button = findViewById<Button>(R.id.button)
        //gridLayoutMap.setOnTouchListener {}

    }

    private fun initMapGrid() {
        gridLayoutMap = findViewById(R.id.gridLayoutMap)
        gridLayoutMap.columnCount = GameMap.N_COLUMNS
        gridLayoutMap.rowCount = GameMap.N_ROWS
        drawMap()
    }

    private fun drawMap() {
        for (c in 0 until GameMap.N_COLUMNS) {
            for (r in 0 until GameMap.N_ROWS) {
                if (MapViewer.getTileContent(c,r) <= Tiles.EMPTY.value) {
                    // Show grass
                    drawTile(c,r, R.drawable.grass)
                } else {
                    // Show road
                    drawTile(c,r, R.drawable.road)
                }
            }
        }
    }

    private fun drawTile(c : Int, r: Int, resId:Int){
        val imageView = ImageView(this)
        imageView.setImageResource(resId)

        // Specify layout parameters
        val params = GridLayout.LayoutParams()
        params.columnSpec = GridLayout.spec(c)
        params.rowSpec = GridLayout.spec(r)
        params.width = GameMap.PX_PER_TILE
        params.height = GameMap.PX_PER_TILE

        // Set layout parameters
        imageView.layoutParams = params

        // Add the TextView to the GridLayout
        gridLayoutMap.addView(imageView)
    }

    fun onClickButtonStartWave() {
        game.startWave()
    }
    fun onClickButtonArcher(view: View) {
        if (selectedTower == buttonArcher) {
            buttonArcher.setBackgroundColor(Color.TRANSPARENT)
            selectedTower = null
        } else {
            this.buttonArcher.setBackgroundColor(Color.GREEN)
            selectedTower = buttonArcher
        }




    }

    private fun placeTower(c: Int, r: Int, towerType: Tiles){
        game.addTower(c, r, towerType)

        //!!!KC : À ajouter: Gérer l'image selon le towerType sélectionné
        drawTile(c, r, R.drawable.ic_launcher_foreground)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun onClickGridLayoutMap(view: View){
        gridLayoutMap.setOnTouchListener{ v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Get X and Y coordinates of the touch event
                val x = event.x.toInt()
                val y = event.y.toInt()

                val pos: Pair<Int, Int> = GameMap.pixelToGrid(x,y)
                button.text = "(${pos.first},${pos.second})"
                when (selectedTower){
                    buttonArcher -> placeTower(pos.first, pos.second, Tiles.ARCHER)
                }
            }

            // Return false to indicate that we haven't consumed the touch event,
            false

        }
    }

    fun onClickRemoveButton(view: View){
        game.addTower(2, 2, Tiles.EMPTY)
        drawTile(2, 2, R.drawable.grass)
    }

}