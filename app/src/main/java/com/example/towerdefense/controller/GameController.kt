package com.example.towerdefense.controller

import android.os.Handler
import android.os.Looper
import com.example.towerdefense.model.Game
import com.example.towerdefense.model.GameMapUtils
import com.example.towerdefense.model.service.GameMapReadService
import com.example.towerdefense.model.service.ServiceLocator
import com.example.towerdefense.model.Tiles
import com.example.towerdefense.model.Tower
import com.example.towerdefense.view.GameView

class GameController(private val app: MainActivity) : GameViewListener {
    //**************************************** Variables **************************************** //
    private var mGame : Game = Game()
    private var mView : GameView = GameView(app, this)

    private var mDisplayThreadEnable = false

    //*************************************** Constructor *************************************** //
    init {
        val mGameMapViewer : GameMapReadService = ServiceLocator.getService(GameMapReadService::class.java) as GameMapReadService

        setTilesDimensions()
        mView.updateStats(mGame.getMoney(), mGame.getHitPoints(), mGame.getWaveNum())
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
        mDisplayThreadEnable = false
    }

    override fun resumeGame() {
        mGame.resumeWave()
        mDisplayThreadEnable = true
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
        val handler = Handler(Looper.getMainLooper())

        Thread {
            while (true) {
                if (mDisplayThreadEnable) {
                    handler.post {
                        mView.updateStats(mGame.getMoney(), mGame.getHitPoints(), mGame.getWaveNum())
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