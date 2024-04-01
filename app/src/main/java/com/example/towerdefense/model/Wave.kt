package com.example.towerdefense.model

class Wave(nWave:Int = 0) {
    //**************************************** Variables **************************************** //
    private var completionReward = 0
    private val enemies : MutableList<Enemy> = mutableListOf()

    //*************************************** Constructor *************************************** //
    init {
        this.completionReward = nWave * 50 + 300

        // !!!SB Remplir la liste d'ennemy selon n
    }

    //************************************* Public methods ************************************* //
    fun getEnemies() : List<Enemy> { return this.enemies.toList(); }

    fun getCompletionReward() : Int { return this.completionReward; }

    //************************************* Private methods ************************************* //

}
