package com.example.towerdefense.model

abstract class Body(col : Int, row : Int) {
    //**************************************** Variables **************************************** //
    var mGridX: Int = col
        set(value) {
            if (GameMap.isValidCol(value)) {
                field = value
            }
        }
    var mGridY: Int = row
        set(value) {
            if (GameMap.isValidRow(value)) {
                field = value
            }
        }

    //*************************************** Constructor *************************************** //


    //************************************* Public methods ************************************* //
    abstract fun advanceTick()


    //************************************* Private methods ************************************* //
}