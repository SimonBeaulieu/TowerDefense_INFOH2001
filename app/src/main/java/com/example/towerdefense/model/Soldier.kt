package com.example.towerdefense.model

class Soldier(col:Int, row: Int, spawnTick: Int) : Enemy(col, row, spawnTick) {
    //**************************************** Variables **************************************** //

    //*************************************** Constructor *************************************** //

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