package com.example.towerdefense.model

abstract class Enemy(col: Int, row: Int) : Body(col, row), AttackEventListener {
    //**************************************** Variables **************************************** //
    protected var _hitPoints: Int = 0
    protected var _loot: Int = 0

    protected var _spawnTick: Int = 0
    protected var _isDead: Boolean = false
    protected var _reachedEnd: Boolean = false

    //*************************************** Constructor *************************************** //

    //************************************* Public methods ************************************* //
    fun getHitPoints(): Int {
        return _hitPoints; }

    fun getLoot(): Int {
        return _loot; }

    fun getIsDead(): Boolean {
        return _isDead; }

    fun hasReachedEnd(): Boolean {
        return _reachedEnd; }

    fun getSpawnTick(): Int {
        return _spawnTick; }

    fun decrementSpawnTick() {
        _spawnTick--; }

    override fun onAttack(damage: Int) {
        _hitPoints -= damage

        if (_hitPoints <= 0) {
            _isDead = true
        }
    }

    override fun advanceTick() {
        move()
    }

    //************************************* Private methods ************************************* //
    protected fun move() {
        val nextTile = getNextTile()

        gridX = nextTile.first
        gridY = nextTile.second
    }

    private fun getNextTile(): Pair<Int,Int> {
        var tileValue : Int
        var maxTileValue = 0

        var nextX = 0
        var nextY = 0

        // Haut, gauche, bas, droite
        val neighbors4 = listOf(0 to -1, -1 to 0, 0 to 1, 1 to 0)

        for ((dx, dy) in neighbors4) {
            if (MapViewer.isRoad(gridX+dx, gridY+dy)) {
                tileValue = MapViewer.getTileContent(gridX+dx, gridY+dy)

                if (tileValue > maxTileValue) {
                    maxTileValue = tileValue
                    nextX = dx
                    nextY = dy
                }
            }
        }

        return Pair(nextX, nextY)
    }
}