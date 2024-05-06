package com.example.towerdefense.model

class Soldier(col: Int, row: Int, spawnTick: Int, hitPoints: Int) : Enemy(col, row, spawnTick, hitPoints) {
    //**************************************** Variables **************************************** //

    //*************************************** Constructor *************************************** //
    init {
        mHitPoints = hitPoints
        mLoot= 25 + 2*mHitPoints
    }

    //************************************* Public methods ************************************* //
    override fun advanceMainTick() {
        super.advanceMainTick()
    }

    override fun advanceDisplayTick() {
        // !!!SB: Implementer
        super.advanceDisplayTick()
    }

    //************************************* Private methods ************************************* //
}