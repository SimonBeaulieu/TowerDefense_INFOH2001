package com.example.towerdefense.model.service

import com.example.towerdefense.model.GameTimer

class GameTimerReadService(private val gameTimer : GameTimer) : Service {
    override fun getServiceClass() : Class<*> {
        return GameTimerReadService::class.java
    }

    fun getTickFraction() : Double {
        return gameTimer.getTickFraction()
    }

    fun isFirstHalf(): Boolean {
        return gameTimer.isFirstHalf()
    }
}