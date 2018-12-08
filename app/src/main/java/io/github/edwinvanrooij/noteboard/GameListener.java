package io.github.edwinvanrooij.noteboard;

public interface GameListener {
  void gameStarted();
  void onClockChange(int secondsLeft);
  void onAccuracyChange(int accuracy);
  void onScoreChange(int newScore);
  void onNewNote(Note note);
  void gameEnded();
}
