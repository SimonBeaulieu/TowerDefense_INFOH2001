package com.example.towerdefense.model

class Flamethrower(col: Int, row: Int) : Tower(col, row) {
    //**************************************** Variables **************************************** //

    //*************************************** Constructor *************************************** //

    //************************************* Public methods ************************************** //
    override fun advanceMainTick() {
        // !!!SB: Implementer
    }

    override fun advanceDisplayTick() {
        // !!!SB: Implementer
        super.advanceDisplayTick()
    }

    override fun findEnemiesInBlastRadius(enemies: List<AttackListener>) {
        TODO("Not yet implemented")
    }


    //************************************* Private methods ************************************* //
}