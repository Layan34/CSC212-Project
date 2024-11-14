package searchengine;
public class QueryProcessor {
    static InvertedIndex invertedInd;

    public QueryProcessor(InvertedIndex inv){
        invertedInd=inv;
    }

    public static boolean isInResult(LinkedList<Integer>result,Integer id){
        if(result.empty())
        return false;

        result.findFirst();

        while(!result.last()){
            if(result.retrieve().equals(id))
            return true;

            result.findNext();
        }
        if (result.retrieve().equals(id))
        return true;

        return false;
    }


    public static LinkedList <Integer> AND (String query) {
        LinkedList<Integer> pt1 =new LinkedList<Integer>();
        LinkedList<Integer> pt2 =new LinkedList<Integer>();
        String wordsBtw[]=query.split("AND"); //words that have "AND" between them
        if (wordsBtw.length==0)
        return pt1;

        boolean wordFound=invertedInd.isInInverted(wordsBtw[0].trim().toLowerCase());
        if (wordFound)
        pt1=invertedInd.invertedIndex.retrieve().documentIDs;

        for(int i=1; i<wordsBtw.length; i++){
            wordFound=invertedInd.isInInverted(wordsBtw[i].trim().toLowerCase());
            if (wordFound)
            pt2=invertedInd.invertedIndex.retrieve().documentIDs;

            pt1=AND(pt1,pt2);
        }

        return pt1;
    }

    public static LinkedList <Integer> AND (LinkedList<Integer>pt1,LinkedList<Integer>pt2){
        LinkedList<Integer> result = new LinkedList<Integer>();
        if(pt1.empty()||pt2.empty())
        return result;

        pt1.findFirst();
        
        while(true){
            boolean wordFound=isInResult(result,pt1.retrieve());
            if(!wordFound){
                pt2.findFirst();
                while(true){
                    if(pt2.retrieve().equals(pt1.retrieve())){
                        result.insert(pt1.retrieve());
                        break;
                    }
                    if(!pt2.last())
                    pt2.findNext();

                    else
                    break;

                }
            }

            if(!pt1.last())
            pt1.findNext();

            else
            break; 
        }
        return result;

    }

    public static LinkedList <Integer> OR  (String query){
        LinkedList<Integer> pt1 =new LinkedList<Integer>();
        LinkedList<Integer> pt2 =new LinkedList<Integer>();
        String wordsBtw[]=query.split("OR"); //words that have "OR" between them
        if (wordsBtw.length==0)
        return pt1;

        boolean wordFound=invertedInd.isInInverted(wordsBtw[0].trim().toLowerCase());
        if (wordFound)
        pt1=invertedInd.invertedIndex.retrieve().documentIDs;

        for(int i=1; i<wordsBtw.length; i++){
            wordFound=invertedInd.isInInverted(wordsBtw[i].trim().toLowerCase());
            if (wordFound)
            pt2=invertedInd.invertedIndex.retrieve().documentIDs;

            pt1=OR(pt1,pt2);
        }

        return pt1;
    }

    public static LinkedList <Integer> OR  (LinkedList<Integer>pt1,LinkedList<Integer>pt2){
        LinkedList<Integer> result = new LinkedList<Integer>();
        if(pt1.empty()  && pt2.empty())
        return result; 

        pt1.findFirst();
        while(!pt1.empty()){
            boolean wordFound=isInResult(result,pt1.retrieve());
            if(!wordFound)
            result.insert(pt1.retrieve());

            if(!pt1.last())
            pt1.findNext();
            else 
            break;
        }

        pt2.findFirst();

        while(!pt2.empty()){
            boolean wordFound=isInResult(result,pt2.retrieve());
            if(!wordFound)
            result.insert(pt2.retrieve());

            if(!pt2.last())
            pt2 .findNext();
            else 
            break;


        }

        return result;

        }
    }



