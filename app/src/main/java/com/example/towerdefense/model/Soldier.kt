package com.example.towerdefense.model

class Soldier(col:Int, row: Int, spawnTick: Int) : Enemy(col, row, spawnTick) {

    //**************************************** Variables **************************************** //

    //*************************************** Constructor *************************************** //

    //************************************* Public methods ************************************* //
    override fun advanceTick() {
        super.advanceTick()
    }

    //************************************* Private methods ************************************* //
}