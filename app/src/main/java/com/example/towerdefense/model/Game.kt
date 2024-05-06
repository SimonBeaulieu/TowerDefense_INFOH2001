package com.example.towerdefense.model

class Game {
    //**************************************** Variables **************************************** //
    private var mCurrentWave : Wave
    private var mWaveNum = 0
    private var mGameOver = false
    private var mDisplayTickDuration : Long = 50

    private var mMoney : Int = 1000
        set(value) {
            if (value >= 99999) {
                field = 99999
            } else if (value <= 0) {
                field = 0
            } else {
                field = value
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

    fun getWaveNum() : Int { return mWaveNum }

    private val mGameMap : GameMap
    private var mGameManager: GameManager
    private var mGameTimer : GameTimer? = null

    //*************************************** Constructor *************************************** //
    init {
        // Create references
        initGameTimer()
        mGameMap = GameMap(1000)

        // Create objects who requires references
        mGameManager = GameManager()

        // Init waves
        mGameMap.initWaves()

        // Get first wave
        mCurrentWave = mGameMap.getWave(mWaveNum)

        // Start timer (enableTicks are set to false)
        mGameTimer?.start()
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
    }

    fun resumeWave() {
        mGameTimer?.enableTicks = true
        mGameTimer?.enableDisplay = true
    }

    fun endGame() {
        mGameTimer?.enableTicks = false
        mGameTimer?.enableDisplay = false

        mCurrentWave.mInProgress = false
    }

    fun addTower(col : Int, row : Int, towerType: Tiles) {
        if (mGameMap.isEmptyTile(col, row)) {
            when (towerType) {
                Tiles.ARCHER -> {
                    val archerTower = Archer(col, row)
                    if(archerTower.getCost()<=this.mMoney){
                        mGameManager.addTower(archerTower)
                        mGameMap.setTileContent(col, row, towerType.value)
                        this.mMoney-=archerTower.getCost()
                    }

                }
                Tiles.CANNON -> {
                    val cannonTower = Cannon(col, row)
                    if(cannonTower.getCost()<=this.mMoney) {
                        mGameManager.addTower(cannonTower)
                        mGameMap.setTileContent(col, row, towerType.value)
                        this.mMoney-=cannonTower.getCost()
                    }
                }
                Tiles.FLAMETHROWER -> {
                    val flameThrowerTower = Flamethrower(col, row)
                    if(flameThrowerTower.getCost()<=this.mMoney) {
                        mGameManager.addTower(flameThrowerTower)
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

        for (t in mGameManager.getTowers()){
            r.addAll(t.getProjectiles())
        }

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
        if (mMoney >= tower.getUpgradeCost() && !tower.isMaxLevel()) {
            mMoney -= tower.getUpgradeCost()
            tower.upgrade()
        }
    }
}
