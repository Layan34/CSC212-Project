
package searchengine;

import java.io.File;
import java.util.Scanner;


public class ReadDoc {
    
    /*
    public static void LoadFile(String Name){
        String line;
        try{
            File f = new File(Name);
            Scanner read = new Scanner(f);
            read.nextLine(); //to skip first line
            while(read.hasNextLine()){
               line=read.nextLine();
               
               if(line.trim().length()<3){
                   break; //empty line 
               }
               System.out.println(line);
               String sID = line.substring(0, line.indexOf(',')).trim();
               int ID = Integer.parseInt(sID);
               String data = line.substring(line.indexOf(',')+1).trim();
            }

        }
        catch(Exception em){
          System.out.println("File is ended.");
        
        }
   }
    
    public static void LoadStop(String Name){
        String line = null;
        try{
            File f = new File(Name);
            Scanner read = new Scanner(f);
            read.nextLine(); //to skip first line
            while(read.hasNextLine()){
               line=read.nextLine();
               System.out.println(line);}

    
        }catch(Exception em){
          System.out.println("File is ended.");
        
        }
    
    } */
}