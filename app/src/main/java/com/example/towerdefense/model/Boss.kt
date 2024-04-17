package com.example.towerdefense.model

class Boss(col:Int, row: Int, spawnTick: Int) : Enemy(col, row, spawnTick) {
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

    //************************************* Private methods ************************************* //
}