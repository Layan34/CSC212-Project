package searchengine;

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
