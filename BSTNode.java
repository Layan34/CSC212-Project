
package searchengine;
    public class BSTNode<T>{
    public String key;
    public T data;
    public BSTNode<T> left,right;
    public BSTNode(String key,T data){
        this.key=key;
        this.data=data;
        left=right=null;
        
    }
    
}