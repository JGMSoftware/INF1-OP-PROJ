public class WordStatistics {
Word head=new Word();
int totalWords=0;

public WordStatistics(){
head=new Word ();
totalWords=0;
}

public void addWord (String newWord, Word current){
if (current.word==null) {
  current.set(newWord);
  current.next=new Word();
  totalWords++;
  }
 
if (newWord.equals(current.word)) {
  current.count++;
  totalWords++;}
else {addWord(newWord, current.next);}


}

public void printStats(){
if (head.word!=null){printWordStatsRec(head);}
}

public void printWordStatsRec(Word toPrint){
printWordStats(toPrint);
if (toPrint.next.word!=null) printWordStatsRec(toPrint.next);
}

public void printWordStats(Word toPrint){
System.out.println("\""+toPrint.word+"\"" + " was used "+toPrint.count+ " times");
}

}
