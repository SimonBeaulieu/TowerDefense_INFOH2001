package com.example.towerdefense.model

class GameManager {

    //**************************************** Variables **************************************** //
    private var mMoneyToAdd : Int = 0
    private var mHitPointsToRemove : Int = 0

    private val mTowers : MutableList<Tower> = mutableListOf()
    private var mActiveEnemies : MutableList<Enemy> = mutableListOf()
    private var mPendingEnemies : MutableList<Enemy> = mutableListOf()

    //*************************************** Constructor *************************************** //


    //************************************* Public methods ************************************** //
    /**
     * Note: order of listAdvance is important. Towers before Enemies. First thing Enemies will
     * check is if they got killed during this turn.
     */
    fun advanceTick() {
        mMoneyToAdd = 0
        mHitPointsToRemove = 0

        advanceTowers()
        advanceActiveEnemies()
        advancePendingEnemies()
    }

    fun getMoneyToAdd() : Int { return this.mMoneyToAdd }

    fun getHitPointsToRemove() : Int { return this.mHitPointsToRemove }

    fun setPendingEnemies(l : List<Enemy>) { this.mPendingEnemies = l.toMutableList() }

    fun addTowerToList(t: Tower) { this.mTowers.add(t) }

    fun getTowers() : List<Tower> { return this.mTowers }

    fun getActiveEnemies() : List<Enemy> { return this.mActiveEnemies }

    //************************************* Private methods ************************************* //
    /**
     * Advance 1 turn for every towers
     */
    private fun advanceTowers() {
        val towerIterator = mTowers.iterator()
        var t : Tower

        while (towerIterator.hasNext()){
            t = towerIterator.next()
            t.advanceTick()
        }
    }

    /**
     * Advance 1 turn for every active enemies
     *
     * Must be removed if is dead or has reached the end. Otherwise advance a turn
     */
    private fun advanceActiveEnemies() {
        val enemyIterator = mActiveEnemies.iterator()
        var e : Enemy

        while (enemyIterator.hasNext()){
            e = enemyIterator.next()

            if (e.getIsDead()) {
                mMoneyToAdd += e.getLoot()
                enemyIterator.remove()

            } else if (e.hasReachedEnd()) {
                mHitPointsToRemove += e.getHitPoints()
                enemyIterator.remove()

            } else {
                e.advanceTick()
            }
        }
    }

    /**
     * Advance 1 turn for every pending ennemies
     *
     * If pending enemy must join the map this tick, remove it from pending and add it to active.
     * Otherwise, decrement spawn tick of 1
     */
    private fun advancePendingEnemies() {
        val enemyIterator = mPendingEnemies.iterator()
        var e : Enemy

        while (enemyIterator.hasNext()) {
            e = enemyIterator.next()

            if (e.getSpawnTick() == 0) {
                val t = MapViewer.getFirstTile()
                e.mGridX = t.first
                e.mGridY = t.second

                mActiveEnemies.add(e)
                enemyIterator.remove()

            } else {
                e.decrementSpawnTick()
            }
        }
    }
}