package com.example.towerdefense.model

abstract class Body {
    //**************************************** Variables **************************************** //
    // !!!SB: Attention au valeurs "hard codé", c'est correct ne pas en mettre aussi de limite
    var posX: Int = 0
        set(value) {
            if (value in 0..2080)
                field = value
        }
    var posY: Int = 0
        set(value) {
            if (value in 0..1080)
                field=value
        }

    //*************************************** Constructor *************************************** //


    //************************************* Public methods ************************************* //
    abstract fun advanceTick()

    //************************************* Private methods ************************************* //
}