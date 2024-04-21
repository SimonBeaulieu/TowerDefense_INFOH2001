package com.example.towerdefense.controller

import android.os.Handler
import android.os.Looper
import com.example.towerdefense.MainActivity
import com.example.towerdefense.R
import com.example.towerdefense.model.Archer
import com.example.towerdefense.model.Cannon
import com.example.towerdefense.model.Flamethrower
import com.example.towerdefense.model.Game
import com.example.towerdefense.model.GameMapUtils
import com.example.towerdefense.model.GameMapViewer
import com.example.towerdefense.model.References
import com.example.towerdefense.model.Soldier
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

    fun showView() {
        mView.drawMap(mGameMapViewer)
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

    fun launchDrawingThread() {
        Thread {
            while (true) {
                if (enableDisplay) {
                    handler.post {
                        mView.clearBodies()
                        mView.updateStats(mGame.getMoney(), mGame.getHitPoints())

                        for (b in mGame.getDrawableBodies()) {
                            if (b is Archer) {
                                mView.drawBody(b.getRealX(), b.getRealY(), R.drawable.archer, b)

                            } else if (b is Cannon){
                                mView.drawBody(b.getRealX(), b.getRealY(), R.drawable.cannon, b)

                            } else if (b is Flamethrower){
                                mView.drawBody(b.getRealX(), b.getRealY(), R.drawable.flame, b)

                            } else if (b is Soldier) {
                                mView.drawBody(b.getRealX(), b.getRealY(), R.drawable.soldier, b)

                            }
                        }
                    }
                    Thread.sleep(50)
                }
            }
        }.start()
    }
}