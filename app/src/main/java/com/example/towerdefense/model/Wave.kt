package com.example.towerdefense.model

import kotlin.math.pow
import kotlin.math.round

class Wave(waveNum:Int = 0) {
    //**************************************** Variables **************************************** //
    private var mCompletionReward = 0
    private val mEnemies : MutableList<Enemy> = mutableListOf()

    var mInProgress : Boolean = false

    //*************************************** Constructor *************************************** //
    init {
        val nEnemies : Int = round(4 * 20.0.pow((waveNum - 1)/10.0)).toInt()
        val enemiesHitPoints : Int = 5 + waveNum

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
