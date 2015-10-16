package nl.iris_meerman.ghost;

import android.content.Context;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* Lexicon.java
 * This class takes care of reading the text file (=lexicon) and putting it into an hashset.
 * It also takes care of the filtering process when users submit letters.
 */

public class Lexicon {
    Set<String> lexiconSet;
    ArrayList<String> arrayLexicon = new ArrayList<>();
    Iterator<String> it;
    ArrayList<String> filteredLexicon = new ArrayList<>();

    // 'Lexicon' opens the lexicon file and activates fillHashSet.
    Lexicon(int sourcePath, Context context) {
        try {
            InputStream openLexicon = context.getResources().openRawResource(sourcePath);
            fillHashSet(openLexicon, arrayLexicon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 'fillHashSet' fills the arrayLexicon based on the opened lexicon and puts this array to the set.
    public boolean fillHashSet(InputStream openLexicon, ArrayList arrayLexicon ){
        if (openLexicon != null) {
            InputStreamReader inputReader = new InputStreamReader(openLexicon);
            BufferedReader buffReader = new BufferedReader(inputReader);
            String line = "";
            try {
                while ((line = buffReader.readLine()) != null) {
                    arrayLexicon.add(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            lexiconSet = new HashSet<>(arrayLexicon);
        }
        return true;
    }

    // 'filter' filters the hashset based on the given input so far by the user.
    // If it is the first letter, words that can still be formed with that letter are
    // added to an arraylist. The next steps are removing letters from the arraylist
    // based on the letter input.
    public void filter(String word) {
        if (word.length() == 1) {
            for (it = lexiconSet.iterator(); it.hasNext(); ) {
                String f = it.next();
                if (f.startsWith(word)) {
                    filteredLexicon.add(f);
                }
            }
        } else {
            for (int elem = 0; elem < filteredLexicon.size(); elem++) {
                if (!(filteredLexicon.get(elem).startsWith(word))) {
                    filteredLexicon.remove(elem);
                    elem -= 1;
                }
            }
        }
    }

    // 'count' returns the amount of words that can still be formed with the given input
    public int count(){
        return filteredLexicon.size();
    }

    // 'result' gives the word at a certain position in the filtered array
    public String result(int element){
        return filteredLexicon.get(element);
    }

}