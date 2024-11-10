package searchengine;

public class Node<T>{
    public T data;
    public Node<T> next;
    
    
    public Node (T Val){
        data = Val;
        next=null;
        
    }
}
