package com.example.towerdefense.model

import com.example.towerdefense.model.service.GameMapReadService
import com.example.towerdefense.model.service.ServiceLocator

class GameManager {

    //**************************************** Variables **************************************** //
    private var mMoneyToAdd : Int = 0
    private var mHitPointsToRemove : Int = 0
    private var mWaveEnded : Boolean = true

    private val mTowers : MutableList<Tower> = mutableListOf()
    private var mActiveEnemies : MutableList<Enemy> = mutableListOf()
    private var mPendingEnemies : MutableList<Enemy> = mutableListOf()

    private val mGameMapView : GameMapReadService = ServiceLocator.getService(GameMapReadService::class.java) as GameMapReadService

    //*************************************** Constructor *************************************** //


    //************************************* Public methods ************************************** //
    /**
     * Note: order of listAdvance is important. Towers before Enemies. First thing Enemies will
     * check is if they got killed during this turn.
     */
    fun advanceMainTick() {
        mMoneyToAdd = 0
        mHitPointsToRemove = 0

        advanceTowers()
        advanceActiveEnemies()
        advancePendingEnemies()

        if (mActiveEnemies.isEmpty() && mPendingEnemies.isEmpty() && !mWaveEnded) {
            mWaveEnded = true
        }
    }

    fun advanceDisplayTick() {
        val l: MutableList<Body> = mutableListOf()
        l.addAll(mTowers)
        l.addAll(mActiveEnemies)

        //for (t in mTowers){
        //    l.addAll(t.getProjectiles())
        //}

        val it = l.iterator()
        var b : Body

        while (it.hasNext()){
            b = it.next()
            b.advanceDisplayTick()
        }
    }

    fun getMoneyToAdd() : Int { return this.mMoneyToAdd }

    fun getHitPointsToRemove() : Int { return this.mHitPointsToRemove }

    fun setPendingEnemies(l : List<Enemy>) {
        this.mPendingEnemies = l.toMutableList()
        mWaveEnded = false
    }

    fun addTowerToList(t: Tower) { this.mTowers.add(t) }

    fun getTowers() : List<Tower> { return this.mTowers }

    fun getActiveEnemies() : List<Enemy> { return this.mActiveEnemies }

    fun getWaveEnded() : Boolean { return mWaveEnded }

    //************************************* Private methods ************************************* //
    /**
     * Advance 1 turn for every towers
     */
    private fun advanceTowers() {
        val towerIterator = mTowers.iterator()
        var t : Tower

        while (towerIterator.hasNext()){
            t = towerIterator.next()
            t.findTargets(mActiveEnemies.toList() as List<AttackListener>)
            t.advanceMainTick()
        }
    }

    /**
     * Advance 1 turn for every active enemies
     *
     * Must be removed if is dead or has reached the end. Otherwise advance a turn
     */
    private fun advanceActiveEnemies() {
        val damagePerHitPoints = 5

        val enemyIterator = mActiveEnemies.iterator()
        var e : Enemy

        while (enemyIterator.hasNext()){
            e = enemyIterator.next()

            if (e.getIsDead()) {
                mMoneyToAdd += e.getLoot()
                enemyIterator.remove()

            } else if (e.hasReachedEnd()) {
                mHitPointsToRemove += e.getHitPoints() * damagePerHitPoints
                enemyIterator.remove()

            } else {
                e.advanceMainTick()

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
                val t = mGameMapView.getFirstTile()
                e.setGridX(t.first)
                e.setGridY(t.second)

                mActiveEnemies.add(e)
                enemyIterator.remove()

            } else {
                e.decrementSpawnTick()
            }
        }
    }
}