package com.example.towerdefense.model
import java.util.*
import kotlin.concurrent.timerTask

class GameTimer(private val displayTickInterval:Long = 50) : GameTimerViewer {
    //**************************************** Variables **************************************** //
    private val mTickRatio = 10
    private var mTickCount = 0

    private var mIsRunning = false

    private var mTimer: Timer? = null

    private var mMainTickListener: (() -> Unit)? = null
    private var mDisplayTickListener: (() -> Unit)? = null

    //*************************************** Constructor *************************************** //

    //************************************* Public methods ************************************** //
    fun start() {
        mTimer = Timer()

        mTimer?.scheduleAtFixedRate(timerTask {
            mTickCount += 1

            if (mTickCount >= mTickRatio && mIsRunning) {
                mMainTickListener?.invoke()
                mTickCount = 0
            }

            mDisplayTickListener?.invoke()

        }, 0, displayTickInterval)
    }

    fun startMain() {
        mIsRunning = true
    }

    fun stopMain() {
        mIsRunning = false
    }

    fun setMainTickListener(listener: () -> Unit){
        mMainTickListener = listener
    }

    fun setDisplayTickListener(listener: () -> Unit){
        mDisplayTickListener = listener
    }

    fun isRunning() : Boolean { return mIsRunning }

    override fun getTickFraction() : Double {
        return mTickCount/mTickRatio.toDouble()
    }

    //************************************* Private methods ************************************* //

}