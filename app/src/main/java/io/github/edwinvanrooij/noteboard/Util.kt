package io.github.edwinvanrooij.noteboard

import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName
import io.github.edwinvanrooij.noteboard.noteboardengine.NoteName.*
import kotlin.math.roundToInt

fun accuracyToString(accuracy: Double): String {
    return "${(accuracy * 100).roundToInt()}%"
}

fun textToNoteName(rawText: String): NoteName? {
    val text = rawText.toLowerCase().trim()

    val aMap = arrayOf("a", "ay", "hey")
    val bMap = arrayOf("b", "be")
    val cMap = arrayOf("c", "see", "she", "chi")
    val dMap = arrayOf("d")
    val eMap = arrayOf("e")
    val fMap = arrayOf("f")
    val gMap = arrayOf("g")

    return when (text) {
        in aMap -> A
        in bMap -> B
        in cMap -> C
        in dMap -> D
        in eMap -> E
        in fMap -> F
        in gMap -> G
        else -> null
    }
}
