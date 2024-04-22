package com.example.towerdefense.controller

import android.os.Handler
import android.os.Looper
import com.example.towerdefense.MainActivity
import com.example.towerdefense.model.Game
import com.example.towerdefense.model.GameMapUtils
import com.example.towerdefense.model.GameMapViewer
import com.example.towerdefense.model.References
import com.example.towerdefense.model.Tiles
import com.example.towerdefense.view.GameView

class GameController(private val app: MainActivity) : GameControllerListener {
    //**************************************** Variables **************************************** //
    private var mGame : Game = Game()
    private var mView : GameView = GameView(app, this as GameControllerListener)
    private val mGameMapViewer = References.getRef(GameMapViewer::class.java)

    private val handler = Handler(Looper.getMainLooper())
    var enableDisplay = false

    //*************************************** Constructor *************************************** //
    init {
        setTilesDimensions()
        mView.updateStats(mGame.getMoney(), mGame.getHitPoints())
        mView.drawMap(mGameMapViewer)
        launchDrawingThread()
    }

    //************************************* Public methods ************************************** //
    override fun startWave() {
        mGame.startWave()
    }

    override fun addTower(col: Int, row: Int, towerType: Tiles) {
        mGame.addTower(col, row, towerType)
    }

    override fun switchToMenu() {
        app.showMenu()
    }

    fun pauseGame() {
        mGame.pauseWave()
        enableDisplay = false
    }

    fun resumeGame() {
        mGame.resumeWave()
        enableDisplay = true
    }

    fun launchDrawingThread() {
        Thread {
            while (true) {
                if (enableDisplay) {
                    handler.post {
                        mView.updateStats(mGame.getMoney(), mGame.getHitPoints())
                        mView.clearBodies()
                        mView.drawBodies(mGame.getDrawableBodies())
                    }
                    Thread.sleep(50)
                }
            }
        }.start()
    }

    //************************************* Private methods ************************************* //
    private fun setTilesDimensions() {
        val screenWidth = app.resources.displayMetrics.widthPixels - mView.layoutCharacters.layoutParams.width
        val screenHeight = app.resources.displayMetrics.heightPixels

        val cellWidth = screenWidth / GameMapUtils.N_COLUMNS
        val cellHeight = screenHeight / GameMapUtils.N_ROWS

        GameMapUtils.PX_PER_TILE = minOf(cellWidth, cellHeight)
    }
}