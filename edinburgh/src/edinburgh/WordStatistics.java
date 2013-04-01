package edinburgh;

import java.util.ArrayList;
import java.util.HashMap;

public class WordStatistics {

   private ArrayList<String> words;
   private HashMap<String, Integer> statsMap=new HashMap();
   private     String statistics=new String("");

    public WordStatistics() {
        words=new ArrayList();
        statsMap=new HashMap();
    }
    public WordStatistics sortWords (){
        return sortWords (this);
            }
    
    public int getCount(String word){
        return statsMap.get(word);
    }
    
        public String getStats() {
        
       
        if (!words.isEmpty()) {
            for(String word: words){
            statistics = statistics + "\n " + word + " used " + statsMap.get(word) + " times";
        }
           }
        return statistics;
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
                   String[] w = str.split(" "); //split string into words
        for (int i = 0; i < w.length; i++) {
            String strippedWord = (w[i].replaceAll("\\W", ""));//removes all non-alphanumeric characters.
            String out="";
          for(int x=0;x<strippedWord.length();x++){
             out=out+Character.toLowerCase(strippedWord.charAt(x)); //decapitalises each letter
          }
addWord(out);
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


    
    public static void main (String[] args){
        WordStatistics wordies = new WordStatistics();
        String s = new String("Hello hello hello hi, why how are are you you you you you, this this is is a a a a a a test test test");
   wordies.addString(s);
   System.out.println(wordies.getStats());
    }
}
