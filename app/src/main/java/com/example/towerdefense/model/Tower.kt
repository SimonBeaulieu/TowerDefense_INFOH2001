package com.example.towerdefense.model
abstract class Tower(col: Int, row: Int) : Body(col, row) {
    //**************************************** Variables **************************************** //
    protected var mRange : Int = 200
    protected var mDamage : Int = 1
    protected var mLevel : Int = 1

    protected var mTarget : AttackListener? = null
    protected var mInBlastRadius: MutableList<AttackListener> = mutableListOf()

    protected var mCost : Int = 0

    //*************************************** Constructor *************************************** //

    //************************************* Public methods ************************************** //
    override fun advanceMainTick() {
        if (mTarget != null) {
            mTarget?.onAttack(mDamage)
        }
        for (e in mInBlastRadius) {
            e.onAttack(mDamage)
        }
    }

    override fun advanceDisplayTick() {
        // !!!SB: Implementer
    }

    fun findTargets(enemies: List<AttackListener>){
        findMainTarget(enemies)
        findEnemiesInBlastRadius(enemies)
    }

    private fun findMainTarget(enemies: List<AttackListener>){
        var pos : Pair<Int, Int>
        var tileValue : Int = 0
        var maxTileValue : Int = 0
        for (e in enemies){
            if (isInRange(e)){
                pos = GameMapUtils.pixelToGrid(e.getPosX(), e.getPosY())
                tileValue = mGameMapView.getTileContent(pos.first, pos.second)
                if (tileValue > maxTileValue) {
                    maxTileValue = tileValue
                    mTarget = e
                }
            }
        }
    }

    protected abstract fun findEnemiesInBlastRadius(enemies: List<AttackListener>)
    protected fun isInRange(enemy: AttackListener): Boolean {
        var dx = enemy.getPosX()-this.getRealX()
        var dy = enemy.getPosY()-this.getRealY()
        var dx2 = dx * dx
        var dy2 = dy * dy
        return ((dx2 + dy2) < (mRange * mRange))
    }
    fun upgradeTower(){
        mLevel+=1
    }

    fun getCost() : Int {
        return mCost
    }
    //************************************* Private methods ************************************* //


}