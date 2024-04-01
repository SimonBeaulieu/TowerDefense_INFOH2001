package com.example.towerdefense.model

class Game {
    //**************************************** Variables **************************************** //
    private var _currentWave = 0
    private var _tickDuration : Long = 500
    private var _money : Int = 1000
    private var _hitPoints : Int = 200

    private val _gameManager: GameManager = GameManager()
    private var _gameTimer : GameTimer? = null
    private val _gameMap : GameMap = GameMap()

    //*************************************** Constructor *************************************** //
    init {
        initGameTimer()
        MapViewer.linkMap(_gameMap)
    }

    private fun initGameTimer() {
        _gameTimer = GameTimer(_tickDuration)
        _gameTimer?.setTickListener { this.advanceTick() }
    }

    //************************************* Public methods ************************************* //
    fun startWave() {
        // !!!SB: Appeler la fonction à partir d'eventHandler buttons. À compléter

        // !!!KC: Changer pour accéder au singleton (nWave)
        if (_currentWave < _gameMap.nWave) {
            _gameManager.setPendingEnemies(MapViewer.getWaveEnemies(_currentWave))
            _gameTimer?.start()
        }
    }

    fun pauseWave() {
        // !!!SB: Appeler la fonction à partir d'eventHandler buttons. À compléter
        _gameTimer?.stop()
    }

    fun resumeWave() {
        // !!!SB: Appeler la fonction à partir d'eventHandler buttons. À compléter
        _gameTimer?.start()
    }

    fun endGame() {
        // !!!SB: Appeler la fonction à partir d'eventHandler buttons. À compléter
        _gameTimer?.stop()
    }

    fun addTower(col : Int, row : Int, towerType: Tiles) {
        // !!!SB: Ajouté à partir du main
        if (MapViewer.isEmptyTile(col, row)) {
            when (towerType) {
                Tiles.ARCHER -> {
                    _gameManager.addTowerToList(Archer(col, row))
                    _gameMap.setTileContent(col, row, towerType.value)
                }
                Tiles.CANNON -> {
                    _gameManager.addTowerToList(Cannon(col, row))
                    _gameMap.setTileContent(col, row, towerType.value)
                }
                Tiles.FLAMETHROWER -> {
                    _gameManager.addTowerToList(Flamethrower(col, row))
                    _gameMap.setTileContent(col, row, towerType.value)
                }
                else -> { }
            }
        }
    }

    //************************************* Private methods ************************************* //
    private fun advanceTick() {
        _gameManager.advanceTick()

        _money += _gameManager.getMoneyToAdd()
        _hitPoints -= _gameManager.getHitPointsToRemove()
    }
}
