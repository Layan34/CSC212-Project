package searchengine;

import java.io.File;
import java.util.Scanner;

public class SearchEngine {

    LinkedList<String> stopwords;
    Index index;
    InvertedIndex invertedIndex;

    public SearchEngine () {
        stopwords=new LinkedList<>();
        index=new Index();
        invertedIndex=new InvertedIndex();
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
                index.addDoc(new Document(ID, wordsInDoc)); //linked list of docs (with data & IDs)
            }
        }
        catch(Exception em){
            System.out.println("File is ended.");
        }
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
        s.LoadFile("dataset.csv");
        s.LoadStop("stop.txt");
        s.index.displayDocs();
        System.out.println("\n\nInverted Index:");
        s.invertedIndex.displayInvertedIndex();
    }
    
}
