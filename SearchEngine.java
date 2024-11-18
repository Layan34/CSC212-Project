
package searchengine;

import java.io.File;
import java.util.Scanner;

public class SearchEngine {

    LinkedList<String> stopwords;
    Index index;
    InvertedIndex invertedIndex;
    InvertedIndexBST invertedIndexBST;

    public SearchEngine () {
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
        data=data.toLowerCase().replaceAll("[^ A-Za-z0-9]","");
        String[]tokens=data.split("\\s+");
        for(int i=0; i<tokens.length;i++){
            String w=tokens[i];
            if(!isStopWord(w)){
                wInDoc.insert(w);
                invertedIndex.addToInverted(w,id);
                invertedIndexBST.add(w, id);
            }
        }
    }

    public LinkedList<String> createInvIndList (String d, int id){
        LinkedList<String> wordsInDoc = new LinkedList<String>();
        CreateIndAndInv(d,wordsInDoc,id);
        return wordsInDoc;
    }



   
    public static void main(String[] args) {
        SearchEngine s = new SearchEngine();
        s.LoadStop("stop.txt");
        s.LoadFile("dataset.csv");
        System.out.println("\nIndex:");
        s.index.displayDocs();
        System.out.println("\nInverted Index:");
        s.invertedIndex.displayInvertedIndex();
        InvertedIndexBST indexBST=new InvertedIndexBST();
        indexBST.add_from_inverted_list(s.invertedIndex);

        Ranking R = new Ranking(indexBST,s.index,"a");
        R.insertStoredList();
        R.displayAllDocWithScore();
        QueryProcessorBST qBST=new QueryProcessorBST(s.invertedIndexBST);
        LinkedList<Integer> resBST=qBST.OR("surprising OR football");
        QueryProcessor query = new QueryProcessor(s.invertedIndex);
        LinkedList<Integer> resAND = query.AND("championship AND squad");
        LinkedList<Integer> resOR = QueryProcessor.OR("market OR global");
        System.out.println("\nResults of query: championship AND squad");
        s.displayDocsFromIDs(resAND);
        System.out.println("\nResults of query: surprising OR football");
        s.displayDocsFromIDs(resBST);
        
        System.out.println("\n\nInverted Index by BST:");
        s.invertedIndexBST.display_invertedIndex();
    }
    
}
