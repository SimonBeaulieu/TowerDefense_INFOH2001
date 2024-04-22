package com.example.towerdefense.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.towerdefense.MainActivity
import com.example.towerdefense.R
import com.example.towerdefense.controller.GameControllerListener
import com.example.towerdefense.model.Archer
import com.example.towerdefense.model.Body
import com.example.towerdefense.model.Cannon
import com.example.towerdefense.model.Flamethrower
import com.example.towerdefense.model.GameMapUtils
import com.example.towerdefense.model.GameMapViewer
import com.example.towerdefense.model.Soldier
import com.example.towerdefense.model.Tiles
import com.example.towerdefense.model.Tower

class GameView(private val app : MainActivity, private val mListener: GameControllerListener) {
    //**************************************** Variables **************************************** //
    lateinit var layoutCharacters : LinearLayout
    private lateinit var gridLayoutMap : androidx.gridlayout.widget.GridLayout
    private lateinit var layoutBodies : FrameLayout

    private lateinit var textHitPoints : TextView
    private lateinit var textMoney : TextView

    private lateinit var buttonStartWave : Button
    private lateinit var buttonEnd : Button

    private lateinit var buttonArcher : ImageButton
    private lateinit var buttonCannon : ImageButton
    private lateinit var buttonFlamethrower : ImageButton

    private var selectedTower : ImageButton? = null

    //*************************************** Constructor *************************************** //
    init {
        initLayouts()
        initButtons()
        initStats()
        initGrid()
    }

    private fun initLayouts() {
        layoutBodies = app.findViewById(R.id.layoutBodies)
        layoutCharacters = app.findViewById(R.id.layoutCharacters)
    }

    private fun initButtons() {
        buttonStartWave = app.findViewById(R.id.buttonStartWave)
        buttonStartWave.setOnClickListener { onClickButtonStartWave(buttonStartWave) }

        buttonArcher = app.findViewById(R.id.buttonArcher)
        buttonArcher.setOnClickListener { onClickButtonArcher(buttonArcher) }

        buttonCannon = app.findViewById(R.id.buttonCannon)
        buttonCannon.setOnClickListener{ onClickButtonCannon(buttonCannon) }

        buttonFlamethrower = app.findViewById(R.id.buttonFlamethrower)
        buttonFlamethrower.setOnClickListener{ onClickButtonFlamethrower(buttonFlamethrower) }

        buttonEnd = app.findViewById(R.id.buttonEnd)
        buttonEnd.setOnClickListener { onClickButtonEnd(buttonEnd) }
    }

    private fun initStats() {
        textHitPoints = app.findViewById(R.id.textViewHitPoints)
        textMoney = app.findViewById(R.id.textViewMoney)
    }

    private fun initGrid() {
        gridLayoutMap = app.findViewById(R.id.gridLayoutMap)
        gridLayoutMap.columnCount = GameMapUtils.N_COLUMNS
        gridLayoutMap.rowCount = GameMapUtils.N_ROWS
        gridLayoutMap.setOnClickListener { onClickGridLayoutMap(gridLayoutMap) }
    }

    //************************************* Public methods ************************************** //
    fun updateStats(money: Int, hitPoints: Int) {
        textHitPoints.text = hitPoints.toString()
        textMoney.text = money.toString()
    }

    fun drawMap(map : GameMapViewer) {
        gridLayoutMap.removeAllViews()

        for (c in 0 until GameMapUtils.N_COLUMNS) {
            for (r in 0 until GameMapUtils.N_ROWS) {
                if (map.getTileContent(c,r) <= Tiles.EMPTY.value) {
                    // Show grass
                    drawTile(c,r, R.drawable.grass)
                } else {
                    // Show road
                    drawTile(c,r, R.drawable.road)
                }
            }
        }
    }

    fun clearBodies() {
        layoutBodies.removeAllViews()
    }

    fun drawBodies(drawableBodies : List<Body>) {
        for (b in drawableBodies) {
            if (b is Archer) {
                drawBody(b.getRealX(), b.getRealY(), R.drawable.archer, b)
            } else if (b is Cannon){
                drawBody(b.getRealX(), b.getRealY(), R.drawable.cannon, b)
            } else if (b is Flamethrower){
                drawBody(b.getRealX(), b.getRealY(), R.drawable.flame, b)
            } else if (b is Soldier) {
                drawBody(b.getRealX(), b.getRealY(), R.drawable.soldier, b)
            }
        }
    }

    //************************************* event handlers ************************************* //
    fun onClickButtonEnd(view: View) {
        mListener.switchToMenu()
    }

    fun onClickTower(tower: Tower) {
        // !!!SB: afficher upgrade list. Avoir une variable "selected" (pas meme chose que selectedTower)
    }

    fun onClickButtonStartWave(view: View) {
        mListener.startWave()
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
    private fun onClickGridLayoutMap(view: View){
        gridLayoutMap.setOnTouchListener{_, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Get X and Y coordinates of the touch event
                val x = event.x.toInt()
                val y = event.y.toInt()
                val pos: Pair<Int, Int> = GameMapUtils.pixelToGrid(x,y)

                when (selectedTower) {
                    buttonArcher -> {
                        mListener.addTower(pos.first, pos.second, Tiles.ARCHER)
                        toggleTowerButton(buttonArcher)
                    }
                    buttonCannon -> {
                        mListener.addTower(pos.first, pos.second, Tiles.CANNON)
                        toggleTowerButton(buttonCannon)
                    }
                    buttonFlamethrower -> {
                        mListener.addTower(pos.first, pos.second, Tiles.FLAMETHROWER)
                        toggleTowerButton(buttonFlamethrower)
                    }
                }
            }
            // Return false to indicate that we haven't consumed the touch event,
            false
        }
    }

    //************************************* private functions************************************ //
    private fun drawTile(c : Int, r: Int, resId:Int){
        val imageView = ImageView(app)
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

    private fun drawBody(px : Int, py: Int, resId: Int, body: Body) {
        val imageView = ImageView(app)
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
}