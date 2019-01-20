package io.github.edwinvanrooij.noteboard

import kotlin.math.roundToInt

fun accuracyToString(accuracy: Double):String{
    return "${(accuracy * 100).roundToInt()}%"
}
