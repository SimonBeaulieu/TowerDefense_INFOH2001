package com.example.towerdefense.model

import android.graphics.Color
import kotlin.math.atan2

abstract class Tower(col: Int, row: Int) : Body(col, row) {
    //**************************************** Variables **************************************** //
    protected var mRange : Int = 200
    protected var mDamage : Int = 1
    protected var mAttackSpdTick : Int = 3
    private var mAttackCoolDown : Int = 0

    protected var mTarget : AttackListener? = null
    protected var mInBlastRadius: MutableList<AttackListener> = mutableListOf()

    protected var mProjectiles: MutableList<Projectile> = mutableListOf()
    protected var mProjectileRadius:Int=0
    protected var mProjectileColor : Int = Color.RED

    protected var mCost : Int = 0

    protected var mLevel : Int = 1
    protected val mMaxLevel : Int = 4


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

        if (mTarget != null && mAttackCoolDown <= 0) {
            aimEnemy(mTarget!!)
            createProjectiles()
            mAttackCoolDown = mAttackSpdTick

        }else if (mAttackCoolDown > 0) {
            mAttackCoolDown -= 1
        }

    }

    override fun advanceDisplayTick() {
        for (p in mProjectiles){
            p.advanceDisplayTick()
        }
    }

    fun findTargets(enemies: List<AttackListener>){
        findMainTarget(enemies)
        findEnemiesInBlastRadius(enemies)
    }

    private fun findMainTarget(enemies: List<AttackListener>){
        mTarget=null
        var pos : Pair<Int, Int>
        var tileValue = 0
        var maxTileValue = 0
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

    protected open fun aimEnemy(enemy: AttackListener) {
        val y2 = enemy.getNextRealPos().first
        val x2 = enemy.getNextRealPos().second
        val dx : Double = (y2 - this.getRealX()).toDouble()
        val dy : Double = (x2- this.getRealY()).toDouble()
        mAngle = Math.toDegrees(atan2(dy, dx)).toFloat() + 90.0F
    }

    protected abstract fun findEnemiesInBlastRadius(enemies: List<AttackListener>)

    protected fun isInRange(enemy: AttackListener): Boolean {
        val dx = enemy.getPosX()-this.getRealX()
        val dy = enemy.getPosY()-this.getRealY()
        val dx2 = dx * dx
        val dy2 = dy * dy
        return ((dx2 + dy2) < (mRange * mRange))
    }

    fun upgrade(){
        if (!isMaxLevel()) {
            mLevel++
            upgradeStats()
        }
    }

    protected abstract fun upgradeStats()

    fun getCost() : Int {
        return mCost
    }
    fun getRange(): Int{
        return mRange
    }
    protected abstract fun createProjectiles()

    fun getProjectiles(): List<Projectile>{
        return mProjectiles
    }

    fun getProjectileRadius():Int{
        return mProjectileRadius
    }

    fun getUpgradeCost() : Int {
        return 500*mLevel
    }

    fun getLevel() : Int {
        return mLevel
    }

    fun isMaxLevel() : Boolean {
        return mLevel == mMaxLevel
    }

    abstract fun getTowerName() : String

    //************************************* Private methods ************************************* //


}