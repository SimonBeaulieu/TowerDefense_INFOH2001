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

        // !!!KC: Changer pour accéder au singleton (nWave)
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

    fun endGame() {
        // !!!SB: Appeler la fonction à partir d'eventHandler buttons. À compléter
        gameTimer?.stop()
    }

    fun addTower(col : Int, row : Int, towerType: Tiles) {
        // !!!SB: Ajouté à partir du main
        if (MapViewer.isEmptyTile(col, row)) {
            when (towerType) {
                Tiles.ARCHER -> {
                    gameManager.addTowerToList(Archer(col, row))
                    gameMap.setTileContent(col, row, towerType.value)
                }
                Tiles.CANNON -> {
                    gameManager.addTowerToList(Cannon(col, row))
                    gameMap.setTileContent(col, row, towerType.value)
                }
                Tiles.FLAMETHROWER -> {
                    gameManager.addTowerToList(Flamethrower(col, row))
                    gameMap.setTileContent(col, row, towerType.value)
                }
                else -> { }
            }
        }
    }

    //************************************* Private methods ************************************* //
    private fun advanceTick() {
        gameManager.advanceTick()

        money += gameManager.getMoneyToAdd()
        hitPoints -= gameManager.getHitPointsToRemove()
    }
}
