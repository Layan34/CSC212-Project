/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package searchengine;


/**
 *
 * @author Layan saad
 */
public class SearchEngine {

    public static void main(String[] args) {
        ReadDoc.LoadFile("dataset.csv");
        ReadDoc.LoadStop("stop.txt");
    }
    
}
