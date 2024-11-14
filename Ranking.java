
package searchengine;
public class Ranking {
    static String query;
    static InvertedIndexBST inv;
    static Index in;
    static LinkedList<Integer> allDocInQuery;
    static LinkedList<DocRaking> allDocRanked;
    
    public Ranking(InvertedIndexBST inv ,Index in , String query ){
        this.inv=inv;
        this.in = in;
        this.query=query;
        allDocInQuery= new LinkedList<Integer>();
        allDocRanked= new  LinkedList<DocRaking>();
    }
     
    public static void displayAllDocWithScore(){
        if(allDocRanked.empty()){
            System.out.println("Empty.");
            return;
        }
       System.out.printf("%-8s%-8s\n","DocID","Score");
       
       allDocRanked.findFirst();
       while(!allDocRanked.last()){
           allDocRanked.retrieve().display();
           allDocRanked.findNext();
       }
           allDocRanked.retrieve().display();

        
    }
    
    public static Document getDocByID(int id){
        return in.getDocFromID(id);
    }
    
    public static int termFreqInDoc(Document d,String term){
        int freq =0;
        LinkedList<String>words=d.words;
        if(words.empty())
            return 0;
        words.findFirst();
        while(!words.last())
        {
            if(words.retrieve().equalsIgnoreCase(term))
                freq++;
            words.findNext();
        }
        if(words.retrieve().equalsIgnoreCase(term))
                freq++;
        return freq;
    }
    
    public static int getDocRankScore(Document d,String query){
        if(query.length()==0)
            return 0;
        String T[] = query.split(" ");
        int sum =0;
        for(int i=0;i<T.length;i++)
            sum+=termFreqInDoc(d,T[i].trim().toLowerCase());
        return sum;
    }
     public static void RanQuery(String query){
         LinkedList<Integer> l= new LinkedList<Integer>();
         if(query.length()==0)
             return;
         String T[] = query.split(" ");
         boolean found = false;
         for(int i=0; i<T.length;i++)
         {
             found=inv.search_word_in_inverted(T[i].trim().toLowerCase());
             if(found)
                 l=inv.invertedIndex.retrieve().documentIDs;
             AddInList(l);
         }
         
     }
    public static void AddInList(LinkedList<Integer> l){
         if(l.empty())
             return;
         l.findFirst();
         while(!l.empty()){
             boolean found= Inruslt(allDocInQuery,l.retrieve());
         if(!found)
             insertIDs(l.retrieve());
         if(!l.last())
             l.findNext();
         else
             break;
             
     }
    }
    
    public static boolean Inruslt(LinkedList<Integer>rus,Integer id){
        if(rus.empty())
            return false;
        rus.findFirst();
        while(!rus.last()){
            if(rus.retrieve().equals(id))
                return true;
            rus.findNext();
            
        }
        if(rus.retrieve().equals(id))
                return true;

        return false;
    }
    
    public static void insertIDs( Integer id){
        if(allDocInQuery.empty()){
            allDocInQuery.insert(id);
            return;          
        }
       allDocInQuery.findFirst();
       while(!allDocInQuery.last())
       {
           if(id<allDocInQuery.retrieve()){
               Integer id1 = allDocInQuery.retrieve();
               allDocInQuery.update(id);
               allDocInQuery.insert(id1);
               return;
               
           }
           
          else
               allDocInQuery.findNext();
       }
        if(id<allDocInQuery.retrieve()){
               Integer id1 = allDocInQuery.retrieve();
               allDocInQuery.update(id);
               allDocInQuery.insert(id1);
               return;
               
           }
           
          else
               allDocInQuery.findNext();
       
    }
    
    public static void insertStoredList(){
       RanQuery(query);
       if(allDocInQuery.empty()){
           System.out.println("Empty.");
           return;
        }
       allDocInQuery.findFirst();
       while(!allDocInQuery.last()){
           Document document = getDocByID(allDocInQuery.retrieve());
           int r =getDocRankScore(document,query);
           insertSorted(new DocRaking(allDocInQuery.retrieve(),r));
       }
        Document document = getDocByID(allDocInQuery.retrieve());
        int r =getDocRankScore(document,query);
        insertSorted(new DocRaking(allDocInQuery.retrieve(),r));
    }
    
    public static void insertSorted(DocRaking documentRank){
        if(allDocRanked.empty())
        {
            allDocRanked.insert(documentRank);
            return;
        }
        allDocRanked.findFirst();
        while(!allDocRanked.last()){
            if(documentRank.rank>allDocRanked.retrieve().rank){
               DocRaking docR=allDocRanked.retrieve();
               allDocRanked.update(documentRank);
               allDocRanked.insert(docR);
               return;
            }
            else
               allDocRanked.findNext();
        }
        if(documentRank.rank>allDocRanked.retrieve().rank){
               DocRaking docR=allDocRanked.retrieve();
               allDocRanked.update(documentRank);
               allDocRanked.insert(docR);
               return;
        
         }
        else
            allDocRanked.insert(documentRank);
    
}
}
