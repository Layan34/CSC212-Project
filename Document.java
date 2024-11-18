package searchengine;

public class Document {
    LinkedList<String> words = new LinkedList<String>();
    int id ;
    String word;
    
    public Document(int id , LinkedList<String> words, String word){
        this.id = id;
        this.words=words;
        this.word=word;
        
    }
}
