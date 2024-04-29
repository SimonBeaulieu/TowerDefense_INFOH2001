package com.example.towerdefense.model


class Flamethrower(col: Int, row: Int) : Tower(col, row) {
    //**************************************** Variables **************************************** //

    //*************************************** Constructor *************************************** //

    init{
        mCost=600
        mDamage=5
        mRange=150
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
            mProjectiles.add(Projectile(this.getRealX(), this.getRealY(),
                ProjectileType.FLAMETHROWER_PROJECTILE, mTarget!!,false,mDamage))
        }
        for (e in mInBlastRadius){
            mProjectiles.add(Projectile(this.getRealX(), this.getRealY(),
                ProjectileType.FLAMETHROWER_PROJECTILE, e,false,mDamage))

        }
    }


    //************************************* Private methods ************************************* //
}