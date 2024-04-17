package com.example.towerdefense.model

class Game {
    //**************************************** Variables **************************************** //
    private var mCurrentWave = 0
    private var mMainTickDuration : Long = 500
    private var mMoney : Int = 1000
    private var mHitPoints : Int = 200

    private val mGameMap : GameMap
    private var mGameManager: GameManager
    private var mGameTimer : GameTimer? = null

    //*************************************** Constructor *************************************** //
    init {
        // Create references
        initGameTimer()
        mGameMap = GameMap()

        // Register references (timer and map)
        initReferences()

        // Create objects who requires references
        mGameMap.initWaves()
        mGameManager = GameManager()
    }

    private fun initReferences() {
        // Reference à la carte en mode read only
        References.addRef(mGameMap, GameMapViewer::class.java)

        // Reference au timer en mode read only
        if (mGameTimer != null) {
            References.addRef(mGameTimer as GameTimer, GameTimerViewer::class.java)
        }
    }

    private fun initGameTimer() {
        mGameTimer = GameTimer(mMainTickDuration)

        mGameTimer?.setMainTickListener { advanceTick() }
        mGameTimer?.setDisplayTickListener { advanceDisplayTick() }
    }

    //************************************* Public methods ************************************* //
    fun startWave() {
        if (mGameTimer?.isRunning() == false) {
            if (mCurrentWave < mGameMap.nWave) {
                mGameManager.setPendingEnemies(mGameMap.getWaveEnemies(mCurrentWave))
                mGameTimer?.startMain()
            }
        }
    }

    fun pauseWave() {
        // !!!SB: Appeler la fonction à partir d'eventHandler buttons. À compléter
        mGameTimer?.stopMain()
    }

    fun resumeWave() {
        // !!!SB: Appeler la fonction à partir d'eventHandler buttons. À compléter
        mGameTimer?.start()
    }

    fun endGame() {
        // !!!SB: Appeler la fonction à partir d'eventHandler buttons. À compléter
        mGameTimer?.stopMain()
    }

    fun addTower(col : Int, row : Int, towerType: Tiles) {
        // !!!SB: Ajouté à partir du main
        if (mGameMap.isEmptyTile(col, row)) {
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

    fun startDisplayOnly() {
        mGameTimer?.stopMain()
        mGameTimer?.start()
    }

    //************************************* Private methods ************************************* //
    private fun advanceTick() {
        mGameManager.advanceMainTick()

        mMoney += mGameManager.getMoneyToAdd()
        mHitPoints -= mGameManager.getHitPointsToRemove()
    }

    private fun advanceDisplayTick() {
        mGameManager.advanceDisplayTick()
    }
}
