package nl.iris_meerman.ghost;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Lexicon {
    Set<String> lexiconSet;
    ArrayList<String> arrayLexicon = new ArrayList<>();
    Iterator<String> it, woop;
    ArrayList<String> filteredLexicon = new ArrayList<>();

    Lexicon(int sourcePath, Context context) {
        try {
            InputStream instream = context.getResources().openRawResource(sourcePath);
            fillHashSet(instream, arrayLexicon);
        } catch (Exception e) {
            //Log.d("Test: ", "gefaald");
            String error = "";
            error = e.getMessage();
            e.printStackTrace();
        }
        //filter("aahin");
        //filteredLexicon = new ArrayList<>();
        if (count() == 1){
            String result = result(0);
            Log.d("test result: ", result);
        }
    }

    // Fill hashset fills the arrayLexicon based on the instream and adds this array to the set.
    public boolean fillHashSet(InputStream instream, ArrayList arrayLexicon ){
        Log.d("test", "in fillhashset");

        if (instream != null) {
            InputStreamReader inputreader = new InputStreamReader(instream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line = "";
            try {
                while ((line = buffreader.readLine()) != null) {
                    arrayLexicon.add(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            lexiconSet = new HashSet<String>(arrayLexicon);
            Log.d("test: ", "hashset gevuld");
            Log.d("test hashset size: ", String.valueOf(lexiconSet.size()));
        }
        return true;
    }

    public void filter(String word){
        for (it = lexiconSet.iterator(); it.hasNext(); ) {
            String f = it.next();
            if (f.startsWith(word)) {
                filteredLexicon.add(f);
            }
        }
        //Log.d("test filtered list: ", filteredLexicon.toString());
        //return filteredLexicon;
    }

    public void filterList(String word){
        for (int elem = 0; elem < filteredLexicon.size(); elem++){
            if (!(filteredLexicon.get(elem).startsWith(word))){
                filteredLexicon.remove(elem);
                elem -= 1;
            } else {
                //Log.d("test words left: ", filteredLexicon.get(elem));
            }
        }
        //return filteredLexicon;
    }

    public int count(){
        return filteredLexicon.size();
    }

    public String result(int element){
        return filteredLexicon.get(element);
    }

}