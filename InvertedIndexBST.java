

package searchengine;
public class InvertedIndexBST {
    BST<Word>invertedIndex;
    public InvertedIndexBST(){
        invertedIndex=new BST<Word>();
    }
    
        // Method to populate the BST from an existing InvertedIndex object.

    public void add_from_inverted_list(InvertedIndex inverted){
        if(inverted.invertedIndex.empty())
            return;
        inverted.invertedIndex.findFirst();
        while(!inverted.invertedIndex.last()){
            invertedIndex.insert(inverted.invertedIndex.retrieve().word,inverted.invertedIndex.retrieve());
            inverted.invertedIndex.findNext();    
        }
        invertedIndex.insert(inverted.invertedIndex.retrieve().word,inverted.invertedIndex.retrieve());
            
    }
        // Method to add a word and its document ID to the inverted index (BST).
    public void add( String word,int id ){
        if (!search_word_in_inverted(word)){
            Word w= new Word(word);
            w.documentIDs.insert(id);
            invertedIndex.insert(word, w);
        }
        else{
            Word indDoc=invertedIndex.retrieve();
            indDoc.addID(id);
        }
            
            
        }
    
        // Method to search for a word in the inverted index (BST).

    public boolean search_word_in_inverted(String w){
        return invertedIndex.findKey(w);
    }
    
        // Method to display the contents of the inverted index.

    public void display_invertedIndex(){
        if(invertedIndex==null){
            System.out.println("null invertedIndex");
            return;
        }
        else if(invertedIndex.empty()){
            System.out.println("empty invertedIndex");
            return;
        }
        invertedIndex.inOrder();
        }
    
    }
