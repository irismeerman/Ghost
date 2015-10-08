package nl.iris_meerman.ghost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {

    String wordGuess = "", player;
    int player1 = 1;
    int player2 = 2;
    int countTurns, winnerint;
    int i = 0;
    int winner;
    SharedPreferences gameprefs;
    private Lexicon lexicon;

    public Game(Lexicon lexicon){
        this.lexicon = lexicon;
        //ArrayList<String> testgame = new ArrayList<String>(Arrays.asList("a", "a", "r", "d", "d", "a", "r", "k"));
        countTurns = 0;
        // zolang de game nog niet over is : speel het spel
        //while (ended() == false) {
            //Log.d("test: ", "in while loop game");
            //int whosturn = turn();
            //Log.d("test player turn: ", String.valueOf(whosturn));

            //String letteryes = testgame.get(i);
            //Log.d("test player letter: ", letteryes);
            //guess(letteryes);
            //i++;
        // }
        //Log.d("test: ", "uit while loop");
        //winnerint = winner();
        //Log.d("test winner: ", String.valueOf(winnerint));
    }

    // guess filtert alle onmogelijke woorden weg
    public String guess(String letter){
        // als het de eerste letter is, filter dan op de hele lexiconSet
        if (wordGuess.isEmpty()) {
            lexicon.filter(letter);
            wordGuess = letter;
            //Log.d("test wordguess: ", wordGuess);
        } // als de guess niet de eerste letter is, filter dan op het filter
        else {
            wordGuess = wordGuess + letter;
            Log.d("test wordguess: ", wordGuess);
            lexicon.filterList(wordGuess);
            //Log.d("test filter in guess: ", filteredLexicon.toString());
            //lexicon.filterList(wordGuess);
        }
        return wordGuess;
    }

    public int turn(){
        // als getal even is; player1
        Log.d("test in turn", String.valueOf(countTurns));
        if ( (countTurns & 1) == 0 ) {
            countTurns++;
            return player1;
        } // als getal oneven is; player2
        else {
            countTurns++;
            return player2;
        }
    }

    public boolean ended(){
        // spel is over als: speler heeft letter gelegd die de filter array size op 0 brengt
        // speler die woord afmaakt, langer dan drie letters
        if (countTurns != 0) {
            if (lexicon.count() == 0) {
                Log.d("test: ", "no more words possible");
                return true;
            } else if (countTurns > 3) {
                for (int i = 0; i < lexicon.count(); i++) {
                    Log.d("test words possible: ", lexicon.result(i));
                    if (lexicon.result(i).equals(wordGuess)) {
                        Log.d("test word is complete: ", wordGuess);
                        return true;
                    }
                }
            } //else {
              //  return false;
            //}
        }
        //Log.d("test", "game ended is false");
        return false;
    }

    public int winner(){
        winner = turn();
        //Log.d("test winner: ", String.valueOf(winner));
        return winner;
    }

}
