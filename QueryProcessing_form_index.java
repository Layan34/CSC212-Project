/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csc212.project1;

import csc212.project1.Index;
import csc212.project1.LinkedList;


/**
 *
 * @author mmrrj
 */
public class QueryProcessing_form_index {
    static Index index1;
    public QueryProcessing_form_index(Index index1){
        this.index1=index1;
    }
    public static LinkedList<Integer>BooleanQuery(String Query){
        if(!Query.contains("AND")&&!Query.contains("OR"))
                return ANDQuery(Query);
        else if (Query.contains("AND")&&!Query.contains("OR"))
        return ANDQuery(Query);
        else if(!Query.contains("AND")&&Query.contains("OR"))
           return ORQuery(Query);
        else
            return MixedQuery(Query);
        
    }
    public static LinkedList<Integer> MixedQuery(String query) {
        LinkedList<Integer> q1 = new LinkedList<>();
        LinkedList<Integer> q2 = new LinkedList<>();

        if (query.length() == 0) 
            return q1;
        

        String ORs[] = query.split("OR"); // Split by "OR"
        q1 = ANDQuery(ORs[0]); // Process the first part

        for (int i = 1; i < ORs.length; i++) {
            q2 = ANDQuery(ORs[i]); // Process each "AND" group
            q1 = ORQuery(q1, q2); // Combine results
        }

        return q1;
    }
    public static LinkedList<Integer> ANDQuery(String query) {
        //System.out.println("Starting AND");
        LinkedList<Integer> pt1 = new LinkedList<>();
        LinkedList<Integer> pt2 = new LinkedList<>();
        String terms[] = query.split("AND"); // words separated by "AND"

        if (terms.length == 0) 
            return pt1;
        
        pt1=index1.get_all_documents_given_term(terms[0].trim().toLowerCase());
      
       
        for (int i = 1; i < terms.length; i++) {
            
           pt2=index1.get_all_documents_given_term(terms[0].trim().toLowerCase());
           pt1=ANDQuery(pt1,pt2);
           
            }
            return pt1;
      
    }
    public static LinkedList<Integer> ANDQuery(LinkedList<Integer> pt1, LinkedList<Integer> pt2) {
        LinkedList<Integer> result = new LinkedList<>();
        if ( pt1.empty() || pt2.empty()) {
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
     public static LinkedList<Integer> ORQuery(String query) {
        LinkedList<Integer> pt1 = new LinkedList<>();
        LinkedList<Integer> pt2 = new LinkedList<>();
        String terms[] = query.split("OR"); // words separated by "OR"

        if (terms.length == 0) {
            return pt1;
        }
        pt1=index1.get_all_documents_given_term(terms[0].trim().toLowerCase());
       

       for (int i = 1; i < terms.length; i++) {
            
           pt2=index1.get_all_documents_given_term(terms[0].trim().toLowerCase());
           pt1=ORQuery(pt1,pt2);
           
            }

        return pt1;
    }
 public static LinkedList<Integer> ORQuery(LinkedList<Integer> pt1, LinkedList<Integer> pt2) {
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
public static  LinkedList<Integer> notQuery(String query,Index ind1){
     LinkedList<Integer> pt1 = new LinkedList<>();
        LinkedList<Integer> pt2 = new LinkedList<>();
        if(query.length()==0)return pt1;
        if (!query.contains("NOT"))return pt1;
        String term=query.replaceFirst("NOT", "").trim().toLowerCase();
        pt1=index1.get_all_documents_given_term(term.trim().toLowerCase());
        if(ind1.documents.empty()) return pt1;
        ind1.documents.findFirst();
        while(!ind1.documents.last()){
            if(!pt1.exist(ind1.documents.retrieve().id))
                pt2.insert(ind1.documents.retrieve().id);
        ind1.documents.findNext();
}
   if(!pt1.exist(ind1.documents.retrieve().id))  
       pt2.insert(ind1.documents.retrieve().id);
   return pt2;
}
public static boolean isInResult(LinkedList<Integer> result, Integer id) {
        if ( result.empty()) {
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

}
