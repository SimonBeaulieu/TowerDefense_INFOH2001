package com.example.towerdefense.model

interface GameTimerSubject {
    fun attachObserver(observer:GameTimerObserver)

    fun detachObserver(observer: GameTimerObserver)

    fun notifyObserversGameTick()

    fun notifyObserversDisplayTick()
}