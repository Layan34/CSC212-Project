
package searchengine;

public class Document {
    LinkedList<String> words = new LinkedList<String>();
    int id ;
    
    public Document(int id , LinkedList<String> words){
        this.id = id;
        this.words=words;
        
    }
}
