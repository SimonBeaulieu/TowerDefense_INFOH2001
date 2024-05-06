package com.example.towerdefense.model

import kotlin.math.min
import kotlin.math.pow
import kotlin.math.round

class Wave(waveNum:Int = 0) {
    //**************************************** Variables **************************************** //
    private var mCompletionReward = 0
    private val mEnemies : MutableList<Enemy> = mutableListOf()

    var mInProgress : Boolean = false

    //*************************************** Constructor *************************************** //
    init {
        val nEnemies : Int = min(3 * 1.2.pow(waveNum).toInt(), 300)
        val enemiesHitPoints : Int = min(5 * 1.15.pow(waveNum).toInt(), 1000)

        mCompletionReward = waveNum * 50 + 100

        for (i in 0 until nEnemies) {
            mEnemies.add(Soldier(-1,-1, i, enemiesHitPoints))
        }
    }

    //************************************* Public methods ************************************* //
    fun getEnemies() : List<Enemy> { return this.mEnemies.toList(); }

    fun getCompletionReward() : Int { return this.mCompletionReward; }

    //************************************* Private methods ************************************* //

}
