package com.example.towerdefense.model

abstract class Enemy(col: Int, row: Int) : Body(col, row), AttackEventListener {
    //**************************************** Variables **************************************** //
    protected var hitPoints : Int = 0
    protected var loot : Int = 0

    protected var spawnTick : Int = 0
    protected var isDead : Boolean = false
    protected var reachedEnd : Boolean = false

    //*************************************** Constructor *************************************** //

    //************************************* Public methods ************************************* //
    fun getHitPoints() : Int { return this.hitPoints; }

    fun getLoot() : Int { return this.loot; }

    fun getIsDead() : Boolean { return this.isDead; }

    fun hasReachedEnd() : Boolean { return this.reachedEnd; }

    fun getSpawnTick() : Int { return this.spawnTick; }

    fun decrementSpawnTick() { this.spawnTick--; }

    override fun onAttack(damage: Int) {
        hitPoints -= damage

        if (hitPoints <= 0) {
            isDead = true
        }
    }
    //************************************* Private methods ************************************* //
}