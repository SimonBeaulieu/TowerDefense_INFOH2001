package com.example.towerdefense.model
import java.util.*
import kotlin.concurrent.timerTask

class GameTimer(private val tickInterval:Long = 1000) {
    //**************************************** Variables **************************************** //
    private var mTimer: Timer? = null
    private var mIsRunning = false
    private var mTickListener: (() -> Unit)? = null

    //*************************************** Constructor *************************************** //

    //************************************* Public methods ************************************** //
    fun start() {
        if (!mIsRunning) {
            mTimer = Timer()

            mTimer?.scheduleAtFixedRate(timerTask {
                mTickListener?.invoke()
            }, 0, tickInterval)
            mIsRunning = true
        }
    }

    fun stop() {
        mTimer?.cancel()
        mIsRunning = false
    }

    fun setTickListener(listener: () -> Unit){
        mTickListener = listener
    }

    fun isRunning() : Boolean { return mIsRunning }

    //************************************* Private methods ************************************* //

}