package com.example.towerdefense.view

import CircleView
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
import com.example.towerdefense.model.Boss
import com.example.towerdefense.model.Cannon
import com.example.towerdefense.model.Flamethrower
import com.example.towerdefense.model.GameMapUtils
import com.example.towerdefense.model.GameMapViewer
import com.example.towerdefense.model.Projectile
import com.example.towerdefense.model.ProjectileType
import com.example.towerdefense.model.Soldier
import com.example.towerdefense.model.Tiles
import com.example.towerdefense.model.Tower

class GameView(private val app : AppCompatActivity, private val mController: GameControllerListener) {
    //**************************************** Variables **************************************** //
    lateinit var layoutCharacters : LinearLayout
    private lateinit var gridLayoutMap : androidx.gridlayout.widget.GridLayout
    private lateinit var layoutBodies : FrameLayout
    private lateinit var layoutGameOver : LinearLayout

    private lateinit var textHitPoints : TextView
    private lateinit var textMoney : TextView

    private lateinit var textCost : TextView
    private lateinit var textLevel : TextView
    private lateinit var textSelection : TextView
    private lateinit var textWave : TextView

    private lateinit var buttonStart : ImageButton
    private lateinit var buttonPause : ImageButton
    private lateinit var buttonFast : ImageButton
    private lateinit var buttonQuit : ImageButton

    private lateinit var buttonUpgrade : Button
    private lateinit var buttonMainMenu : Button

    private lateinit var buttonArcher : ImageButton
    private lateinit var buttonCannon : ImageButton
    private lateinit var buttonFlamethrower : ImageButton

    private var selectedBuyable : ImageView? = null
    private var selectedTower : Tower? = null

    private val drawableBodies : MutableList<DrawableBody> = mutableListOf()
    private var fastButtonClicked = false

    private var disableUI : Boolean = false
    private var isPaused : Boolean = false

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
        layoutGameOver = app.findViewById(R.id.layoutGameOver)
    }

    private fun initButtons() {
        buttonArcher = app.findViewById(R.id.buttonArcher)
        buttonArcher.setOnClickListener { onClickButtonArcher(buttonArcher) }
        buttonArcher.setBackgroundColor(Color.LTGRAY)

        buttonCannon = app.findViewById(R.id.buttonCannon)
        buttonCannon.setOnClickListener{ onClickButtonCannon(buttonCannon) }
        buttonCannon.setBackgroundColor(Color.LTGRAY)

        buttonFlamethrower = app.findViewById(R.id.buttonFlamethrower)
        buttonFlamethrower.setOnClickListener{ onClickButtonFlamethrower(buttonFlamethrower) }
        buttonFlamethrower.setBackgroundColor(Color.LTGRAY)

        buttonStart = app.findViewById(R.id.buttonStart)
        buttonStart.setOnClickListener { onClickButtonStart(buttonStart) }
        buttonStart.setBackgroundColor(Color.LTGRAY)

        buttonPause = app.findViewById(R.id.buttonPause)
        buttonPause.setOnClickListener { onClickPause(buttonPause) }
        buttonPause.setBackgroundColor(Color.LTGRAY)

        buttonFast = app.findViewById(R.id.buttonFast)
        buttonFast.setOnClickListener { onClickFast(buttonFast) }
        buttonFast.setBackgroundColor(Color.LTGRAY)

        buttonUpgrade = app.findViewById(R.id.buttonUpgrade)
        buttonUpgrade.setOnClickListener{ onClickUpgrade(buttonUpgrade) }

        buttonMainMenu = app.findViewById(R.id.buttonMainMenu)
        buttonMainMenu.setOnClickListener{ onClickMainMenu(buttonMainMenu) }

        buttonQuit = app.findViewById(R.id.buttonQuit)
        buttonQuit.setOnClickListener{ onClickQuit(buttonQuit) }
        buttonQuit.setBackgroundColor(Color.LTGRAY)
    }

    private fun initStats() {
        textHitPoints = app.findViewById(R.id.textViewHitPoints)
        textMoney = app.findViewById(R.id.textViewMoney)
        textWave = app.findViewById(R.id.textWave)

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
    fun updateStats(money: Int, hitPoints: Int, wave: Int) {
        textHitPoints.text = hitPoints.toString()
        textMoney.text = money.toString()
        textWave.text = "Wave: " + wave.toString()
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
        gridLayoutMap.addView(layoutBodies)
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

    private fun createDrawableProjectile(p: Projectile):DrawableBody {
        val circleView = CircleView(app)

        when(p.getType()){
            ProjectileType.ARCHER_PROJECTILE -> {
                circleView.setCircleAttributes(p.getRealX(), p.getRealY(), p.getRadius(), Color.RED)
            }
            ProjectileType.CANNON_PROJECTILE -> {

            }
            ProjectileType.FLAMETHROWER_PROJECTILE -> {

            }
            else -> {}
        }
        layoutBodies.addView(circleView)
        return DrawableBody(p,circleView)
    }

    fun showGameOver() {
        layoutGameOver.visibility = View.VISIBLE
        disableUI = true
    }

    //************************************* event handlers ************************************* //
    @SuppressLint("SetTextI18n")
    fun onClickTower(view : View, tower : Tower) {
        if (!disableUI) {
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
    }

    private fun onClickUpgrade(view: View) {
        if (!disableUI && selectedTower != null) {
            mController.upgradeTower(selectedTower!!)
            selectedTower = null
            textSelection.text = "Selection: "
            textLevel.text = "Level: "
            textCost.text = "Cost: "
            hideTowerStats()
        }
    }

    fun onClickButtonStart(view: View) {
        if (!disableUI){
            mController.startWave()
        }
    }

    fun onClickPause(view: View) {
        if (!disableUI) {
            if (isPaused) {
                mController.resumeGame()
                buttonPause.setBackgroundColor(Color.LTGRAY)
            } else {
                mController.pauseGame()
                buttonPause.setBackgroundColor(Color.GREEN)
            }

            isPaused = !isPaused
        }
    }

    fun onClickMainMenu(view : View) {
        mController.switchToMenu()
        layoutGameOver.visibility = View.INVISIBLE
        disableUI = false
    }

    fun onClickQuit(view : View) {
        showGameOver()
        disableUI = true
    }

    fun onClickFast(view: View) {
        if (!disableUI) {
            mController.toggleSpeed()
            fastButtonClicked = !fastButtonClicked

            if (fastButtonClicked) {
                buttonFast.setBackgroundColor(Color.GREEN)
            } else {
                buttonFast.setBackgroundColor(Color.LTGRAY)
            }
        }
    }

    fun onClickButtonEnd(view: View) {
        if (!disableUI) {
        }
    }

    @SuppressLint("SetTextI18n")
    fun onClickButtonArcher(view: View) {
        if (!disableUI) {
            toggleTowerButton(buttonArcher)

            textCost.text = "Cost: " + Archer(0, 0).getCost().toString()
            textSelection.text = "Selection: Archer"
            textLevel.text = "Level: 1"
        }
    }

    @SuppressLint("SetTextI18n")
    fun onClickButtonCannon(view: View){
        if (!disableUI) {
            toggleTowerButton(buttonCannon)
            textCost.text = "Cost: " + Cannon(0, 0).getCost().toString()
            textSelection.text = "Selection: Cannon"
            textLevel.text = "Level: 1"
        }
    }

    @SuppressLint("SetTextI18n")
    fun onClickButtonFlamethrower(view: View){
        if (!disableUI) {
            toggleTowerButton(buttonFlamethrower)
            textCost.text = "Cost: " + Flamethrower(0, 0).getCost().toString()
            textSelection.text = "Selection: Flamethrower"
            textLevel.text = "Level: 1"
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onClickGridLayoutMap(view: View){
        if (!disableUI) {
            gridLayoutMap.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    // Get X and Y coordinates of the touch event
                    val x = event.x.toInt()
                    val y = event.y.toInt()
                    val pos: Pair<Int, Int> = GameMapUtils.pixelToGrid(x, y)

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
        if (body is Projectile){
            var p = body as Projectile

            if(p.isVisible()){
                return createDrawableProjectile(p)
            }
        } else{
            imageView.setImageResource(getImageId(body))

            if (body is Tower) {
                imageView.setOnClickListener { onClickTower(imageView, body) }
            }

            layoutBodies.addView(imageView)
        }

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
        } /*else if (body is Boss){
            //drawBody(b.getRealX(), b.getRealY(), R.drawable.boss, b)
            return 0
        } */
        else {
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

    fun unselectAll() {
        unselectTowers()
        buttonPause.setBackgroundColor(Color.LTGRAY)
        buttonFast.setBackgroundColor(Color.LTGRAY)
    }
}