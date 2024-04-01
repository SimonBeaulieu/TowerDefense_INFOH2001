package com.example.towerdefense.model
import java.util.*
import kotlin.concurrent.timerTask

class GameTimer(private val tickInterval:Long = 1000) {
    //**************************************** Variables **************************************** //
    private var _timer: Timer? = null
    private var _isRunning = false
    private var _tickListener: (() -> Unit)? = null

    //*************************************** Constructor *************************************** //

    //************************************* Public methods ************************************** //
    fun start() {
        if (!_isRunning) {
            _timer = Timer()

            _timer?.scheduleAtFixedRate(timerTask {
                _tickListener?.invoke()
            }, 0, tickInterval)
            _isRunning = true
        }
    }

    fun stop() {
        _timer?.cancel()
        _isRunning = false
    }

    fun setTickListener(listener: () -> Unit){
        _tickListener = listener
    }

    //************************************* Private methods ************************************* //

}