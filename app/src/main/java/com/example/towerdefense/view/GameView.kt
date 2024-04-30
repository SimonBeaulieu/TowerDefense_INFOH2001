package com.example.towerdefense.view

import androidx.gridlayout.widget.GridLayout
import android.annotation.SuppressLint
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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

class GameView(private val app : AppCompatActivity, private val mController: GameControllerListener) {
    //**************************************** Variables **************************************** //
    lateinit var layoutCharacters : LinearLayout
    private lateinit var gridLayoutMap : androidx.gridlayout.widget.GridLayout
    private lateinit var layoutBodies : FrameLayout

    private lateinit var textHitPoints : TextView
    private lateinit var textMoney : TextView

    private lateinit var textCost : TextView
    private lateinit var textLevel : TextView
    private lateinit var textSelection : TextView

    private lateinit var buttonStart : ImageButton
    private lateinit var buttonPause : ImageButton
    private lateinit var buttonEnd : ImageButton
    private lateinit var buttonFast : ImageButton
    private lateinit var buttonUpgrade : Button

    private lateinit var buttonArcher : ImageButton
    private lateinit var buttonCannon : ImageButton
    private lateinit var buttonFlamethrower : ImageButton

    private var selectedBuyable : ImageView? = null
    private var selectedTower : Tower? = null

    private val drawableBodies : MutableList<DrawableBody> = mutableListOf()
    private var fastButtonClicked = false

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
        buttonArcher = app.findViewById(R.id.buttonArcher)
        buttonArcher.setOnClickListener { onClickButtonArcher(buttonArcher) }

        buttonCannon = app.findViewById(R.id.buttonCannon)
        buttonCannon.setOnClickListener{ onClickButtonCannon(buttonCannon) }

        buttonFlamethrower = app.findViewById(R.id.buttonFlamethrower)
        buttonFlamethrower.setOnClickListener{ onClickButtonFlamethrower(buttonFlamethrower) }

        buttonStart = app.findViewById(R.id.buttonStart)
        buttonStart.setOnClickListener { onClickButtonStart(buttonStart) }

        buttonPause = app.findViewById(R.id.buttonPause)
        buttonPause.setOnClickListener { onClickPause(buttonPause) }

        //buttonEnd = app.findViewById(R.id.buttonEnd)
        //buttonEnd.setOnClickListener { onClickButtonEnd(buttonEnd) }

        buttonFast = app.findViewById(R.id.buttonFast)
        buttonFast.setOnClickListener { onClickFast(buttonFast) }

        buttonUpgrade = app.findViewById(R.id.buttonUpgrade)
        buttonUpgrade.setOnClickListener{ onClickUpgrade(buttonUpgrade) }
    }

    private fun initStats() {
        textHitPoints = app.findViewById(R.id.textViewHitPoints)
        textMoney = app.findViewById(R.id.textViewMoney)

        textCost = app.findViewById(R.id.textCost)
        textLevel = app.findViewById(R.id.textLevel)
        textSelection = app.findViewById(R.id.textSelection)
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

    fun drawBodies(bodies : List<Body>) {
        val copyBodies : MutableList<Body> = bodies.toMutableList()

        // Add drawable for Body not in Drawable list
        copyBodies.forEach { body ->
            if (!drawableBodies.any { it.getBody() == body }) {
                drawableBodies.add(createDrawableBody(body))
            }
        }

        // Remove drawable not in Body list
        val drawableToRemove = drawableBodies.filterNot { drawableBody ->
            copyBodies.any { it == drawableBody.getBody() }
        }
        drawableBodies.removeAll(drawableToRemove)

        // Remove from view
        for (db in drawableToRemove) {
            layoutBodies.removeView(db.getImage())
        }

        // Update all images
        for (db in drawableBodies) {
            db.updateImage()
        }
    }

    //************************************* event handlers ************************************* //
    @SuppressLint("SetTextI18n")
    fun onClickTower(view : View, tower : Tower) {
        unselectTowers()

        if (selectedTower == tower) {
            selectedTower = null
            hideTowerStats()

        } else {
            selectedTower = tower
            textSelection.text = "Upgrading tower.."
            textCost.text = "Cost: " + tower.getUpgradeCost().toString()
            textLevel.text = "Level: " + tower.getLevel()
            showTowerStats()
        }
    }

    private fun onClickUpgrade(view: View) {
        if (selectedTower != null) {
            mController.upgradeTower(selectedTower!!)
            selectedTower = null
            textSelection.text = "Selection: "
            textLevel.text = "Level: "
            textCost.text = "Cost: "
            hideTowerStats()
        }
    }

    fun onClickButtonStart(view: View) {
        mController.startWave()
    }

    fun onClickPause(view: View) {
        mController.switchToMenu()
    }

    fun onClickFast(view: View) {
        mController.toggleSpeed()
        fastButtonClicked = !fastButtonClicked

        if (fastButtonClicked) {
            buttonFast.setBackgroundColor(Color.GREEN)
        } else {
            buttonFast.setBackgroundColor(Color.LTGRAY)
        }
    }

    fun onClickButtonEnd(view: View) {

    }

    @SuppressLint("SetTextI18n")
    fun onClickButtonArcher(view: View) {
        toggleTowerButton(buttonArcher)

        textCost.text="Cost: "+ Archer(0,0).getCost().toString()
        textSelection.text = "Selection: Archer"
        textLevel.text = "Level: 1"
    }

    @SuppressLint("SetTextI18n")
    fun onClickButtonCannon(view: View){
        toggleTowerButton(buttonCannon)
        textCost.text="Cost: "+ Cannon(0,0).getCost().toString()
        textSelection.text = "Selection: Cannon"
        textLevel.text = "Level: 1"
    }

    @SuppressLint("SetTextI18n")
    fun onClickButtonFlamethrower(view: View){
        toggleTowerButton(buttonFlamethrower)
        textCost.text="Cost: "+ Flamethrower(0,0).getCost().toString()
        textSelection.text = "Selection: Flamethrower"
        textLevel.text = "Level: 1"
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onClickGridLayoutMap(view: View){
        gridLayoutMap.setOnTouchListener{_, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Get X and Y coordinates of the touch event
                val x = event.x.toInt()
                val y = event.y.toInt()
                val pos: Pair<Int, Int> = GameMapUtils.pixelToGrid(x,y)

                when (selectedBuyable) {
                    buttonArcher -> {
                        mController.addTower(pos.first, pos.second, Tiles.ARCHER)
                        toggleTowerButton(buttonArcher)
                    }
                    buttonCannon -> {
                        mController.addTower(pos.first, pos.second, Tiles.CANNON)
                        toggleTowerButton(buttonCannon)
                    }
                    buttonFlamethrower -> {
                        mController.addTower(pos.first, pos.second, Tiles.FLAMETHROWER)
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

    private fun createDrawableBody(body: Body) : DrawableBody {
        val imageView = ImageView(app)
        imageView.setImageResource(getImageId(body))

        if (body is Tower) {
            imageView.setOnClickListener { onClickTower(imageView, body) }
        }

        layoutBodies.addView(imageView)

        return DrawableBody(body, imageView)
    }

    private fun getImageId(body: Body): Int {
        if (body is Archer) {
            return R.drawable.archer
        } else if (body is Cannon){
            return R.drawable.cannon
        } else if (body is Flamethrower){
            return R.drawable.flame
        } else if (body is Soldier) {
            return R.drawable.soldier
        } else {
            return R.drawable.ic_launcher_foreground
        }
    }

    private fun toggleTowerButton(imageButton: ImageButton) {
        unselectTowers()

        if (selectedBuyable == imageButton) {
            selectedBuyable = null
            hideTowerStats()

        } else {
            imageButton.setBackgroundColor(Color.GREEN)
            selectedBuyable = imageButton

            showTowerStats()
        }
    }

    private fun showTowerStats() {
        textCost.visibility = View.VISIBLE
        textSelection.visibility = View.VISIBLE
        textLevel.visibility = View.VISIBLE
    }

    private fun hideTowerStats() {
        textCost.visibility = View.INVISIBLE
        textSelection.visibility = View.INVISIBLE
        textLevel.visibility = View.INVISIBLE
    }

    private fun unselectTowers() {
        buttonArcher.setBackgroundColor(Color.LTGRAY)
        buttonCannon.setBackgroundColor(Color.LTGRAY)
        buttonFlamethrower.setBackgroundColor(Color.LTGRAY)
    }
}