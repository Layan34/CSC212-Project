package searchengine;

public class Index {
    LinkedList<Document> documents;
    
    public Index(){
       documents = new LinkedList<Document>();
    }
    
     // Adds a document to the index.
    public void addDoc(Document doc){
        documents.insert(doc);
    }
        
        // Retrieves all document IDs containing a specific term.

    public LinkedList<Integer> get_all_documents_given_term(String term){
        LinkedList<Integer>res=new LinkedList<>();
        if(documents.empty()){
            System.out.println("no documents exist");
            return null;
        }
        documents.findFirst();
        while(!documents.last()){
            if(documents.retrieve().words.exist(term.toLowerCase().trim()))
                res.insert(documents.retrieve().id);
            documents.findNext();
        }
        if(documents.retrieve().words.exist(term.toLowerCase().trim()))
            res.insert(documents.retrieve().id);
        return res;
    }
    
        // Retrieves a document based on its ID.

    public Document getDocFromID (int ID){

        if(documents.empty()){
            System.out.println("Empty.");
            return null;
        }

        documents.findFirst();
        while(!documents.last()){
            if(documents.retrieve().id==ID)
            return documents.retrieve();

            documents.findNext();
        }
            if(documents.retrieve().id==ID)
            return documents.retrieve();

        
        return null;
    }



        // Displays all documents and their content.

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
