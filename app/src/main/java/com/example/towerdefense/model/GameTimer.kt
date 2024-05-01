package com.example.towerdefense.model
import java.util.*
import kotlin.concurrent.timerTask

class GameTimer(private val displayTickInterval:Long = 50) : GameTimerViewer {
    //**************************************** Variables **************************************** //
    private val mTickRatio = 10
    private var mTickCount = 0
    private var mAccelerated = false
    private var mActualTickInterval : Long = displayTickInterval
    private var mRestart : Boolean = false

    var enableDisplay = false
    var enableTicks = false

    private var mTimer: Timer? = null

    private var mMainTickListener: (() -> Unit)? = null
    private var mDisplayTickListener: (() -> Unit)? = null

    //*************************************** Constructor *************************************** //
    init {
        // Creation of timer
        mTimer = Timer()
    }
    //************************************* Public methods ************************************** //
    /**
     * Should only be called onced, at initialisation, after the listener
     * are setted.
     *
     * Use enableDisplay and enableTicks to stop/start these functionnalities
     */
    fun start() {
        // Executed code when timer period elapse
        mTimer?.purge()
        mTimer?.scheduleAtFixedRate(timerTask {
            updateMechanics()
            updateDisplay()
        }, 0, mActualTickInterval)
    }

    fun toggleSpeed() {
        mAccelerated = !mAccelerated

        if (mAccelerated) {
            mActualTickInterval = displayTickInterval/2
        } else {
            mActualTickInterval = displayTickInterval
        }
        mRestart = true
    }

    fun setMainTickListener(listener: () -> Unit){
        mMainTickListener = listener
    }

    fun setDisplayTickListener(listener: () -> Unit){
        mDisplayTickListener = listener
    }

    override fun getTickFraction() : Double {
        return mTickCount/mTickRatio.toDouble()
    }

    override fun isFirstHalf(): Boolean {
        return mTickCount/mTickRatio.toDouble() < 0.5
    }

    //************************************* Private methods ************************************* //
    private fun updateDisplay() {
        if (enableDisplay) {
            mDisplayTickListener?.invoke()
        }
    }

    private fun updateMechanics() {
        if (enableTicks) {
            mTickCount += 1

            if (mTickCount >= mTickRatio) {
                mMainTickListener?.invoke()
                mTickCount = 0
            }
        }

        if (mRestart) {
            mTimer?.cancel()
            mTimer = Timer()
            start()
            mRestart = false
        }
    }
}