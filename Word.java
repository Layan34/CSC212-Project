package searchengine;

public class Word{
    String word ;
    LinkedList<Integer> documentIDs ;
    
    public Word(String w){
        word = w;
        documentIDs= new LinkedList<Integer>();
    }
    
    
    public boolean isInDoc(Integer id){
        if(documentIDs.empty())
            return false;
        
        documentIDs.findFirst();
        while(!documentIDs.last()){
            if(documentIDs.retrieve().equals(id))
                return true;
            
            documentIDs.findNext();
        }
        if(documentIDs.retrieve().equals(id))
                return true;
        return false;
    }
    public void addID(int id){
        if(!isInDoc(id))
            documentIDs.insert(id);
    }
    
    public void displayWordIds(){
        System.out.println("\nword: "+word);
        System.out.print("document IDs: ");
        System.out.print("[ ");
        documentIDs.display();
        System.out.print("]\n");
           
       
    }
    
    
   
    
}