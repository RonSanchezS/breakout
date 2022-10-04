package com.moviles.practicadibujotouch.modelos

import android.graphics.Color

class Cuadrado(
    var x: Float,
    var y: Float,
    var lado: Float,
    var alto: Float,
    val color: Int
) {

    override fun toString(): String {
        return "Cuadrado(x=$x, y=$y, lado=$lado, alto=$alto)"
    }
}