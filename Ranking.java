
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
     
        // Method to display the ranking of documents with their scores.

    public static void displayAllDocWithScore(){
        if(allDocRanked.empty()){
            System.out.println("Empty.");
            return;
        }
        System.out.println("\nRanking of term : "+query) ;
       System.out.printf("%-8s%-8s\n","DocID","Score");
       
       allDocRanked.findFirst();
       while(!allDocRanked.last()){
           allDocRanked.retrieve().display();
           allDocRanked.findNext();
       }
           allDocRanked.retrieve().display();

        
    }
    
        // Method to retrieve a document by its ID from the main index.
    public static Document getDocByID(int id){
        return in.getDocFromID(id);
    }
    
        // Method to calculate the term frequency of a word in a specific document.
    public static int termFreqInDoc(Document d,String term){
        int freq =0;
        LinkedList<String>words=d.words; //words in this doc (to see term freq in it then rank each doc)
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
    
        // Method to calculate the rank score of a document based on the query terms.
    public static int getDocRankScore(Document d,String query){
        if(query.length()==0)
            return 0;
        String T[] = query.split(" ");
        int sum =0;
        for(int i=0;i<T.length;i++)
            sum+=termFreqInDoc(d,T[i].trim().toLowerCase());
        return sum;
    }
    
        // Method to process the query and find the documents that contain the query terms.
     public static void RanQuery(String query){
         LinkedList<Integer> l= new LinkedList<Integer>();
         if(query.length()==0)
             return;
         String T[] = query.split("\\s+");
         boolean found = false;
         for(int i=0; i<T.length;i++)
         {
             found=inv.search_word_in_inverted(T[i].trim().toLowerCase());
             if(found)
                 l=inv.invertedIndex.retrieve().documentIDs;
             AddInList(l);
         }
         
     }
     
    // Method to add document IDs from a list to the list of documents in the query, ensuring no duplicates.

    public static void AddInList(LinkedList<Integer> l){
         if(l.empty())
             return;
         l.findFirst();
         while(!l.empty()){
             boolean found= Inruslt(allDocInQuery,l.retrieve());
         if(!found)
             allDocInQuery.insert(l.retrieve());
         if(!l.last())
             l.findNext();
         else
             break;
     }
    }
    
        // Method to check if a document ID is already in the results list.
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

        // Method to fill the list of documents with scores based on the query.
    public static void insertStoredList(){ 
       RanQuery(query);
       if(allDocInQuery.empty()){
           System.out.println("The query is empty.");
           return;
        }
       allDocInQuery.findFirst();
       while(!allDocInQuery.last()){
           Document document = getDocByID(allDocInQuery.retrieve());
           int r =getDocRankScore(document,query);
           insertSorted(new DocRaking(allDocInQuery.retrieve(),r));
                  allDocInQuery.findNext();

       }
        Document document = getDocByID(allDocInQuery.retrieve());
        int r =getDocRankScore(document,query);
        insertSorted(new DocRaking(allDocInQuery.retrieve(),r));
    }
    
    
        // Method to insert a ranked document into the list of ranked documents, maintaining the order of scores.

    public static void insertSorted(DocRaking documentRank){
        if (allDocRanked.empty()) {
            
            allDocRanked.insert(documentRank);
            return;
        }

        allDocRanked.findFirst();
        while (!allDocRanked.last()) {
            
            if (documentRank.rank > allDocRanked.retrieve().rank) {
                DocRaking docR = allDocRanked.retrieve();
                allDocRanked.update(documentRank); 
                allDocRanked.insert(docR); 
                return;
            }

            else
            if(documentRank.rank==allDocRanked.retrieve().rank)
            {
                while(!allDocRanked.last() && documentRank.rank==allDocRanked.retrieve().rank && documentRank.id>allDocRanked.retrieve().id)
                    allDocRanked.findNext(); 


                if(!allDocRanked.last() || documentRank.id<allDocRanked.retrieve().id) {

                    DocRaking docR = allDocRanked.retrieve();
                    allDocRanked.update(documentRank); 
                    allDocRanked.insert(docR);
                    return;
                }


                else {
                    allDocRanked.insert(documentRank);
                    return;

                }
            }
            else
                allDocRanked.findNext();

        }

        // check last doc if loop not entered
        if (documentRank.rank > allDocRanked.retrieve().rank) {
            DocRaking docR = allDocRanked.retrieve();
            allDocRanked.update(documentRank); 
            allDocRanked.insert(docR); 
            return;
        }
        else {
            allDocRanked.insert(documentRank);
        }






}
    
}
