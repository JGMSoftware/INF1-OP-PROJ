package edinburgh;

public class WordStatistics {

    Word head = new Word();
    int totalWords = 0;
    String statistics;

    public WordStatistics() {
        head = new Word();
        totalWords = 0;
    }

    public void addWord(String newWord, Word current) {
        if (current.word == null) {
            current.set(newWord);
            current.next = new Word();
            totalWords++;
        }

        if (newWord.equals(current.word)) {
            current.count++;
            totalWords++;
        } else {
            addWord(newWord, current.next);
        }


    }

     public void addString(String str) {
      
        String[] words = str.split(" "); //split string into words
        for (int i = 0; i < words.length; i++) {
            String strippedWord = (words[i].replaceAll("\\W", ""));//removes all non-alphanumeric characters.
            //Need to decapitalize the "strippedWord" here as well
            addWord(strippedWord, head);
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
