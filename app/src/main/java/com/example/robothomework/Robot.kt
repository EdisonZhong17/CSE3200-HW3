package com.example.robothomework

// data class used to hold state/ data
data class Robot(
    val robotMessageResource : Int,
    var myTurn : Boolean,
    val robotImgLarge : Int,
    val robotImgSmall : Int,
    var myEnergy : Int
)
