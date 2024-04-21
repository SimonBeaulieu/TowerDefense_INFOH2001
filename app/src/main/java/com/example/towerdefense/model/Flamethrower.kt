package com.example.towerdefense.model

class Flamethrower(col: Int, row: Int) : Tower(col, row) {
    //**************************************** Variables **************************************** //

    //*************************************** Constructor *************************************** //

    init{
        mCost=600
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
        // !!! KCSJ : Implémenter
    }


    //************************************* Private methods ************************************* //
}