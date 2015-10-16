package nl.iris_meerman.ghost;

/* Game.java
 * The game class contains the regulations for the game. It is closely
 * related to the GameActivity, where the actual game is played. The methods
 * are called from that class. In this class, the letter guess of a player
 * is processed, who's turn it is is calculated here, who the winner is, and
 * whether the game has ended.
*/
public class Game {

    String wordGuess = "";
    int player1 = 1, player2 = 2, countTurns, winner;
    private Lexicon lexicon;

    // 'game' searches for the lexicon.
    public Game(Lexicon lexicon){
        this.lexicon = lexicon;
        countTurns = 0;
    }

    // 'guess' takes the given letter and checks which words can still be formed.
    public String guess(String letter){
        wordGuess = wordGuess + letter;
        lexicon.filter(wordGuess);
        return wordGuess;
    }

    // 'turn' returns an integer (1 or 2), depicting the player that is on turn.
    public int turn(){
        // if turn is an even number: player 1
        if ( (countTurns & 1) == 0 ) {
            countTurns++;
            return player1;
        } // if turn is an odd number: player 2
        else {
            countTurns++;
            return player2;
        }
    }

    // 'ended' checks if the game is over, which is the case when someone chose
    // a letter that makes it impossible to create a word, or someone finished
    // the word (longer than three letters).
    public boolean ended(){
        if (countTurns != 0) {
            if (lexicon.count() == 0) {
                return true;
            } else if (countTurns > 3) {
                for (int i = 0; i < lexicon.count(); i++) {
                    if (lexicon.result(i).equals(wordGuess)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // 'winner' only gets called when the game has ended. The one that was on turn
    // has won.
    public int winner(){
        winner = turn();
        return winner;
    }
}
