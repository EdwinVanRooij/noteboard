package io.github.edwinvanrooij.noteboard

/**
 * A standard-tuned 6-string guitar.
 */
class Guitar(
    amountOfFrets: Int
) {

    private var guitarStrings = ArrayList<GuitarString>()

    init {
        // Add all 6 guitar strings on standard tuning
        guitarStrings.addAll(
            arrayOf(
                GuitarString(Note(NoteName.E, 4)), // string 1
                GuitarString(Note(NoteName.B, 3)), // string 2
                GuitarString(Note(NoteName.G, 3)), // string 3
                GuitarString(Note(NoteName.D, 3)), // string 4
                GuitarString(Note(NoteName.A, 2)), // string 5
                GuitarString(Note(NoteName.E, 2)) // string 6
            )
        )
    }
}
