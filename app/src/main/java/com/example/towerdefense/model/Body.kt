package com.example.towerdefense.model

abstract class Body(col : Int, row : Int) {
    //**************************************** Variables **************************************** //
    // !!!SB: Attention au valeurs "hard codé", c'est correct ne pas en mettre aussi de limite
    protected var posX: Int = 0
        set(value) {
            if (value in 0..2080)
                field = value
        }
    protected var posY: Int = 0
        set(value) {
            if (value in 0..1080)
                field=value
        }

    //*************************************** Constructor *************************************** //
    init {
        val p = GameMap.gridToPixel(col, row)
        posX = p.first
        posY = p.second
    }

    //************************************* Public methods ************************************* //
    abstract fun advanceTick()

    //************************************* Private methods ************************************* //
}