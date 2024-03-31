package com.example.towerdefense.model

class Wave {
    //**************************************** Variables **************************************** //
    private var bonusMoney = 0
    private val waveEnemies : MutableList<Enemy> = mutableListOf()

    //*************************************** Constructor *************************************** //
    fun initWave(n : Int) {
        this.bonusMoney = n * 300

        // !!!SB Remplir la liste d'ennemy selon n
    }

    //************************************* Public methods ************************************* //
    fun getWaveEnemies() : MutableList<Enemy> { return this.waveEnemies; }

    fun getBonusMoney() : Int { return this.bonusMoney; }

    //************************************* Private methods ************************************* //

}
