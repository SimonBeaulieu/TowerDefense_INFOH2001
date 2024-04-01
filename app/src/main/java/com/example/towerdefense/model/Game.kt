package com.example.towerdefense.model

class Game {
    //**************************************** Variables **************************************** //
    private var currentWave = 0
    private var tickDuration : Long = 500
    private var money : Int = 1000
    private var hitPoints : Int = 200

    private val gameManager: GameManager = GameManager()
    private var gameTimer : GameTimer? = null
    private val gameMap : GameMap = GameMap()

    //*************************************** Constructor *************************************** //
    init {
        initGameTimer()
        MapViewer.linkMap(gameMap)
    }

    private fun initGameTimer() {
        gameTimer = GameTimer(tickDuration)
        gameTimer?.setTickListener { this.advanceTick() }
    }

    //************************************* Public methods ************************************* //
    fun startWave() {
        // !!!SB: Appeler la fonction à partir d'eventHandler buttons. À compléter

        //!!!KC: Changer pour accéder au singleton (nWave)
        if (currentWave < gameMap.nWave) {
            gameManager.setPendingEnemies(MapViewer.getWaveEnemies(currentWave))
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
