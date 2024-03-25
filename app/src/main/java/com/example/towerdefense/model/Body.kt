package com.example.towerdefense.model

abstract class Body {
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
}