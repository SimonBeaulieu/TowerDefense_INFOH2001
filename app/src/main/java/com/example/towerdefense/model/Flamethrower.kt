package com.example.towerdefense.model

class Flamethrower(col: Int, row: Int) : Tower(col, row) {
    //**************************************** Variables **************************************** //

    //*************************************** Constructor *************************************** //

    init{
        mCost = 600
        mDamage = 1
        mRange = 150
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

    override fun upgradeStats() {
        mRange += 10
        mDamage += 1

        if (mAttackSpdTick >= 2) {
            mAttackSpdTick -= 1
        }
    }


    //************************************* Private methods ************************************* //
}