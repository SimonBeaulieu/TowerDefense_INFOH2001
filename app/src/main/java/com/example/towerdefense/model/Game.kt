package com.example.towerdefense.model

class Game {
    //**************************************** Variables **************************************** //
    private var currentWave = 0
    private var tickDuration : Long = 500
    private var money : Int = 1000
    private var hitPoints : Int = 200
    private val nWave = 12

    private val gameManager: GameManager = GameManager()
    private var gameTimer : GameTimer? = null
    private val waves: MutableList<Wave> = mutableListOf()

    //*************************************** Constructor *************************************** //
    init {
        initGameTimer()
        initWaves()
    }

    private fun initGameTimer() {
        gameTimer = GameTimer(tickDuration)
        gameTimer?.setTickListener { this.advanceTick() }
    }

    private fun initWaves() {
        for (i in 0 until nWave)
            waves.add(Wave(i))
    }

    //************************************* Public methods ************************************* //
    fun startWave() {
        // !!!SB: Appeler la fonction à partir d'eventHandler buttons. À compléter
        if (currentWave < nWave) {
            gameManager.setPendingEnemies(waves[currentWave].getWaveEnemies())
            gameTimer?.start()
        }
    }

    fun pauseWave() {
        // !!!SB: Appeler la fonction à partir d'eventHandler buttons. À compléter
        gameTimer?.stop()
    }

    fun resumeWave() {
        // !!!SB: Appeler la fonction à partir d'eventHandler buttons. À compléter
        gameTimer?.start()
    }

    fun endWave() {
        // !!!SB: Appeler la fonction à partir d'eventHandler buttons. À compléter
        gameTimer?.stop()
    }

    //************************************* Private methods ************************************* //
    private fun advanceTick() {
        gameManager.advanceTick()

        money += gameManager.getMoneyToAdd()
        hitPoints -= gameManager.getHitPointsToRemove()
    }
}
