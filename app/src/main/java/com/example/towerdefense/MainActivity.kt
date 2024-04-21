package com.example.towerdefense

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.gridlayout.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.towerdefense.model.Archer
import com.example.towerdefense.model.Body
import com.example.towerdefense.model.Cannon
import com.example.towerdefense.model.Flamethrower
import com.example.towerdefense.model.Game
import com.example.towerdefense.model.GameMapUtils
import com.example.towerdefense.model.GameMapViewer
import com.example.towerdefense.model.References
import com.example.towerdefense.model.Soldier
import com.example.towerdefense.model.Tiles
import com.example.towerdefense.model.Tower

class MainActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var gridLayoutMap : androidx.gridlayout.widget.GridLayout
    private lateinit var layoutBodies : FrameLayout
    private lateinit var layoutCharacters : LinearLayout

    private lateinit var textHitPoints : TextView
    private lateinit var textMoney : TextView

    private lateinit var buttonStartWave : Button

    private lateinit var buttonArcher : ImageButton
    private lateinit var buttonCannon : ImageButton
    private lateinit var buttonFlamethrower : ImageButton

    private var selectedTower : ImageButton? = null

    private val game : Game = Game()
    private val mGameMapView = References.getRef(GameMapViewer::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutBodies = findViewById(R.id.layoutBodies)
        layoutCharacters = findViewById(R.id.layoutCharacters)
        initButtons()
        initStats()

        setTilesDimensions()

        initGrid()

        drawingThread()
    }

    private fun setTilesDimensions() {
        val screenWidth = resources.displayMetrics.widthPixels - layoutCharacters.layoutParams.width
        val screenHeight = resources.displayMetrics.heightPixels

        val cellWidth = screenWidth / GameMapUtils.N_COLUMNS
        val cellHeight = screenHeight / GameMapUtils.N_ROWS

        GameMapUtils.PX_PER_TILE = minOf(cellWidth, cellHeight)
    }

    private fun initButtons() {
        buttonStartWave = findViewById(R.id.buttonStartWave)
        buttonArcher = findViewById(R.id.buttonArcher)
        buttonCannon = findViewById(R.id.buttonCannon)
        buttonFlamethrower = findViewById(R.id.buttonFlamethrower)
    }

    private fun initStats(){
        textHitPoints = findViewById(R.id.textViewHitPoints)
        textMoney = findViewById(R.id.textViewMoney)

        updateStats()
    }

    private fun updateStats(){
        textHitPoints.text=game.getHitPoints().toString()
        textMoney.text=game.getMoney().toString()
    }

    private fun initGrid() {
        gridLayoutMap = findViewById(R.id.gridLayoutMap)
        gridLayoutMap.columnCount = GameMapUtils.N_COLUMNS
        gridLayoutMap.rowCount = GameMapUtils.N_ROWS
        drawMap()
    }

    private fun drawMap() {
        for (c in 0 until GameMapUtils.N_COLUMNS) {
            for (r in 0 until GameMapUtils.N_ROWS) {
                if (mGameMapView.getTileContent(c,r) <= Tiles.EMPTY.value) {
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
        params.width = GameMapUtils.PX_PER_TILE
        params.height = GameMapUtils.PX_PER_TILE

        // Set layout parameters
        imageView.layoutParams = params

        // Add the TextView to the GridLayout
        gridLayoutMap.addView(imageView)
    }

    private fun drawBody(px : Int, py: Int, resId: Int, body:Body) {
        val imageView = ImageView(this)
        imageView.setImageResource(resId)

        if (body is Tower) {
            imageView.setOnClickListener { onClickTower(body) }
        }

        // Specify layout parameters
        val params = FrameLayout.LayoutParams(GameMapUtils.PX_PER_TILE, GameMapUtils.PX_PER_TILE)
        params.leftMargin = px
        params.topMargin = py

        // Set layout parameters
        imageView.layoutParams = params

        // Add the TextView to the GridLayout
        layoutBodies.addView(imageView)
    }

    private fun onClickTower(tower: Tower) {
        // !!!SB: afficher upgrade list. Avoir une variable "selected" (pas meme chose que selectedTower)
    }

    private fun toggleTowerButton(imageButton: ImageButton) {
        if (selectedTower == imageButton) {
            imageButton.setBackgroundColor(Color.LTGRAY)
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

    fun onClickButtonCannon(view: View){
        toggleTowerButton(buttonCannon)
    }

    fun onClickButtonFlamethrower(view: View){
        toggleTowerButton(buttonFlamethrower)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun onClickGridLayoutMap(view: View){
        gridLayoutMap.setOnTouchListener{_, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Get X and Y coordinates of the touch event
                val x = event.x.toInt()
                val y = event.y.toInt()
                val pos: Pair<Int, Int> = GameMapUtils.pixelToGrid(x,y)

                when (selectedTower) {
                    buttonArcher -> {
                        game.addTower(pos.first, pos.second, Tiles.ARCHER)
                        toggleTowerButton(buttonArcher)
                    }
                    buttonCannon -> {
                        game.addTower(pos.first, pos.second, Tiles.CANNON)
                        toggleTowerButton(buttonCannon)
                    }
                    buttonFlamethrower -> {
                        game.addTower(pos.first, pos.second, Tiles.FLAMETHROWER)
                        toggleTowerButton(buttonFlamethrower)
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
                    updateStats()

                    for (b in game.getDrawableBodies()) {
                        if (b is Archer) {
                            image = R.drawable.archer
                        } else if (b is Cannon){
                            image = R.drawable.cannon
                        } else if (b is Flamethrower){
                            image = R.drawable.flame
                        } else if (b is Soldier) {
                            image = R.drawable.soldier
                        }
                        drawBody(b.getRealX(), b.getRealY(), image, b)
                    }
                }
                Thread.sleep(50)
            }
        }.start()
    }
}