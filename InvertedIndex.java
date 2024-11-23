
package searchengine;

public class InvertedIndex {
    
    LinkedList<Word> invertedIndex;
    
    public InvertedIndex(){
        invertedIndex = new LinkedList<Word>();
    }
    
        // Method to check if a word is already in the inverted index.
    public boolean isInInverted (String word){
        if(invertedIndex==null||invertedIndex.empty())
            return false;
        invertedIndex.findFirst();
        while(!invertedIndex.last()){
            if(invertedIndex.retrieve().word.equals(word)){
                return true;
            }
            invertedIndex.findNext();
            
        }
        if(invertedIndex.retrieve().equals(word)){
                return true;
           }
        return false;
    }
    
        // Method to add a word along with its document ID to the inverted index.

    public void addToInverted(String word, int id){
        if(!isInInverted(word)){
            Word w1 = new Word(word);
            w1.documentIDs.insert(id);
            invertedIndex.insert(w1);
            
        }
        else{
            Word w2 = invertedIndex.retrieve();
             w2.addID(id);
        }
    }
    
        // Method to display the contents of the inverted index.
    public void displayInvertedIndex(){
        if(invertedIndex.empty()){
            System.out.println("Inverted index is empty.");
            return;
         }
        invertedIndex.findFirst();
        while(!invertedIndex.last()){
            invertedIndex.retrieve().displayWordIds();
            invertedIndex.findNext();
        }
        invertedIndex.retrieve().displayWordIds();
     }
            
}
