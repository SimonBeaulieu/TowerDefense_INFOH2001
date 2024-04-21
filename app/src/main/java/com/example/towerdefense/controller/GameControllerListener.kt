package com.example.towerdefense.controller

import com.example.towerdefense.model.Tiles

interface GameControllerListener {
    fun startWave()

    fun addTower(col : Int, row : Int, towerType: Tiles)

    fun switchToMenu()
}