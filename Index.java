/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package searchengine;

/**
 *
 * @author Layan saad
 */
public class Index {
    LinkedList<Document> documents;
    
    public Index(){
       documents = new LinkedList<Document>();
    }
    
    public void addDoc(Document doc){
        documents.insert(doc);
    }
    
    public void displayDocs(){
        if(documents.empty()){
            System.out.println("There are no available documents.");
            return;
        }
        documents.findFirst();
        while(!documents.last()){
            System.out.println("\nID: "+ documents.retrieve().id);
            
            documents.retrieve().words.display();
            documents.findNext();
        }
        
        System.out.println("\nID: "+ documents.retrieve().id);
         documents.retrieve().words.display();
            
    }
}
