package io.github.edwinvanrooij.noteboard;

public interface GameListener {
  void gameStarted();
  void onAccuracyChange(int accuracy);
  void onScoreChange(int newScore);
  void onNewNote(Note note);
  void onCorrectGuess(Note note);
  void onIncorrectGuess(Note note);
}
