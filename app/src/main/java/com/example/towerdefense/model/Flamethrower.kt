package com.example.towerdefense.model

import android.graphics.Color

class Flamethrower(col: Int, row: Int) : Tower(col, row) {
    //**************************************** Variables **************************************** //

    //*************************************** Constructor *************************************** //

    init{
        mCost = 600
        mDamage = 1
        mRange = 150
        mProjectileRadius = 50

        mProjectileColor = Color.parseColor("#f95109")
    }

    //************************************* Public methods ************************************** //
    override fun advanceMainTick() {
        super.advanceMainTick()
    }

    override fun advanceDisplayTick() {
        // !!!SB: Implementer
        super.advanceDisplayTick()
    }

    override fun findEnemiesInBlastRadius(enemies: List<AttackListener>) {

        mInBlastRadius.clear()

        for (e in enemies) {
            if (mTarget != e && isInRange(e)) {
                mInBlastRadius.add(e)
            }
        }
    }

    override fun createProjectile() {
        if(mTarget != null){
            mProjectiles.add(Projectile(this.getGridX(), this.getGridY(),
                ProjectileType.FLAMETHROWER_PROJECTILE, mProjectileRadius, mTarget!!,false,mDamage, mProjectileColor))
        }
        for (e in mInBlastRadius){
            mProjectiles.add(Projectile(this.getRealX(), this.getRealY(),
                ProjectileType.FLAMETHROWER_PROJECTILE, mProjectileRadius, e,false,mDamage, mProjectileColor))

        }
    }

    override fun upgradeStats() {
        mRange += 10
        mDamage += 1

        if (mAttackSpdTick >= 2) {
            mAttackSpdTick -= 1
        }
    }


    //************************************* Private methods ************************************* //
}