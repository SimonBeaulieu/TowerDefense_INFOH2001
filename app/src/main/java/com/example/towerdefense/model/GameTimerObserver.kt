package com.example.towerdefense.model

interface GameTimerObserver {
    fun onNotifyGameTick()
    fun onNotifyDisplayTick()
}