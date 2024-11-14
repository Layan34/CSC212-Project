
package searchengine;

public class DocRaking {
    int id;
    int rank;
    
   public DocRaking( int id, int rank){
       this.id=id;
       this.rank=rank;
   }
   
   public void display(){
       System.out.printf("%-8d%-8d\n",id,rank);
   }}
