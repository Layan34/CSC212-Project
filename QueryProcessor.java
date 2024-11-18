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

        while (true) {
            if (result.retrieve().equals(id)) {
                return true;
            }
            if (result.last()) {
                break;
            }
            result.findNext();
        }
        return false;
    }

    public static LinkedList<Integer> AND(String query) {
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
            }
            pt1 = AND(pt1, pt2);
        }

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
                    if (pt2.last()) {
                        break;
                    }
                    pt2.findNext();
                }
            }
            if (pt1.last()) {
                break;
            }
            pt1.findNext();
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
            if (wordFound) {
                pt2 = invertedInd.invertedIndex.retrieve().documentIDs;
            }
            pt1 = OR(pt1, pt2);
        }

        return pt1;
    }

    public static LinkedList<Integer> OR(LinkedList<Integer> pt1, LinkedList<Integer> pt2) {
        LinkedList<Integer> result = new LinkedList<>();
        if ((pt1 == null || pt1.empty()) && (pt2 == null || pt2.empty())) {
            return result;
        }

        if (pt1 != null) {
            pt1.findFirst();
            while (true) {
                if (!isInResult(result, pt1.retrieve())) {
                    result.insert(pt1.retrieve());
                }
                if (pt1.last()) {
                    break;
                }
                pt1.findNext();
            }
        }

        if (pt2 != null) {
            pt2.findFirst();
            while (true) {
                if (!isInResult(result, pt2.retrieve())) {
                    result.insert(pt2.retrieve());
                }
                if (pt2.last()) {
                    break;
                }
                pt2.findNext();
            }
        }

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
