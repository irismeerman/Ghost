# Ghost
Ghost word game; each player has to add one letter to a word, without forming it into an existing word. Finishing the word means losing the game. 
You can also challenge your opponent to finish the word in case you think it can not become an existing word anymore. The challanger wins if the opponent is not able to finish the word.

Features: 
- new playernames can be entered as well as already used names can be selected by a dropdown menu that appears as soon as the EditText field is selected at the main activity
- the keyboard will only contain alphabetic characters, and therefore prevent the players for wrong input
- the language and playernames will be saved in a SharedPreference object, and therefore maintain when reopening the app
- a menubutton at the upper right corner of the app will lead to a settings menu in which the names and language can be set. This menubutton is accessible through the whole game.
- Winning a game means 1 point added to the total amount of points for that player (name).

