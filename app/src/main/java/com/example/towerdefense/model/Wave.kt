package com.example.towerdefense.model

class Wave(nWave:Int = 0) {
    //**************************************** Variables **************************************** //
    private var _completionReward = 0
    private val _enemies : MutableList<Enemy> = mutableListOf()

    //*************************************** Constructor *************************************** //
    init {
        this._completionReward = nWave * 50 + 300

        // !!!SB Remplir la liste d'ennemy selon n
    }

    //************************************* Public methods ************************************* //
    fun getEnemies() : List<Enemy> { return this._enemies.toList(); }

    fun getCompletionReward() : Int { return this._completionReward; }

    //************************************* Private methods ************************************* //

}
