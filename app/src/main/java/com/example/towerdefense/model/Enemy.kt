package com.example.towerdefense.model

abstract class Enemy(col: Int, row: Int, spawnTick: Int) : Body(col, row), AttackEventListener {
    //**************************************** Variables **************************************** //
    protected var mHitPoints: Int = 0
    protected var mLoot: Int = 0

    protected var mSpawnTick: Int = spawnTick
    protected var mIsDead: Boolean = false
    protected var mReachedEnd: Boolean = false

    //*************************************** Constructor *************************************** //

    //************************************* Public methods ************************************* //
    fun getHitPoints(): Int {
        return mHitPoints; }

    fun getLoot(): Int {
        return mLoot; }

    fun getIsDead(): Boolean {
        return mIsDead; }

    fun hasReachedEnd(): Boolean {
        return mReachedEnd; }

    fun getSpawnTick(): Int {
        return mSpawnTick; }

    fun decrementSpawnTick() {
        mSpawnTick--; }

    override fun onAttack(damage: Int) {
        mHitPoints -= damage

        if (mHitPoints <= 0) {
            mIsDead = true
        }
    }

    override fun advanceTick() {
        move()
    }

    //************************************* Private methods ************************************* //
    protected fun move() {
        val nextTile = getNextTile()

        mGridX = nextTile.first
        mGridY = nextTile.second
    }

    private fun getNextTile(): Pair<Int,Int> {
        var tileValue : Int
        var maxTileValue = 0

        var nextX = mGridX
        var nextY = mGridY

        // Haut, gauche, bas, droite
        val neighbors4 = listOf(0 to -1, -1 to 0, 0 to 1, 1 to 0)

        for ((dx, dy) in neighbors4) {
            if (GameMap.isValidCol(mGridX + dx) && GameMap.isValidRow(mGridY + dy)) {
                if (MapViewer.isRoad(mGridX + dx, mGridY + dy)) {
                    tileValue = MapViewer.getTileContent(mGridX + dx, mGridY + dy)

                    if (tileValue > maxTileValue) {
                        maxTileValue = tileValue
                        nextX = dx
                        nextY = dy
                    }
                }
            }
        }

        return Pair(mGridX + nextX, mGridY + nextY)
    }
}