package com.example.towerdefense.model

class Game {
    //**************************************** Variables **************************************** //
    private var mCurrentWave : Wave
    private var mWaveNum = 0
    private var mGameOver = false

    private var mDisplayTickDuration : Long = 50
    private var mMoney : Int = 1000
        set(value) {
            if (value >= 0) {
                field = value
            }
            else{
                field = 0
            }
        }
    fun getMoney(): Int { return mMoney }

    private var mHitPoints : Int = 200
        set(value) {
            if (value >= 0) {
                field = value
            }
            else{
                field = 0
            }
        }
    fun getHitPoints(): Int { return mHitPoints }

    fun getWave() : Int { return mWaveNum }


    private val mGameMap : GameMap
    private var mGameManager: GameManager
    private var mGameTimer : GameTimer? = null

    //*************************************** Constructor *************************************** //
    init {
        // Create references
        initGameTimer()
        mGameMap = GameMap(20)

        // Register references (timer and map)
        initReferences()

        // Create objects who requires references
        mGameMap.initWaves()
        mGameManager = GameManager()
        mCurrentWave = mGameMap.getWave(mWaveNum)

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
        if (mWaveNum <= mGameMap.nWave && !mCurrentWave.mInProgress) {
            setNextWaveEnemies()

            mGameTimer?.enableTicks = true
            mGameTimer?.enableDisplay = true

            mCurrentWave.mInProgress = true
        }
    }

    fun pauseWave() {
        mGameTimer?.enableTicks = false
        mGameTimer?.enableDisplay = true

        // !!!SB: Popup
    }

    fun resumeWave() {
        mGameTimer?.enableTicks = true
        mGameTimer?.enableDisplay = true

        // !!!SB: Remove popup
    }

    fun endGame() {
        mGameTimer?.enableTicks = false
        mGameTimer?.enableDisplay = false

        mCurrentWave.mInProgress = false

        // !!!SB: Popup with gameOver or gaved up
    }

    fun addTower(col : Int, row : Int, towerType: Tiles) {
        // !!!SB: Ajouté à partir du main
        if (mGameMap.isEmptyTile(col, row)) {
            when (towerType) {
                Tiles.ARCHER -> {
                    val archerTower = Archer(col, row)
                    if(archerTower.getCost()<=this.mMoney){
                        mGameManager.addTowerToList(archerTower)
                        mGameMap.setTileContent(col, row, towerType.value)
                        this.mMoney-=archerTower.getCost()
                    }

                }
                Tiles.CANNON -> {
                    val cannonTower = Cannon(col, row)
                    if(cannonTower.getCost()<=this.mMoney) {
                        mGameManager.addTowerToList(cannonTower)
                        mGameMap.setTileContent(col, row, towerType.value)
                        this.mMoney-=cannonTower.getCost()
                    }
                }
                Tiles.FLAMETHROWER -> {
                    val flameThrowerTower : Flamethrower = Flamethrower(col, row)
                    if(flameThrowerTower.getCost()<=this.mMoney) {
                        mGameManager.addTowerToList(flameThrowerTower)
                        mGameMap.setTileContent(col, row, towerType.value)
                        this.mMoney-=flameThrowerTower.getCost()
                    }
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

    fun toggleSpeed() {
        mGameTimer?.toggleSpeed()
    }

    fun isGameOver() : Boolean {
        return mGameOver
    }

    //************************************* Private methods ************************************* //
    private fun advanceTick() {
        mGameManager.advanceMainTick()

        if (mCurrentWave.mInProgress && mGameManager.getWaveEnded()) {
            wavedEnded()
        }

        updateStats()
    }

    private fun advanceDisplayTick() {
        mGameManager.advanceDisplayTick()
    }

    private fun setNextWaveEnemies() {
        mWaveNum++
        mCurrentWave = mGameMap.getWave(mWaveNum-1)
        mGameManager.setPendingEnemies(mCurrentWave.getEnemies())
    }

    private fun wavedEnded(){
        mMoney += mCurrentWave.getCompletionReward()
        mCurrentWave.mInProgress = false
    }

    private fun updateStats() {
        mMoney += mGameManager.getMoneyToAdd()
        mHitPoints -= mGameManager.getHitPointsToRemove()

        if (mHitPoints <= 0 ) {
            mGameOver = true
            endGame()
        }
    }

    fun upgradeTower(tower: Tower) {
        if (mMoney >= tower.getUpgradeCost()) {
            tower.upgrade()
            mMoney -= tower.getUpgradeCost()
        }
    }
}
