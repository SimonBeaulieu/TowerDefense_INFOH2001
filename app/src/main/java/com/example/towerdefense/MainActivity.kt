package com.example.towerdefense

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.gridlayout.widget.GridLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.example.towerdefense.model.Archer
import com.example.towerdefense.model.Game
import com.example.towerdefense.model.MapViewer
import com.example.towerdefense.model.GameMap
import com.example.towerdefense.model.Soldier
import com.example.towerdefense.model.Tiles

class MainActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var gridLayoutMap : androidx.gridlayout.widget.GridLayout
    private lateinit var layoutBodies : FrameLayout

    private lateinit var buttonStartWave : Button

    private lateinit var buttonArcher : ImageButton
    private var selectedTower : ImageButton? = null

    private val game : Game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initGrids()
        initButtons()
        layoutBodies = findViewById(R.id.layoutBodies)

        drawingThread()
    }

    private fun initButtons() {
        buttonStartWave = findViewById(R.id.buttonStartWave)
        buttonArcher = findViewById(R.id.buttonArcher)

    }
    private fun initGrids() {
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
                    drawTileMap(c,r, R.drawable.grass)
                } else {
                    // Show road
                    drawTileMap(c,r, R.drawable.road)
                }
            }
        }
    }

    private fun drawTileMap(c : Int, r: Int, resId:Int){
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

    private fun drawBody(c : Int, r: Int, resId: Int) {
        val imageView = ImageView(this)
        imageView.setImageResource(resId)

        // Specify layout parameters
        val p = GameMap.gridToPixel(c,r)
        val params = FrameLayout.LayoutParams(GameMap.PX_PER_TILE, GameMap.PX_PER_TILE)
        params.leftMargin = p.first
        params.topMargin = p.second

        // Set layout parameters
        imageView.layoutParams = params

        // Add the TextView to the GridLayout
        layoutBodies.addView(imageView)
    }

    private fun toggleTowerButton(imageButton: ImageButton) {
        if (selectedTower == imageButton) {
            imageButton.setBackgroundColor(Color.TRANSPARENT)
            selectedTower = null
        } else {
            imageButton.setBackgroundColor(Color.GREEN)
            selectedTower = imageButton
        }
    }

    private fun isTowerSelected(imageButton: ImageButton) : Boolean {
        return imageButton == selectedTower
    }

    fun onClickStartWave(view: View) {
        game.startWave()
    }

    fun onClickButtonArcher(view: View) {
        toggleTowerButton(buttonArcher)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun onClickGridLayoutMap(view: View){
        gridLayoutMap.setOnTouchListener{_, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Get X and Y coordinates of the touch event
                val x = event.x.toInt()
                val y = event.y.toInt()
                val pos: Pair<Int, Int> = GameMap.pixelToGrid(x,y)

                when (selectedTower) {
                    buttonArcher -> {
                        game.addTower(pos.first, pos.second, Tiles.ARCHER)
                        toggleTowerButton(buttonArcher)
                    }
                }
            }
            // Return false to indicate that we haven't consumed the touch event,
            false
        }
    }

    private fun drawingThread() {
        Thread {
            var image = 0

            while (true) {
                handler.post {
                    layoutBodies.removeAllViews()

                    for (b in game.getDrawableBodies()) {
                        if (b is Archer) {
                            image = R.drawable.archer
                        } else if (b is Soldier) {
                            image = R.drawable.soldier
                        }
                        drawBody(b.mGridX, b.mGridY, image)
                    }
                }
                Thread.sleep(50)
            }
        }.start()
    }
}