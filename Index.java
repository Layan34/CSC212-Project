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
            System.out.print("\nID: "+ documents.retrieve().id+"\n");
            
            documents.retrieve().words.display();
            documents.findNext();
        }
        
        System.out.print("\nID: "+ documents.retrieve().id+"\n");
         documents.retrieve().words.display();
            
    }
}
