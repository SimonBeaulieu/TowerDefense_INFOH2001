package com.example.towerdefense.model
import com.example.towerdefense.model.service.GameTimerReadService
import com.example.towerdefense.model.service.ServiceLocator
import java.util.*
import kotlin.concurrent.timerTask

class GameTimer(private val displayTickInterval:Long = 50) : GameTimerSubject {
    //**************************************** Variables **************************************** //
    private val mObservers : MutableList<GameTimerObserver> = mutableListOf()

    private val mTickRatio = 10
    private var mTickCount = 0
    private var mAccelerated = false
    private var mTickDuration : Long = displayTickInterval
    private var mNeedRestart : Boolean = false

    var enableDisplay = true
    var enableTicks = true

    private var mTimer: Timer? = null

    //*************************************** Constructor *************************************** //
    init {
        // Creation of timer
        mTimer = Timer()
        ServiceLocator.addService(GameTimerReadService(this))
    }

    //************************************* Public methods ************************************** //
    override fun attachObserver(observer:GameTimerObserver) {
        this.mObservers.add(observer)
    }

    override fun detachObserver(observer: GameTimerObserver) {
        this.mObservers.remove(observer)
    }

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
            gameTick()
            displayTick()
        }, 0, mTickDuration)
    }

    fun toggleSpeed() {
        mAccelerated = !mAccelerated

        if (mAccelerated) {
            mTickDuration = displayTickInterval/2
        } else {
            mTickDuration = displayTickInterval
        }
        mNeedRestart = true
    }

    fun getTickFraction() : Double {
        return mTickCount/mTickRatio.toDouble()
    }

    fun isFirstHalf(): Boolean {
        return mTickCount/mTickRatio.toDouble() < 0.5
    }

    //************************************* Private methods ************************************* //
    private fun displayTick() {
        if (enableDisplay) {
            notifyObserversDisplayTick()
        }
    }

    private fun gameTick() {
        if (enableTicks) {
            mTickCount += 1

            if (mTickCount >= mTickRatio) {
                notifyObserversGameTick()
                mTickCount = 0
            }
        }

        if (mNeedRestart) {
            mTimer?.cancel()
            mTimer = Timer()
            start()
            mNeedRestart = false
        }
    }

    override fun notifyObserversGameTick() {
        for (o in this.mObservers) {
            o.onNotifyGameTick()
        }
    }

    override fun notifyObserversDisplayTick() {
        for (o in this.mObservers) {
            o.onNotifyDisplayTick()
        }
    }
}