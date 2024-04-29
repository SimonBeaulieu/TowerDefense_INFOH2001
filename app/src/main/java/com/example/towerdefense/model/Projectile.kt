package com.example.towerdefense.model

import kotlin.math.sqrt

class Projectile(col: Int, row: Int, projectileType: ProjectileType, target: AttackListener, visibility: Boolean, damage: Int) : Body(col, row) {
    //**************************************** Variables **************************************** //

    private val mProjectileType = projectileType
    private val mTarget = target

    private val mStartX = col
    private val mStartY = row
    private val mFinalX = mTarget.getPosX()
    private val mFinalY = mTarget.getPosY()
    private var mDx: Double = 0.0
    private var mDy: Double = 0.0


    private var mTargetAttacked = false
    private val SPEED_PER_TICK = 50
    private var mAttackTick: Int = 0
    private val mDamage:Int = damage
    private val mVisibility = visibility

    //*************************************** Constructor *************************************** //

    init {
        calculateAttackTick()
    }

    //************************************* Public methods ************************************** //
    override fun advanceMainTick() {
        // !!!SB: Implementer
        if(mAttackTick<=0){
            mTarget.onAttack(mDamage)
            mTargetAttacked=true
        } else{
            mAttackTick-=1

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

    //************************************* Private methods ************************************* //

    private fun calculateAttackTick() {
        mAttackTick=distanceToTarget().toInt()/SPEED_PER_TICK
    }

    private fun distanceToTarget(): Double {
        mDx = mFinalX.toDouble()-mStartX
        mDy = mFinalY.toDouble()-mStartY
        return sqrt((mDx * mDx)+(mDy * mDy))
    }

    private fun updatePosition(){

        setRealX(getRealX() + (mDx/(mAttackTick * mGameTimerView.getTickFraction())).toInt())
        setRealY(getRealY() + (mDy/(mAttackTick * mGameTimerView.getTickFraction())).toInt())

    }

}