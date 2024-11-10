/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package searchengine;

/**
 *
 * @author Layan saad
 */
public class Document {
    LinkedList<String> words = new LinkedList<String>();
    int id ;
    
    public Document(int id , LinkedList<String> words){
        this.id = id;
        this.words=words;
        
    }
}
