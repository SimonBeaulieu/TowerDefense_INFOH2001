package com.example.towerdefense.model

import android.graphics.Color

class Flamethrower(col: Int, row: Int) : Tower(col, row) {
    //**************************************** Variables **************************************** //

    //*************************************** Constructor *************************************** //

    init{
        mCost = 1500
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

    override fun createProjectiles() {
        if(mTarget != null){
            mProjectiles.add(Projectile(this.getGridX(), this.getGridY(),
                ProjectileType.FLAMETHROWER_PROJECTILE, mRange, mTarget!!,true,mDamage, mProjectileColor))
        }
        for (e in mInBlastRadius){
            mProjectiles.add(Projectile(this.getRealX(), this.getRealY(),
                ProjectileType.FLAMETHROWER_PROJECTILE, mRange, e,false,mDamage, mProjectileColor))

        }
    }

    override fun upgradeStats() {
        mRange += 10
        mDamage += 1

        if (mAttackSpdTick >= 2) {
            mAttackSpdTick -= 1
        }
    }

    override fun getTowerName(): String {
        return "Flamethrower"
    }

    //************************************* Private methods ************************************* //
}