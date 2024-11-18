package searchengine;
public class QueryProcessor {
    static InvertedIndex invertedInd;

    public QueryProcessor(InvertedIndex inv) {
        invertedInd = inv;
    }

    public static boolean isInResult(LinkedList<Integer> result, Integer id) {
        if (result == null || result.empty()) {
            return false;
        }

        result.findFirst();

        while (!result.last()) {
            if (result.retrieve().equals(id)) {
                return true;
            }
            result.findNext();
        }
        if (result.retrieve().equals(id)) {
            return true;
        }
        return false;
    }

    public static LinkedList<Integer> AND(String query) {
        //System.out.println("Starting AND");
        LinkedList<Integer> pt1 = new LinkedList<>();
        LinkedList<Integer> pt2 = new LinkedList<>();
        String wordsBtw[] = query.split("AND"); // words separated by "AND"

        if (wordsBtw.length == 0) {
            return pt1;
        }

        boolean wordFound = invertedInd.isInInverted(wordsBtw[0].trim().toLowerCase());
        if (wordFound) {
            pt1 = invertedInd.invertedIndex.retrieve().documentIDs;
        }

        for (int i = 1; i < wordsBtw.length; i++) {
            wordFound = invertedInd.isInInverted(wordsBtw[i].trim().toLowerCase());
            if (wordFound) {
                pt2 = invertedInd.invertedIndex.retrieve().documentIDs;
                pt1 = AND(pt1, pt2);
            }

        }
        //System.out.println("End AND");

        return pt1;
    }

    public static LinkedList<Integer> AND(LinkedList<Integer> pt1, LinkedList<Integer> pt2) {
        LinkedList<Integer> result = new LinkedList<>();
        if (pt1 == null || pt2 == null || pt1.empty() || pt2.empty()) {
            return result;
        }

        pt1.findFirst();

        while (true) {
            if (!isInResult(result, pt1.retrieve())) {
                pt2.findFirst();
                while (true) {
                    if (pt2.retrieve().equals(pt1.retrieve())) {
                        result.insert(pt1.retrieve());
                        break;
                    }
                    if(!pt2.last() )
                        pt2.findNext() ;
                    else
                        break;
                }

            }//end if not found
            if(!pt1.last())
                pt1.findNext() ;
            else
                break;
        }
        return result;
    }

    public static LinkedList<Integer> OR(String query) {
        LinkedList<Integer> pt1 = new LinkedList<>();
        LinkedList<Integer> pt2 = new LinkedList<>();
        String wordsBtw[] = query.split("OR"); // words separated by "OR"

        if (wordsBtw.length == 0) {
            return pt1;
        }

        boolean wordFound = invertedInd.isInInverted(wordsBtw[0].trim().toLowerCase());
        if (wordFound) {
            pt1 = invertedInd.invertedIndex.retrieve().documentIDs;
        }

        for (int i = 1; i < wordsBtw.length; i++) {
            wordFound = invertedInd.isInInverted(wordsBtw[i].trim().toLowerCase());
            if (wordFound)
                pt2 = invertedInd.invertedIndex.retrieve().documentIDs;

            pt1 = OR(pt1, pt2);
        }

        return pt1;
    }

    public static LinkedList<Integer> OR(LinkedList<Integer> pt1, LinkedList<Integer> pt2) {
        LinkedList<Integer> result = new LinkedList<>();
        if (pt1.empty () && pt2.empty ())
            return result;
        pt1.findFirst () ;
        while (!pt1.empty ()) {
            boolean found=isInResult(result,pt1.retrieve ()) ;
            if (!found) { // not found in result
                result.insert (pt1.retrieve ()) ;
            }//end if
            if (!pt1.last ())
                pt1.findNext () ;
            else
                break;
        }

        pt2.findFirst () ;
        while (!pt2.empty() ) {
            boolean found= isInResult(result, pt2.retrieve ()) ;
            if(!found){ // not found in result
                result.insert(pt2.retrieve ()) ;
            } //end if
            if(!pt2.last ())
                pt2.findNext () ;
            else
                break;

        }//end inner while for B
        return result;
    }

    public static LinkedList<Integer> MixedQuery(String query) {
        LinkedList<Integer> q1 = new LinkedList<>();
        LinkedList<Integer> q2 = new LinkedList<>();

        if (query == null || query.length() == 0) {
            return q1;
        }

        String ORs[] = query.split("OR"); // Split by "OR"
        q1 = AND(ORs[0]); // Process the first part

        for (int i = 1; i < ORs.length; i++) {
            q2 = AND(ORs[i]); // Process each "AND" group
            q1 = OR(q1, q2); // Combine results
        }

        return q1;
    }
}



