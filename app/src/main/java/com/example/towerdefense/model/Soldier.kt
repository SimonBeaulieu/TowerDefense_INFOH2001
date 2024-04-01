package com.example.towerdefense.model

class Soldier(col:Int, row: Int) : Enemy(col, row) {

    //**************************************** Variables **************************************** //

    //*************************************** Constructor *************************************** //

    //************************************* Public methods ************************************* //
    override fun onAttack(damage: Int) {
        //!!!KL
    }
    override fun advanceTick() {
        // !!!SB: Implementer
    }

    //************************************* Private methods ************************************* //
}