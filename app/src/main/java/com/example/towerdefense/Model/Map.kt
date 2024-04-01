package com.example.towerdefense.model

class Map(val nWave:Int = 12) {

    //**************************************** Variables **************************************** //
    val nRows: Int = 8
    val nColumns: Int = 16
    val emptyTile = 0
    val towerTile = -1
    val pxPerTile = 120

    // Encoding of the tile which contains a road (grid x, grid y)
    private val firstTile : Pair<Int, Int> = Pair(3,0)

    //!!!KC : Changer le pathEncoding hardcodé, rendre accesseible dans le constructeur
    // Absolut path: 0 = right, 1 = up, 2 = left, 3 = down
    private val pathEncoding = listOf(
        3,3,0,0,3,3,2,2,3,3,0,0,0,0,1,1,1,1,1,0,0,0,0,0,3,3,2,2,3,3,0,0,3,3
    )

    val mapGrid  = MutableList(nColumns) { MutableList(nRows) { emptyTile } }
    //KC : À définir un setter pt?

    private val waves: MutableList<Wave> = mutableListOf()

    //*************************************** Constructor *************************************** //

    init{
        initGrid()
        initWaves()
    }
    //************************************* Public methods ************************************** //

    fun getWaves():MutableList<Wave>{ return this.waves }

    //************************************* Private methods ************************************* //

    private fun initWaves() {
        for (i in 0 until nWave)
            waves.add(Wave(i))
    }

    private fun initGrid(){
        var col = firstTile.first
        var row = firstTile.second

        mapGrid[col][row] = 1

        for (i in pathEncoding.indices) {
            when (pathEncoding[i]){
                0 -> mapGrid[++col][row]    = i + 2
                1 -> mapGrid[col][--row] = i + 2
                2 -> mapGrid[--col][row] = i + 2
                3 -> mapGrid[col][++row] = i + 2
            }
        }
    }
}