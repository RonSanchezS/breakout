package com.moviles.practicadibujotouch.modelos

class Pelota(
    var x: Float,
    var y: Float,
    var radio: Float,
    var vX: Float,
    var vY: Float
) {
    val velocidad = 10f
    val direccionHorizontal: String
        get() {
            return if (x < 0) {
                "derecha"
            } else if (x > 500) {
                "izquierda"
            } else {
                "centro"
            }
        }
    val direccionVertical: String
        get() {
            if (y < 0) {
                return "abajo"
            } else if (y > 500) {
                return "arriba"
            } else {
                return "centro"
            }
        }

}