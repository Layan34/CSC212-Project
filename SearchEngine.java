
package csc212.project1;


import java.io.File;
import java.util.Scanner;

public class SearchEngine {

    LinkedList<String> stopwords;
    static Index index;
    InvertedIndex invertedIndex;
    InvertedIndexBST invertedIndexBST;
    int numTokens =0;
    LinkedList<String>uniqueWord = new LinkedList<>();    public SearchEngine () {
        stopwords=new LinkedList<>();
        index=new Index();
        invertedIndex=new InvertedIndex();
        invertedIndexBST=new InvertedIndexBST();
    }


    public void LoadStop(String Name){
        String line;
        try{
            File f = new File(Name);
            Scanner read = new Scanner(f);
            read.nextLine(); //to skip first line
            while(read.hasNextLine()){
                line=read.nextLine();
                stopwords.insert(line);
                //System.out.println(line);
            }

        }
        catch(Exception em){
            System.out.println("File is ended.");
        }

    }



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
            }
        }
        catch(Exception em){
            System.out.println("File is ended.");
        }
    }

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

    public LinkedList<String> createInvIndList (String d, int id){
        LinkedList<String> wordsInDoc = new LinkedList<String>();
        CreateIndAndInv(d,wordsInDoc,id);
        countTokAndUni(d);
        return wordsInDoc;
    }
public static void displayMenu(){
    System.out.println("retrieve a term (there are choices"
    +":using index with lists"
    +":-inverted index with lists"
    +"-inverted index with BST.");
    System.out.println("2-boolean retrieval");
    System.out.println("3-ranked retrieval");
    System.out.println("4-indexed documents:print all documents");
    System.out.println("5-number of documents in the index");
    System.out.println("6-number of unigue words in indexed");
    System.out.println("7-show inverted index with list of lists");
    System.out.println("8-show inverted index with BST");
    System.out.println("9-indexed token:to show number of vocabulary and token in the index.");
    System.out.println("10-exit");
    
}

public static void TestingMenu(){
    SearchEngine s = new SearchEngine();
        s.LoadStop("stop.txt");
        s.LoadFile("dataset.csv");
        Scanner in =new Scanner(System.in);
        int ch=0;
        do{
          displayMenu();
          ch=in.nextInt();
          switch(ch)
          {
              case 1:
                System.out.println("enter a term to retrieve");
                String term=in.next();
                term=term.toLowerCase().trim();
                System.out.println(":using index with lists");
                LinkedList<Integer>res=SearchEngine.index.get_all_documents_given_term(term);
                System.out.println("word:"+term+"{");
                res.display();
                System.out.println("}");
                System.out.println("------------------------------");
                System.out.println(":-inverted index with lists");
                boolean found=s.invertedIndex.isInInverted(term);
                if(found)
                    s.invertedIndex.invertedIndex.retrieve().displayWordIds();
                else
                     System.out.println("not found in inverted index with lists");
                
                System.out.println("-inverted index with BST.");    
                boolean found2=s.invertedIndexBST.search_word_in_inverted(term);
                if(found2)
                    s.invertedIndex.invertedIndex.retrieve().displayWordIds();
                break;
              case 2:
                in.nextLine();
                System.out.println("enter a query to retrieve");
                String query=in.nextLine();
                query=query.toLowerCase();
                query=query.replaceAll("and", "AND");
                query=query.replaceAll("or", "OR");
                System.out.println("\n which method you want to make query enter :\n"
                +"1-for using index\n"
                        +"2-for using inverted index list of lists \n"
                        +"3-for using BST\n");
                
                     int x=in.nextInt();
                     do{
                         if(x==1){
                             QueryProcessing_form_index q=new QueryProcessing_form_index(SearchEngine.index);
                             System.out.println("=========="+query+"==========");
                             LinkedList<Integer>res1=QueryProcessing_form_index.MixedQuery(query);
                             s.displayDocsFromIDs(res1);

                             
                         }
                         else if (x==2){
                             QueryProcessor q=new QueryProcessor(s.invertedIndex);
                             System.out.println("=========="+query+"==========");
                             LinkedList<Integer>res1=QueryProcessor.MixedQuery(query);
                             s.displayDocsFromIDs(res1);
                         }
                         else if (x==3){
                             QueryProcessorBST q=new QueryProcessorBST(s.invertedIndexBST);
                             System.out.println("=========="+query+"==========");
                             LinkedList<Integer>res1=QueryProcessorBST.MixedQuery(query);
                             s.displayDocsFromIDs(res1);
                     }
                         else if (x==4)
                             break;
                         else
                            System.out.println("worng query"); 
                         
                         System.out.println("\n which method you want to make query enter :\n"
                                    +"1-for using index\n"
                                     +"2-for using inverted index list of lists \n"
                                        +"3-for using BST\n");
                         x=in.nextInt();
                     }while(x!=4);
                     
                break;
              case 3:
                  in.nextLine();
                  System.out.println("enter a query to rank");
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
                    System.out.println("num of documents="+SearchEngine.index.documents.n);
                    System.out.println("------------------------------");
                     break;
              case 6:
                  System.out.println("num of unique words without stop words="+s.invertedIndex.invertedIndex.n);
                  System.out.println("------------------------------");
                break;
              case 7:
                  s.invertedIndex.displayInvertedIndex();
                  break;
              case 8:
                  s.invertedIndexBST.display_invertedIndex();
                  break;
              case 9:
                  System.out.println("num of token="+s.numTokens);
                  System.out.println("num of unique words including stop words"+s.uniqueWord.n);
                  break;
              case 10:
                   System.out.println("goodbye");
                   break ;
              default:
                  System.out.println("error input try again");
          }
        }while(ch!=10);
                  

                
          }
        
    public static void main(String[] args) {
//        SearchEngine s = new SearchEngine();
//        s.LoadStop("stop.txt");
//        s.LoadFile("dataset.csv");
//        System.out.println("\nIndex:");
//        s.index.displayDocs();
//        System.out.println("\nInverted Index:");
//        s.invertedIndex.displayInvertedIndex();
//        InvertedIndexBST indexBST=new InvertedIndexBST();
//        indexBST.add_from_inverted_list(s.invertedIndex);
//
//        Ranking R = new Ranking(indexBST,s.index,"a");
//        R.insertStoredList();
//        R.displayAllDocWithScore();
//        QueryProcessorBST qBST=new QueryProcessorBST(s.invertedIndexBST);
//        LinkedList<Integer> resBST=qBST.OR("surprising OR football");
//        QueryProcessor query = new QueryProcessor(s.invertedIndex);
//        LinkedList<Integer> resAND = query.AND("championship AND squad");
//        LinkedList<Integer> resOR = QueryProcessor.OR("market OR global");
//        System.out.println("\nResults of query: championship AND squad");
//        s.displayDocsFromIDs(resAND);
//        System.out.println("\nResults of query: surprising OR football");
//        s.displayDocsFromIDs(resBST);
//        
//        System.out.println("\n\nInverted Index by BST:");
//        s.invertedIndexBST.display_invertedIndex();
        TestingMenu();
    }
    
}