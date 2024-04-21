package com.example.towerdefense.model

class Cannon(col: Int, row: Int) : Tower(col, row) {
    //**************************************** Variables **************************************** //

    //*************************************** Constructor *************************************** //
    init {
        mCost = 1000
        mDamage = 2
    }
    //************************************* Public methods ************************************** //
    override fun advanceMainTick() {
        // !!!SB: Implementer
    }

    override fun advanceDisplayTick() {
        // !!!SB: Implementer
        super.advanceDisplayTick()
    }

    override fun findEnemiesInBlastRadius(enemies: List<AttackListener>) {
        // !!!KCSJ Implémenter
    }


    //************************************* Private methods ************************************* //
}