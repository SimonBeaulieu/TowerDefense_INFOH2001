package com.example.towerdefense.model

import kotlin.math.sqrt

class Projectile(col: Int, row: Int, projectileType: ProjectileType, projectileRadius: Int, target: AttackListener, visibility: Boolean, damage: Int) : Body(col, row) {
    //**************************************** Variables **************************************** //

    private val mProjectileType = projectileType
    private val mTarget = target

    private var mStartX : Int = GameMapUtils.gridToCenterPixel(col)
    private var mStartY : Int = GameMapUtils.gridToCenterPixel(row)
    private val mFinalX : Int = mTarget.getNextRealPos().first + (GameMapUtils.PX_PER_TILE/2)
    private val mFinalY : Int = mTarget.getNextRealPos().second + (GameMapUtils.PX_PER_TILE/2)

    private val mRadius : Int = projectileRadius


    private var mTargetAttacked = false
    private var mActualTick: Int = 1
    private val mDamage: Int = damage
    private val mVisibility = visibility

    //*************************************** Constructor *************************************** //

    init {

    }

    //************************************* Public methods ************************************** //
    override fun advanceMainTick() {
        // !!!SB: Implementer
        if(mActualTick<=0){
            mTarget.onAttack(mDamage)
            mTargetAttacked=true
        } else{
            mActualTick-=1
        }
    }

    override fun advanceDisplayTick() {
        updatePosition()
    }

    fun isVisible(): Boolean{
        return mVisibility
    }

    fun hasReachedTarget() : Boolean {
        return mTargetAttacked
    }

    fun getType() : ProjectileType{
        return mProjectileType
    }

    fun getRadius():Int{
        return mRadius
    }

    //************************************* Private methods ************************************* //

    private fun updatePosition(){

        val tickFraction: Double = mGameTimerView.getTickFraction()

        val newX:Int = ((mFinalX-mStartX)*tickFraction).toInt() + mStartX
        val newY:Int = ((mFinalY-mStartY)*tickFraction).toInt() + mStartY

        setRealX(newX)
        setRealY(newY)

    }

}