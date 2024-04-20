package com.example.towerdefense.model

class Archer(col: Int, row: Int) : Tower(col, row) {
    //**************************************** Variables **************************************** //

    //*************************************** Constructor *************************************** //

    init{
        mCost=500
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