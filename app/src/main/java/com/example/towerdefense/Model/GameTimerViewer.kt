package com.example.towerdefense.model

interface GameTimerViewer {
    fun getTickFraction() : Double

    fun isFirstHalf(): Boolean
}