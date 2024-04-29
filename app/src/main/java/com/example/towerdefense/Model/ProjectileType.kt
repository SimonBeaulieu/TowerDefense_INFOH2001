package com.example.towerdefense.Model

enum class ProjectileType (val value:Int) {
    ARCHER_PROJECTILE(1),
    CANNON_PROJECTILE(2),
    FLAMETHROWER_PROJECTILE(3),
    NONE(0);

    companion object {
        fun fromInt(value: Int): ProjectileType {
            return when (value) {
                ARCHER_PROJECTILE.value -> { ARCHER_PROJECTILE }
                CANNON_PROJECTILE.value -> { CANNON_PROJECTILE }
                FLAMETHROWER_PROJECTILE.value -> { FLAMETHROWER_PROJECTILE }
                else -> { NONE }
            }
        }
    }
}