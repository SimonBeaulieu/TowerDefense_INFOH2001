package com.example.towerdefense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.gridlayout.widget.GridLayout
import android.widget.ImageView
import com.example.towerdefense.model.Game
import com.example.towerdefense.model.MapViewer
import com.example.towerdefense.model.GameMap
import com.example.towerdefense.model.Tiles

class MainActivity : AppCompatActivity() {
    private lateinit var gridLayoutMap : androidx.gridlayout.widget.GridLayout
    private lateinit var buttonStartWave : Button

    private val game : Game = Game()
    private val mapGame : GameMap = GameMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonStartWave.findViewById<Button>(R.id.buttonStartWave)

        initMapGrid()
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
}