/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package searchengine;

/**
 *
 * @author Layan saad
 */
public class Node<T>{
    public T data;
    public Node<T> next;
    
    
    public Node (T Val){
        data = Val;
        next=null;
        
    }
}
