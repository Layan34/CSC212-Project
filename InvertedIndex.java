/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package searchengine;

/**
 *
 * @author Layan saad
 */
public class InvertedIndex {
    
    LinkedList<Word> invertedIndex;
    
    public InvertedIndex(){
        invertedIndex = new LinkedList<Word>();
    }
    
    public boolean isInInverted (String word){
        if(invertedIndex.empty())
            return false;
        invertedIndex.findFirst();
        while(!invertedIndex.last()){
            if(invertedIndex.retrieve().word.equals(word))
                return true;
            invertedIndex.findNext();
            
        }
        if(invertedIndex.retrieve().word.equals(word))
                return true;
        
        return false;
    }
    
    public void addToInverted(String word, int id){
        if(!isInInverted(word)){
            Word w1 = new Word(word);
            w1.documentIDs.insert(id);
            invertedIndex.insert(w1);
            
        }
        else{
            Word w2 = invertedIndex.retrieve();
            w2.documentIDs.insert(id);
        }
    }
    
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
