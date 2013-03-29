package edinburgh;

import java.util.ArrayList;
import java.util.HashMap;

public class WordStatistics {

    ArrayList<String> words;
    HashMap<String, Integer> statsMap=new HashMap();
        String statistics;

    public WordStatistics() {
        words=new ArrayList();
        statsMap=new HashMap();
    }
    public WordStatistics sortWords (){
        return sortWords (this);
            }

    public static WordStatistics sortWords(WordStatistics stats){
        System.out.println(stats.words.size());
         if (stats.words.size() <= 1) {
               return stats;
        }  // an array of zero or one elements is already sorted
     String pivot = stats.words.get(stats.words.size()-1); // select and remove a pivot value 'pivot' from 'array'
     WordStatistics less=new WordStatistics();
     WordStatistics more=new WordStatistics(); // create empty lists 'less' and 'greater'
      for (String  x : stats.words){
          if (!x.equals(pivot)) {
            
          if (stats.statsMap.get(pivot)>stats.statsMap.get(x)) { //check if pivot is added twice (in addStatistic)
              less.addStatistic(x, stats.statsMap.get(x));
         
          }
          else {
           
              more.addStatistic(x, stats.statsMap.get(x));}
          }
      }
            return (concatenate(sortWords(more), pivot, stats.statsMap.get(pivot), sortWords(less))); // two recursive calls
    }
    
    public void addStatistic(String s, int i){
        words.add(s);
        statsMap.put(s, i);
        
    }
    
    public static WordStatistics concatenate(WordStatistics f, String s, int c, WordStatistics t){
        WordStatistics newStats=new WordStatistics();

        for (int i =0;i<f.size(); i++){
       newStats.addStatistic(f.get(i), f.statsMap.get(f.get(i)));
        }
        newStats.addStatistic(s, c);
         for (int i =0;i<t.size(); i++){
       newStats.addStatistic(t.get(i), t.statsMap.get(t.get(i)));
        }
              return newStats;

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
     
    
    public void printStatsTerminal() {
        if (!words.isEmpty()) {
     for (String word: words){
         printWordStats(word);
     }
        }
    }

    public void printWordStats(String toPrint) {
if (!statsMap.containsKey(toPrint)) {
            statsMap.put(toPrint, 0);
        }
        System.out.println("\"" + toPrint + "\"" + " was used " + statsMap.get(toPrint) + " times");
    }

    public String returnStats() {
        String output="";
       
        if (!words.isEmpty()) {
            for(String word: words){
            statistics = statistics + "\n " + word + " used " + statsMap.get(word) + " times";
        }
           }
        return output;
    }
    
    public static void main (String[] args){
        WordStatistics wordies = new WordStatistics();
        wordies.addString("hello hello hello hi, why how are are you you you you you, this this is is a a a a a a test test test");
     // wordies.printStatsTerminal();
        wordies.sortWords().printStatsTerminal();
    }
}
