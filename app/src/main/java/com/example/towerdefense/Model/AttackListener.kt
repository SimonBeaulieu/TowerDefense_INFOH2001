package com.example.towerdefense.model
interface AttackListener {
    fun onAttack(damage: Int)

    fun getPosX() : Int

    fun getPosY() : Int
}