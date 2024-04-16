package com.example.towerdefense.model

class Game {
    //**************************************** Variables **************************************** //
    private var mCurrentWave = 0
    private var mTickDuration : Long = 500
    private var mMoney : Int = 1000
    private var mHitPoints : Int = 200

    private val mGameManager: GameManager = GameManager()
    private var mGameTimer : GameTimer? = null
    private val mGameMap : GameMap = GameMap()

    //*************************************** Constructor *************************************** //
    init {
        initGameTimer()
        MapViewer.linkMap(mGameMap)
    }

    private fun initGameTimer() {
        mGameTimer = GameTimer(mTickDuration)
        mGameTimer?.setTickListener { this.advanceTick() }
    }

    //************************************* Public methods ************************************* //
    fun startWave() {
        // !!!SB: Appeler la fonction à partir d'eventHandler buttons. À compléter

        if (mGameTimer?.isRunning() == false) {
            if (mCurrentWave < mGameMap.nWave) {
                mGameManager.setPendingEnemies(MapViewer.getWaveEnemies(mCurrentWave))
                mGameTimer?.start()
            }
        }
    }

    fun pauseWave() {
        // !!!SB: Appeler la fonction à partir d'eventHandler buttons. À compléter
        mGameTimer?.stop()
    }

    fun resumeWave() {
        // !!!SB: Appeler la fonction à partir d'eventHandler buttons. À compléter
        mGameTimer?.start()
    }

    fun endGame() {
        // !!!SB: Appeler la fonction à partir d'eventHandler buttons. À compléter
        mGameTimer?.stop()
    }

    fun addTower(col : Int, row : Int, towerType: Tiles) {
        // !!!SB: Ajouté à partir du main
        if (MapViewer.isEmptyTile(col, row)) {
            when (towerType) {
                Tiles.ARCHER -> {
                    mGameManager.addTowerToList(Archer(col, row))
                    mGameMap.setTileContent(col, row, towerType.value)
                }
                Tiles.CANNON -> {
                    mGameManager.addTowerToList(Cannon(col, row))
                    mGameMap.setTileContent(col, row, towerType.value)
                }
                Tiles.FLAMETHROWER -> {
                    mGameManager.addTowerToList(Flamethrower(col, row))
                    mGameMap.setTileContent(col, row, towerType.value)
                }
                else -> { }
            }
        }
    }

    fun getDrawableBodies() : List<Body> {
        val r : MutableList<Body> = mGameManager.getTowers().toMutableList()
        r.addAll(mGameManager.getActiveEnemies())
        
        return r.toList()
    }

    //************************************* Private methods ************************************* //
    private fun advanceTick() {
        mGameManager.advanceTick()

        mMoney += mGameManager.getMoneyToAdd()
        mHitPoints -= mGameManager.getHitPointsToRemove()
    }
}
