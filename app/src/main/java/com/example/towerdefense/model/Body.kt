package com.example.towerdefense.model

abstract class Body(col : Int, row : Int) {
    //**************************************** Variables **************************************** //
    protected var gridX: Int = col
        set(value) {
//            if (GameMap.isValidCol(value)) {
                field = value
//            }
        }
    protected var gridY: Int = row
        set(value) {
//            if (GameMap.isValidRow(value)) {
                field = value
//            }
        }

    //*************************************** Constructor *************************************** //


    //************************************* Public methods ************************************* //
    abstract fun advanceTick()

    //************************************* Private methods ************************************* //
}