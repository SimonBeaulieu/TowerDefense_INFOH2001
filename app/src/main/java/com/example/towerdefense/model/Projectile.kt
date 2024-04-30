package com.example.towerdefense.model

import kotlin.math.sqrt

class Projectile(col: Int, row: Int, projectileType: ProjectileType, projectileRadius: Int, target: AttackListener, visibility: Boolean, damage: Int) : Body(col, row) {
    //**************************************** Variables **************************************** //

    private val mProjectileType = projectileType
    private val mTarget = target

    private var mStartX = GameMapUtils.gridToPixel(col)
    private var mStartY = GameMapUtils.gridToPixel(row)
    private val mFinalX = mTarget.getPosX()
    private val mFinalY = mTarget.getPosY()
    private var mDx: Double = 0.0
    private var mDy: Double = 0.0
    private val mRadius = projectileRadius


    private var mTargetAttacked = false
    private val speedPerTick = 50
    private val mAttackTick: Int = 1
    private var mActualTick: Int = 0
    private val mDamage:Int = damage
    private val mVisibility = visibility

    //*************************************** Constructor *************************************** //

    init {
        //setRealX(col)
        //setRealY(row)
        calculateAttackTick()
        distanceToTarget()

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

    private fun calculateAttackTick() {
        //mAttackTick=distanceToTarget().toInt()/speedPerTick
        mActualTick=mAttackTick
    }

    private fun distanceToTarget(): Double {
        mDx = mFinalX.toDouble()-mStartX
        mDy = mFinalY.toDouble()-mStartY
        return sqrt((mDx * mDx)+(mDy * mDy))
    }

    private fun updatePosition(){

        val realX: Int = getRealX()
        val realY: Int = getRealY()
        val newX:Int = realX + (mDx/(mAttackTick * mGameTimerView.getTickRatio()) + 0.5*GameMapUtils.PX_PER_TILE).toInt()
        val newY:Int = realY + (mDy/(mAttackTick * mGameTimerView.getTickRatio()) + 0.5*GameMapUtils.PX_PER_TILE).toInt()

        setRealX(newX)
        setRealY(newY)

    }

}