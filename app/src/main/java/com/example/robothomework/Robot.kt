package com.example.robothomework

// data class used to hold state/ data
data class Robot(
    val robotMessageResource : Int,
    var myTurn : Boolean,
    val robotImgLarge : Int,
    val robotImgSmall : Int,
    var myEnergy : Int,
    var myReward : CharArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Robot

        if (robotMessageResource != other.robotMessageResource) return false
        if (myTurn != other.myTurn) return false
        if (robotImgLarge != other.robotImgLarge) return false
        if (robotImgSmall != other.robotImgSmall) return false
        if (myEnergy != other.myEnergy) return false
        if (!myReward.contentEquals(other.myReward)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = robotMessageResource
        result = 31 * result + myTurn.hashCode()
        result = 31 * result + robotImgLarge
        result = 31 * result + robotImgSmall
        result = 31 * result + myEnergy
        result = 31 * result + myReward.contentHashCode()
        return result
    }
}
