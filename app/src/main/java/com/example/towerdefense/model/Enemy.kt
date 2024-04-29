package com.example.towerdefense.model

abstract class Enemy(col: Int, row: Int, spawnTick: Int, hitPoints: Int) : Body(col, row), AttackListener {
    //**************************************** Variables **************************************** //
    protected var mHitPoints: Int = hitPoints
    protected var mLoot: Int = 0

    protected var mSpawnTick: Int = spawnTick
    protected var mIsDead: Boolean = false
    protected var mReachedEnd: Boolean = false

    private var mPreviousDirection : Pair<Int,Int> = Pair(0,1)
    private var mNextDirection : Pair<Int,Int> = Pair(0,1)

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

    override fun getPosX() : Int {
        return super.getRealX()
    }

    override fun getPosY() : Int{
        return super.getRealY()
    }

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
    /**
     * Set the new gridX and gridY position.
     * Should be called each game tick (mainTick)
     */
    protected fun moveGrid() {
        mPreviousDirection = mNextDirection
        setGridX(getGridX() + mNextDirection.first)
        setGridY(getGridY() + mNextDirection.second)

        mNextDirection = getDirection()

        // Reached end: next tile is at dx=0, dy=0
        if (mNextDirection.first == 0 && mNextDirection.second == 0) {
            this.mReachedEnd = true
        }
    }

    /**
     * Set the new realX and realY position.
     * Should be called each display tick (displayTick)
     */
    protected fun moveReal() {
        val d = getRealDirection()

        val offsX = (d.first * GameMapUtils.PX_PER_TILE).toInt()
        val offsY = (d.second * GameMapUtils.PX_PER_TILE).toInt()

        setRealX((GameMapUtils.gridToPixel(getGridX()) + offsX))
        setRealY((GameMapUtils.gridToPixel(getGridY()) + offsY))
    }

    /**
     * Gives the direction of the next movement (real position)
     *
     * return: Pair<realDx, realDy>
     *     where realDx = [-0.5, 0.5]
     *           realDy = [-0.5, 0.5]
     */
    private fun getRealDirection() : Pair<Double, Double> {
        var realDx = 0.0
        var realDy = 0.0

        if (mGameTimerView.isFirstHalf()) {
            // [0 à 0.5] * [0 ou 1] = [-0.5 à 0]
            realDx = ((mGameTimerView.getTickFraction()-0.5) * mPreviousDirection.first)
            realDy = ((mGameTimerView.getTickFraction()-0.5) * mPreviousDirection.second)
        } else {
            // [0.5 à 1] * [0 ou 1] = [0 à 0.5]
            realDx = ((mGameTimerView.getTickFraction()-0.5) * mNextDirection.first)
            realDy = ((mGameTimerView.getTickFraction()-0.5) * mNextDirection.second)
        }

        return Pair(realDx, realDy)
    }

    /**
     * Gives the direction of the next movement (grid position)
     *
     * return: Pair<dx, dy>
     *     where dx = -1, 0 or 1
     *           dy = -1, 0 or 1
     *
     *           returns (0,0) when no path found = end reached
     */
    private fun getDirection(): Pair<Int,Int> {
        var tileValue : Int
        var maxTileValue = mGameMapView.getTileContent(getGridX(), getGridY())

        var nextX = 0
        var nextY = 0

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

        // if current tile > nextTile = fin de la trajectoire
        // appeler équivalent de isDead pour isDone genre
        return Pair(nextX, nextY)
    }
}