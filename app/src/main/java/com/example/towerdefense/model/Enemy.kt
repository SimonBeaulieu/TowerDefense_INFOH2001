package com.example.towerdefense.model

abstract class Enemy(col: Int, row: Int) : Body(col, row), AttackEventListener {
    //**************************************** Variables **************************************** //
    protected var _hitPoints : Int = 0
    protected var _loot : Int = 0

    protected var _spawnTick : Int = 0
    protected var _isDead : Boolean = false
    protected var _reachedEnd : Boolean = false

    //*************************************** Constructor *************************************** //

    //************************************* Public methods ************************************* //
    fun getHitPoints() : Int { return _hitPoints; }

    fun getLoot() : Int { return _loot; }

    fun getIsDead() : Boolean { return _isDead; }

    fun hasReachedEnd() : Boolean { return _reachedEnd; }

    fun getSpawnTick() : Int { return _spawnTick; }

    fun decrementSpawnTick() { _spawnTick--; }

    override fun onAttack(damage: Int) {
        _hitPoints -= damage

        if (_hitPoints <= 0) {
            _isDead = true
        }
    }
    //************************************* Private methods ************************************* //
}