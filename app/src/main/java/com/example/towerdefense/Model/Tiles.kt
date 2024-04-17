package com.example.towerdefense.model

enum class Tiles(val value:Int) {
    INVALID(0),
    EMPTY(-1),
    ARCHER(-2),
    CANNON(-3),
    FLAMETHROWER(-4);

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