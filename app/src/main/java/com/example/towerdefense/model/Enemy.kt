package com.example.towerdefense.model

abstract class Enemy : Body(), AttackEventListener {
    //**************************************** Variables **************************************** //
    private var hitPoints : Int = 0
    private var loot : Int = 0

    private var spawnTick : Int = 0
    private var isDead : Boolean = false
    private var reachedEnd : Boolean = false

    //*************************************** Constructor *************************************** //

    //************************************* Public methods ************************************* //
    fun getHitPoints() : Int { return this.hitPoints; }

    fun getLoot() : Int { return this.loot; }

    fun getIsDead() : Boolean { return this.isDead; }

    fun hasReachedEnd() : Boolean { return this.reachedEnd; }

    fun getSpawnTick() : Int { return this.spawnTick; }

    fun decrementSpawnTick() { this.spawnTick--; }

    //************************************* Private methods ************************************* //
}