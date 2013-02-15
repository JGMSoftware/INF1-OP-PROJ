
public class WordCounter {


    /*public static void main (String[] args){
     String x =countWords("Hello World, hello Hello, oh my! So many of you");
     System.out.println(x);
     }
     */
    public static String countWords(String str) {
        WordStatistics stats = new WordStatistics();

        String[] words = str.split(" "); //split string into words
        for (int i = 0; i < words.length; i++) {
            String strippedWord = (words[i].replaceAll("\\W", ""));//removes all non-alphanumeric characters.
            //Need to decapitalize the "strippedWord" here as well
            stats.addWord(strippedWord, stats.head);
        }
        return stats.returnStats();

    }
}
