package searchengine;

import java.io.File;
import java.util.Scanner;

public class SearchEngine {
    
    // Declares variables for various data structures used in the program
    LinkedList<String> stopwords;
    static Index index;
    InvertedIndex invertedIndex;
    InvertedIndexBST invertedIndexBST;
    int numTokens =0;
    LinkedList<String>uniqueWord = new LinkedList<>();   
    
   // Constructor to initialize data structures
    public SearchEngine () {
        stopwords=new LinkedList<>();
        index=new Index();
        invertedIndex=new InvertedIndex();
        invertedIndexBST=new InvertedIndexBST();
    }

    // Method to load stop words from a file
     public void LoadStop(String Name){
        String line;
        try{
            File f = new File(Name);
            Scanner read = new Scanner(f);
            while(read.hasNextLine()){
                line=read.nextLine();
                stopwords.insert(line); // Insert stop word into the list
                //System.out.println(line);
            }

        }
        catch(Exception em){
            System.out.println("File is ended.");
        }

    }


    // Method to load documents from a file and index them
    public void LoadFile(String Name){
        String line;
        try{
            File f = new File(Name);
            Scanner read = new Scanner(f);
            read.nextLine(); //to skip first line

            while(read.hasNextLine()){
                line=read.nextLine();

                if(line.trim().length()<3)
                    break; //empty line
                //System.out.println(line);
                String sID = line.substring(0, line.indexOf(',')).trim();
                int ID = Integer.parseInt(sID);
                String data = line.substring(line.indexOf(',')+1).trim();
                LinkedList <String> wordsInDoc= createInvIndList(data, ID); //Inverted index (words and IDs of docs it appeared in)
                index.addDoc(new Document(ID, wordsInDoc,data)); //linked list of docs (with data & IDs)
                countTokAndUni(data);

            }
        }
        catch(Exception em){
            System.out.println("File is ended.");
        }
    }
    // Method to display documents based on their IDs
    public void displayDocsFromIDs(LinkedList<Integer> IDs){
        if(IDs.empty()){
        System.out.println("Empty.");
        return;
        }

        IDs.findFirst();
        while(!IDs.last()){
            Document doc=index.getDocFromID(IDs.retrieve());
            if(doc!=null){
                System.out.println("Document "+doc.id+":"+doc.word);
            }
            IDs.findNext();
        }
        Document doc=index.getDocFromID(IDs.retrieve());
        if(doc!=null)
            System.out.println("Document " + doc.id + ":" + doc.word);
        System.out.println("");
        //System.out.println("in displayDocsFromIDs");

    }

    // Method to check if a word is a stop word
    public boolean isStopWord(String w){
        if(stopwords.empty())
        return false;

        stopwords.findFirst();
        while(!stopwords.last()){
            if(stopwords.retrieve().equals(w))
            return true;
            stopwords.findNext();
        }
        if(stopwords.retrieve().equals(w))
            return true;

            return false;
    }

     // Method to create an inverted index list for a document
     public void CreateIndAndInv(String data,LinkedList<String>wInDoc,int id){
        while(data.contains("-")){
            if(data.charAt(data.indexOf("-")-2)==' '){
                data = data.replaceFirst("-", "");
            }
           else
                data = data.replaceFirst("-", " ");
        }
        
       
        data=data.toLowerCase().replaceAll("[^a-zA-Z0-9 ]","");
        String[]tokens=data.split("\\s+");
        for(String w:tokens){
            if(!isStopWord(w)){
                wInDoc.insert(w);
                invertedIndex.addToInverted(w, id);
                invertedIndexBST.add(w, id);
            }
        }
    }
     // Method to count tokens and unique words in a document
    public void countTokAndUni(String data){
         data = data.toLowerCase().replaceAll("\'"," ");
             data = data.toLowerCase().replaceAll("-"," ");
             data = data.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");
             String[] tokens = data.split("\\s+");
             numTokens+=tokens.length;
             
               for(String w : tokens){
               if(!uniqueWord.exist(w)){
               uniqueWord.insert(w);
        }}}
    
    // Method to create an inverted index list for a document
    public LinkedList<String> createInvIndList (String d, int id){
        LinkedList<String> wordsInDoc = new LinkedList<String>();
        CreateIndAndInv(d,wordsInDoc,id);
        return wordsInDoc;
    }

        
    public static void main(String[] args) {
        SearchEngine s = new SearchEngine();
        s.LoadStop("stop.txt");
        s.LoadFile("dataset.csv");
        Scanner in =new Scanner(System.in);
        int ch=0;
        int sh=0;
        // User interface for the search engine
        do{
               System.out.println("1-Retrieve a term");
               System.out.println("2-Boolean retrieval");
               System.out.println("3-Ranked retrieval");
               System.out.println("4-Indexed documents:print all documents");
               System.out.println("5-Number of documents in the index");
               System.out.println("6-Number of unigue words in indexed");
               System.out.println("7-Show inverted index with list of lists");
               System.out.println("8-Show inverted index with BST");
               System.out.println("9-Indexed token:to show number of vocabulary and token in the index.");
               System.out.println("10-Exit");
                 ch=in.nextInt();
          switch(ch)
          {
              case 1:
                System.out.println("Enter a term to retrieve");
                String term=in.next();
                term=term.toLowerCase().trim();
                
              do{
                     System.out.println("1-Using index with lists");
                     System.out.println("2-Inverted index with lists");
                     System.out.println("3-inverted index with BST.");
                     System.out.println("4-Exit");
                     
                   sh=in.nextInt();
                 
                switch(sh){
                    case 1: 
                            System.out.println("Using index with lists:");
                            LinkedList<Integer>res=SearchEngine.index.get_all_documents_given_term(term);
                            System.out.println("word: "+term);
                            res.display();
                            System.out.println();
                            break;
                    case 2:
                           System.out.println("Inverted index with lists");
                           boolean found=s.invertedIndex.isInInverted(term);
                           if(found)
                            s.invertedIndex.invertedIndex.retrieve().displayWordIds();
                           else
                           System.out.println("not found in inverted index with lists");
                           break;
                    case 3:
                        System.out.println("Inverted index with BST.");    
                        boolean found2=s.invertedIndexBST.search_word_in_inverted(term);
                        if(found2)
                        s.invertedIndex.invertedIndex.retrieve().displayWordIds();
                        break;
                    
                     case 4:
                         break;
                     default:
                        System.out.println("Worng query"); 
                  }}while(sh!=4);
                 break; 
               
           
               case 2:
                in.nextLine();
                System.out.println("Enter a query to retrieve");
                String query=in.nextLine();
                query=query.toLowerCase();
                query = query.replaceAll("\\b(and)\\b", "AND");
                query = query.replaceAll("\\b(or)\\b", "OR");
                do{
                    System.out.println("Which method you want to make query enter:");
                     System.out.println("1-Using index");
                     System.out.println("2-Using inverted index list of lists");
                     System.out.println("3-Using BST");
                     System.out.println("4-Exit");
                     
                   sh=in.nextInt();
                 
                switch(sh){
                      
                    case 1: 
                            QueryProcessing_form_index q=new QueryProcessing_form_index(SearchEngine.index);
                             System.out.println("=========="+query+"==========");
                             LinkedList<Integer>res1=QueryProcessing_form_index.MixedQuery(query);
                             s.displayDocsFromIDs(res1);

                            break;
                    case 2:
                            QueryProcessor q2=new QueryProcessor(s.invertedIndex);
                             System.out.println("=========="+query+"==========");
                             LinkedList<Integer>res2=QueryProcessor.MixedQuery(query);
                             s.displayDocsFromIDs(res2);
                          
                           break;
                    case 3:
                            QueryProcessorBST q3=new QueryProcessorBST(s.invertedIndexBST);
                            System.out.println("=========="+query+"==========");
                            LinkedList<Integer>res3=QueryProcessorBST.MixedQuery(query);
                             s.displayDocsFromIDs(res3);
                        break;
                    
                     case 4:
                         break;
                     default:
                        System.out.println("Worng query"); 
                  }}while(sh!=4);
                 break; 
               
              case 3:
                  in.nextLine();
                  System.out.println("Enter a query to rank");
                   String query2=in.nextLine();
                    query2=query2.toLowerCase();
                    Ranking r5=new Ranking (s.invertedIndexBST,index,query2);
                    r5.insertStoredList();
                    r5.displayAllDocWithScore();
                    break;
              case 4:
                  s.index.displayDocs();
                    System.out.println("------------------------------");
                    break;
              case 5:
                    System.out.println("Number of documents="+SearchEngine.index.documents.s);
                    System.out.println("------------------------------");
                     break;
              case 6:
                  System.out.println("Number of unique words without stop words="+s.invertedIndex.invertedIndex.s);
                  System.out.println("------------------------------");
                break;
              case 7:
                  s.invertedIndex.displayInvertedIndex();
                  System.out.println("------------------------------");

                  break;
              case 8:
                  s.invertedIndexBST.display_invertedIndex();
                  System.out.println("------------------------------");

                  break;
              case 9:
                  System.out.println("num of token="+s.numTokens);
                  System.out.println("num of unique words including stop words"+s.uniqueWord.s);
                  System.out.println("------------------------------");

                  break;
              case 10:
                   System.out.println("Goodbye.");
                   break ;
              default:
                  System.out.println("error input try again");
          }
        }while(ch!=10);
                  

    }
    
}