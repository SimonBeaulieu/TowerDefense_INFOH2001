package com.example.towerdefense.model

enum class Tiles(val value:Int) {
    EMPTY(0),
    ARCHER(-1),
    CANNON(-2),
    FLAMETHROWER(-3);
    companion object {
        fun fromInt(value: Int): Tiles {
            return when (value) {
                ARCHER.value -> { ARCHER }
                CANNON.value -> { CANNON }
                FLAMETHROWER.value -> { FLAMETHROWER }
                else -> { EMPTY }
            }
        }
    }
}