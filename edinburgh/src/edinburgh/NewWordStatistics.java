package edinburgh;

import java.util.ArrayList;
import java.util.HashMap;

public class WordStatistics {

    ArrayList<String> words;
    HashMap<String, Integer> statsMap;
        String statistics;

    public WordStatistics() {
        words=new ArrayList();
    }

    public WordStatistics sortWords(WordStatistics stats){
         if (stats.words.size() <= 1)    return stats;  // an array of zero or one elements is already sorted
     String pivot = stats.words.get(stats.words.size()); // select and remove a pivot value 'pivot' from 'array'
     WordStatistics less=new WordStatistics();
     WordStatistics more=new WordStatistics(); // create empty lists 'less' and 'greater'
      for (String  x : stats.words){
          if (statsMap.get(pivot)>statsMap.get(x)) less.words.add(x);
          else more.words.add(x);
      }
      sortWords(less).addWord(pivot);
      return (concatenate( (), sortWords(more))); // two recursive calls
    }
    
    public WordStatistics concatinate(WordStatistics first, WordStatistics sec){
        for (int i =0;i<sec.size(); i++){
       first.addWord(sec.get(i));
        }
        return first;
    }
    
    public int size(){
        return words.size();
    }
    
    public String get(int i){
        return words.get(i);
    }
    

    
    public void addWord(String newWord) {
   

        if (words.contains(newWord)) {
           statsMap.put(newWord,(statsMap.get(newWord)+1));
          
        } else {
            statsMap.put(newWord, 1);
            words.add(newWord);
        }


    }

     public void addString(String str) {
      
        String[] words = str.split(" "); //split string into words
        for (int i = 0; i < words.length; i++) {
            String strippedWord = (words[i].replaceAll("\\W", ""));//removes all non-alphanumeric characters.
            //Need to decapitalize the "strippedWord" here as well
            addWord(strippedWord);
        }
     }
     
    
    public void printStats() {
        if (head.word != null) {
            printWordStatsRec(head);
        }
    }

    private void printWordStatsRec(Word toPrint) {
        printWordStats(toPrint);
        if (toPrint.next.word != null) {
            printWordStatsRec(toPrint.next);
        }
    }

    public void printWordStats(Word toPrint) {
        System.out.println("\"" + toPrint.word + "\"" + " was used " + toPrint.count + " times");
    }

    public String returnStats() {
        printWordStats2(head);
        return statistics;
    }

    public void printWordStats2(Word toPrint) {
        if (toPrint.word != null) {
            statistics = statistics + "\n " + toPrint.word + " used " + toPrint.count + " times";
        }
        if (toPrint.next != null) {
            printWordStats2(toPrint.next);
        }

    }
}
