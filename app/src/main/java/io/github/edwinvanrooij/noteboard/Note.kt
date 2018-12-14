package io.github.edwinvanrooij.noteboard

data class Note(
  val name: NoteName,
  val octave: Int
) : Comparable<Note> {
  override fun compareTo(other: Note) = when {
    name > other.name -> -1
    name < other.name -> 1
    else -> 0
  }
}
