package com.example.towerdefense.model

class GameManager {

    //**************************************** Variables **************************************** //
    private var moneyToAdd : Int = 0
    private var hitPointsToRemove : Int = 0

    private val towers : MutableList<Tower> = mutableListOf()
    private var activeEnemies : MutableList<Enemy> = mutableListOf()
    private var pendingEnemies : MutableList<Enemy> = mutableListOf()

    //*************************************** Constructor *************************************** //


    //************************************* Public methods ************************************** //
    /**
     * Note: order of listAdvance is important. Towers before Enemies. First thing Enemies will
     * check is if they got killed during this turn.
     */
    fun advanceTick() {
        moneyToAdd = 0
        hitPointsToRemove = 0

        advanceTowers()
        advanceActiveEnemies()
        advancePendingEnemies()
    }

    fun getMoneyToAdd() : Int { return this.moneyToAdd }

    fun getHitPointsToRemove() : Int { return this.hitPointsToRemove }

    fun setPendingEnemies(l : List<Enemy>) { this.pendingEnemies = l.toMutableList() }

    fun addTowerToList(t: Tower) { this.towers.add(t); }

    //************************************* Private methods ************************************* //
    /**
     * Advance 1 turn for every towers
     */
    private fun advanceTowers() {
        val towerIterator = towers.iterator()
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
        val enemyIterator = activeEnemies.iterator()
        var e : Enemy

        while (enemyIterator.hasNext()){
            e = enemyIterator.next()

            if (e.getIsDead()) {
                moneyToAdd += e.getLoot()
                enemyIterator.remove()

            } else if (e.hasReachedEnd()) {
                hitPointsToRemove += e.getHitPoints()
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
        val enemyIterator = pendingEnemies.iterator()
        var e : Enemy

        while (enemyIterator.hasNext()) {
            e = enemyIterator.next()

            if (e.getSpawnTick() == 0) {
                activeEnemies.add(e)
                enemyIterator.remove()

            } else {
                e.decrementSpawnTick()
            }
        }
    }
}