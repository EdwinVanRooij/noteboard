# Game description

1. I press the start button
2. The timer starts ticking
3. Score sets to 0
4. Accuracy not set yet (-)

For 1 minute
5. A question mark (?) appears on the fretboard
6. I press a note using the buttons below

	- If answer was correct:
	Question mark turns into the correctly pressed character, (green) success indication on the character for 1 second
	Pressed button colors (green) success indication for 1 second
	Success sound plays
	Correct answers +1
	Update score
	Update accuracy

	- If answer was incorrect:
	Question mark turns into the correctly pressed character, neutral indication on the character. for 1.5 seconds
	Pressed button colors (red) failure indication for 1.5 seconds
	Correct button colors neutral for 1.5 seconds
	Failure sound plays
	Incorrect answers +1
	Update accuracy

7. Back to 5
After time passes: 
	If accuracy >= 75%: Yaaay sound
	If accuracy >= 50%: Yay sound
	If accuracy < 50%: Neutral end of game sound

8. I press the replay button to start a new game
