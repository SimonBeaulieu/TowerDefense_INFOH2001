package com.example.towerdefense.model

interface AttackListener {
    fun onAttack(damage: Int)

    fun getPosX() : Int

    fun getPosY() : Int

    fun getNextGridPos(): Pair<Int,Int>

    fun getNextRealPos(): Pair<Int,Int>
}