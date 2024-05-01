package com.example.towerdefense.controller

import android.os.Handler
import android.os.Looper
import com.example.towerdefense.MainActivity
import com.example.towerdefense.model.Game
import com.example.towerdefense.model.GameMapUtils
import com.example.towerdefense.model.GameMapViewer
import com.example.towerdefense.model.References
import com.example.towerdefense.model.Tiles
import com.example.towerdefense.model.Tower
import com.example.towerdefense.view.GameView

class GameController(private val app: MainActivity) : GameControllerListener {
    //**************************************** Variables **************************************** //
    private var mGame : Game = Game()
    private var mView : GameView = GameView(app, this)

    private val mGameMapViewer = References.getRef(GameMapViewer::class.java)

    private val handler = Handler(Looper.getMainLooper())
    var enableDisplay = false

    //*************************************** Constructor *************************************** //
    init {
        setTilesDimensions()
        mView.updateStats(mGame.getMoney(), mGame.getHitPoints(), mGame.getWave()+1)
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
        // New game for next time
        mGame = Game()
        mView.unselectAll()
        app.showMenu()
    }

    override fun toggleSpeed() {
        mGame.toggleSpeed()
    }

    override fun upgradeTower(tower: Tower) {
        mGame.upgradeTower(tower)
    }

    override fun pauseGame() {
        mGame.pauseWave()
        enableDisplay = false
    }

    override fun resumeGame() {
        mGame.resumeWave()
        enableDisplay = true
    }

    //************************************* Private methods ************************************* //
    private fun setTilesDimensions() {
        val screenWidth = app.resources.displayMetrics.widthPixels - mView.layoutCharacters.layoutParams.width
        val screenHeight = app.resources.displayMetrics.heightPixels

        val cellWidth = screenWidth / GameMapUtils.N_COLUMNS
        val cellHeight = screenHeight / GameMapUtils.N_ROWS

        GameMapUtils.PX_PER_TILE = minOf(cellWidth, cellHeight)
    }

    private fun launchDrawingThread() {
        Thread {
            while (true) {
                if (enableDisplay) {
                    handler.post {
                        mView.updateStats(mGame.getMoney(), mGame.getHitPoints(), mGame.getWave()+1)
                        mView.drawBodies(mGame.getDrawableBodies())

                        if (mGame.isGameOver()) {
                            mView.showGameOver()
                        }
                    }
                    Thread.sleep(50)
                }
            }
        }.start()
    }
}