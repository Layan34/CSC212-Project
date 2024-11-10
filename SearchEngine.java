
package searchengine;

public class SearchEngine {

    public static void main(String[] args) {
        ReadDoc.LoadFile("dataset.csv");
        ReadDoc.LoadStop("stop.txt");
    }
    
}
