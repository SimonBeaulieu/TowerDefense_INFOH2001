package com.example.towerdefense.model

class Game {
    //**************************************** Variables **************************************** //
    private var mCurrentWave = 0
    private var mDisplayTickDuration : Long = 50
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

        // Start timer (enableTicks are set to false)
        mGameTimer?.start()
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
        mGameTimer = GameTimer(mDisplayTickDuration)

        mGameTimer?.setMainTickListener { this.advanceTick() }
        mGameTimer?.setDisplayTickListener { this.advanceDisplayTick() }

        mGameTimer?.enableDisplay = true
    }

    //************************************* Public methods ************************************* //
    fun startWave() {
        if (mCurrentWave < mGameMap.nWave) {
            mGameManager.setPendingEnemies(mGameMap.getWaveEnemies(mCurrentWave))

            mGameTimer?.enableTicks = true
            mGameTimer?.enableDisplay = true
        }
    }

    fun pauseWave() {
        mGameTimer?.enableTicks = false
        mGameTimer?.enableDisplay = true
    }

    fun resumeWave() {
        mGameTimer?.enableTicks = true
        mGameTimer?.enableDisplay = true
    }

    fun endGame() {
        mGameTimer?.enableTicks = false
        mGameTimer?.enableDisplay = false
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
