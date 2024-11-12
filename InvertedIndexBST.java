/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package searchengine;
public class InvertedIndexBST {
    BST<Word>invertedIndex;
    public InvertedIndexBST(){
        invertedIndex=new BST<Word>();
    }
    public void add_from_inverted_list(InvertedIndex inverted){
        if(inverted.invertedIndex.empty())
            return;
        inverted.invertedIndex.findFirst();
        while(!inverted.invertedIndex.last()){
            invertedIndex.insert(inverted.invertedIndex.retrieve().word,inverted.invertedIndex.retrieve());
            inverted.invertedIndex.findFirst();    
        }
        invertedIndex.insert(inverted.invertedIndex.retrieve().word,inverted.invertedIndex.retrieve());
            
    }
    public void add( String word,int id ){
        if (!invertedIndex.findKey(word)){
            Word w= new Word(word);
            w.documentIDs.insert(id);
            invertedIndex.insert(word, w);
        }
        else{
            Word indDoc=invertedIndex.retrieve();
            indDoc.addID(id);
        }
            
            
        }
    public boolean search_word_in_inverted(String w){
        return invertedIndex.findKey(w);
    }
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