package com.example.towerdefense.model

class Game {
    //**************************************** Variables **************************************** //
    private var currentWave = 1
    private var gameTick : Long = 500
    private var money : Int = 1000
    private var hitPoints : Int = 200

    private val map: Map = Map()
    private var gameTimer : GameTimer? = null
    private val waves: MutableList<Wave> = mutableListOf()

    //*************************************** Constructor *************************************** //
    init {
        initGameTimer()
        initWaves()
    }

    private fun initGameTimer() {
        gameTimer = GameTimer(gameTick)
        gameTimer?.setTickListener { this.advanceTick() }

    }

    private fun initWaves() {
        for (i in 0 until waves.size) {
            waves[i].initWave(i+1)
        }
    }

    //************************************* Public methods ************************************* //
    fun startWave() {
        // !!!SB: Appeler la fonction à partir d'eventHandler buttons. À compléter
        if (currentWave <= waves.size + 1) {
            map.setEnemyList(waves[currentWave-1].getWaveEnemies())
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
        map.advanceTick()

        money += map.getMoneyToAdd()
        hitPoints -= map.getHitPointsToRemove()
    }
}
