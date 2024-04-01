package com.example.towerdefense.model

interface AttackEventListener {
    open fun onAttack(damage: Int)
}