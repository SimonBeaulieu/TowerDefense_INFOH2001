package com.example.towerdefense.model

import kotlin.math.pow
import kotlin.math.round

class Wave(waveNum:Int = 0) {
    //**************************************** Variables **************************************** //
    private var mCompletionReward = 0
    private val mEnemies : MutableList<Enemy> = mutableListOf()

    //*************************************** Constructor *************************************** //
    init {
        val nEnemies : Int = round(10 * 50.0.pow((waveNum - 1) / 10.0)).toInt()
        mCompletionReward = waveNum * 50 + 300

        for (i in 0 until nEnemies) {
            mEnemies.add(Soldier(-1,-1, i))
        }
    }

    //************************************* Public methods ************************************* //
    fun getEnemies() : List<Enemy> { return this.mEnemies.toList(); }

    fun getCompletionReward() : Int { return this.mCompletionReward; }

    //************************************* Private methods ************************************* //

}
