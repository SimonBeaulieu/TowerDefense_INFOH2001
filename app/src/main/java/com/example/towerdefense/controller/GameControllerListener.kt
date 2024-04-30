package com.example.towerdefense.controller

import com.example.towerdefense.model.Tiles
import com.example.towerdefense.model.Tower

interface GameControllerListener {
    fun startWave()

    fun addTower(col : Int, row : Int, towerType: Tiles)

    fun switchToMenu()

    fun toggleSpeed()

    fun upgradeTower(tower : Tower)
}