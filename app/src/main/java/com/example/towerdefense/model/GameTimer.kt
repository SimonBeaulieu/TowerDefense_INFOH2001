package com.example.towerdefense.model
import java.util.*
import kotlin.concurrent.timerTask

class GameTimer(private val tickInterval:Long = 1000) {
    //**************************************** Variables **************************************** //
    private var timer: Timer? = null
    private var isRunning = false
    private var tickListener: (() -> Unit)? = null

    //*************************************** Constructor *************************************** //

    //************************************* Public methods ************************************** //
    fun start() {
        if (!isRunning) {
            timer = Timer()

            timer?.scheduleAtFixedRate(timerTask {
                tickListener?.invoke()
            }, 0, tickInterval)
            isRunning = true
        }
    }

    fun stop() {
        timer?.cancel()
        isRunning = false
    }

    fun setTickListener(listener: () -> Unit){
        tickListener = listener
    }

    //************************************* Private methods ************************************* //

}