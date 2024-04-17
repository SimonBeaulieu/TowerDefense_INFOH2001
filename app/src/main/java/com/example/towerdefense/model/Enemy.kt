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

    override fun advanceMainTick() {
        moveGrid()
    }

    override fun advanceDisplayTick() {
        moveReal()
    }

    //************************************* Private methods ************************************* //
    protected fun moveGrid() {
        val move = getNextTile()

        setGridX(getGridX() + move.first)
        setGridY(getGridY() + move.second)

        //moveReal()
    }

    protected fun moveReal() {
        val d = getNextPos()

        val offsX = (d.first * GameMapUtils.PX_PER_TILE).toInt()
        val offsY = (d.second * GameMapUtils.PX_PER_TILE).toInt()

        setRealX((GameMapUtils.gridToPixel(getGridX()) + offsX))
        setRealY((GameMapUtils.gridToPixel(getGridY()) + offsY))
    }

    private fun getNextPos() : Pair<Double, Double> {
        val nextTile = getNextTile()

        val dx = ((mGameTimerView.getTickFraction()-0.5) * nextTile.first)
        val dy = ((mGameTimerView.getTickFraction()-0.5) * nextTile.second)

        return Pair(dx, dy)
    }

    private fun getNextTile(): Pair<Int,Int> {
        var tileValue : Int
        var maxTileValue = 0

        var nextX = getGridX()
        var nextY = getGridY()

        // Haut, gauche, bas, droite
        val neighbors4 = listOf(0 to -1, -1 to 0, 0 to 1, 1 to 0)

        for ((dx, dy) in neighbors4) {
            if (GameMapUtils.isValidCol(getGridX() + dx) && GameMapUtils.isValidRow(getGridY() + dy)) {
                if (mGameMapView.isRoadTile(getGridX() + dx, getGridY() + dy)) {
                    tileValue = mGameMapView.getTileContent(getGridX() + dx, getGridY() + dy)

                    if (tileValue > maxTileValue) {
                        maxTileValue = tileValue
                        nextX = dx
                        nextY = dy
                    }
                }
            }
        }

        return Pair(nextX, nextY)
    }
}