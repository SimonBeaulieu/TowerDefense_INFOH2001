package com.example.towerdefense.model
abstract class Tower(col: Int, row: Int) : Body(col, row) {
    //**************************************** Variables **************************************** //
    protected var mRange : Int = 200
    protected var mDamage : Int = 1
    protected var mLevel : Int = 1
    protected var mAttackSpdTick : Int = 3
    private var mActualAttackTick : Int = 0

    protected var mTarget : AttackListener? = null
    protected var mInBlastRadius: MutableList<AttackListener> = mutableListOf()

    protected var mProjectiles: MutableList<Projectile> = mutableListOf()

    protected var mCost : Int = 0

    //*************************************** Constructor *************************************** //

    //************************************* Public methods ************************************** //
    override fun advanceMainTick() {

        val projectileIterator = mProjectiles.iterator()
        var p : Projectile

        while (projectileIterator.hasNext()){
            p = projectileIterator.next()
            p.advanceMainTick()

            if (p.hasReachedTarget()){
                projectileIterator.remove()
            }
        }



        if (mTarget != null && mActualAttackTick <= 0) {
            createProjectile()
            mActualAttackTick = mAttackSpdTick

        }else if (mActualAttackTick > 0) {
            mActualAttackTick -= 1
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
        mTarget=null
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
        val dx = enemy.getPosX()-this.getRealX()
        val dy = enemy.getPosY()-this.getRealY()
        val dx2 = dx * dx
        val dy2 = dy * dy
        return ((dx2 + dy2) < (mRange * mRange))
    }

    fun upgradeTower(){
        mLevel+=1
    }

    fun getCost() : Int {
        return mCost
    }

    protected abstract fun createProjectile()

    fun getProjectiles(): List<Projectile>{
        return mProjectiles
    }

    //************************************* Private methods ************************************* //


}