package com.example.towerdefense.model

class Boss(col:Int, row: Int, spawnTick: Int, hitPoints: Int) : Enemy(col, row, spawnTick, hitPoints) {
    //**************************************** Variables **************************************** //

    //*************************************** Constructor *************************************** //
    init {
        mHitPoints = hitPoints
        mLoot= 100*mHitPoints
    }
    //************************************* Public methods ************************************** //

    override fun advanceMainTick() {
        // !!!SB: Implementer
    }

    override fun advanceDisplayTick() {
        // !!!SB: Implementer
        super.advanceDisplayTick()
    }

    //************************************* Private methods ************************************* //
}