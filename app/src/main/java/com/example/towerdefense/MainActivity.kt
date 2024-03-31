package com.example.towerdefense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.gridlayout.widget.GridLayout
import android.widget.ImageView
import com.example.towerdefense.model.Game
import com.example.towerdefense.model.MapGame

class MainActivity : AppCompatActivity() {
    private lateinit var gridLayoutMap : androidx.gridlayout.widget.GridLayout
    private val game : Game = Game()

    private val mapGame : MapGame = MapGame()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMapGrid()
    }

    private fun initMapGrid() {
        gridLayoutMap = findViewById(R.id.gridLayoutMap)
        gridLayoutMap.columnCount = mapGame.nColumns
        gridLayoutMap.rowCount = mapGame.nRows
        drawMap()
    }

    private fun drawMap() {
        for (c in 0 until mapGame.nColumns) {
            for (r in 0 until mapGame.nRows) {
                when (mapGame.mapGrid[c][r]) {
                    mapGame.emptyTile, mapGame.towerTile -> {
                        // Show grass
                        drawTile(c,r, R.drawable.grass)
                    }
                    else -> {
                        // Show road
                        drawTile(c,r, R.drawable.road)
                    }
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
        params.width = mapGame.pxPerTile
        params.height = mapGame.pxPerTile

        // Set layout parameters
        imageView.layoutParams = params

        // Add the TextView to the GridLayout
        gridLayoutMap.addView(imageView)
    }
}