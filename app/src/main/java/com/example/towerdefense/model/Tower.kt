package com.example.towerdefense.model
abstract class Tower(col: Int, row: Int) : Body(col, row) {
    //**************************************** Variables **************************************** //
    protected var mCost: Int = 0
    //*************************************** Constructor *************************************** //

    //************************************* Public methods ************************************** //
    override fun advanceDisplayTick() {
        // !!!SB: Implementer
    }

    fun getCost() : Int{
        return mCost
    }

    //************************************* Private methods ************************************* //

}