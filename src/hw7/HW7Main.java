/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw7;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Noam
 */
public class HW7Main {

    /**
     * @param args the command line arguments
     */
    
    
   
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
     
         
        FileReader fr = new FileReader("novel.txt");
        BufferedReader myBr = new BufferedReader(fr);

        StringBuilder sb=new StringBuilder();  //In this case StringBuilder is more efficient bevause its a muttable classthat can be appended to. We prefer StringBuilder in cases when we have only a single thread accessing our object which is the case here.
        String k = "";
        
        
        while(!k.equals("null\n"))
        {          
            sb.append(k);
            k = myBr.readLine() + "\n";
        }

        String text = sb.toString();
         
        text = text.toLowerCase();
         
        text = text.replaceAll("\\s", " "); //delete all that is not between a-z or ' or single space or -
        text = text.replaceAll(" +", " ");
        text = text.replaceAll("[^a-z' -]", "");
        text = text.replaceAll("----", "");
        text = text.replaceAll("--", " "); //replace -- by a space
         
        
        String[] tokens = text.split(" "); // tokenization

        String[] stem_tokens = new String[tokens.length];//this array will contain only stems
        
        for(int j=0;j<tokens.length;j++) //filling up the array of stems
        {
            int init_length = tokens[j].length();
              
            char[]arr = tokens[j].toCharArray();

            Stemmer s = new Stemmer();

            s.add(arr,init_length);

            s.stem();
            
            stem_tokens[j]= s.toString();
            
        }
       
        HashMap stem_map = new HashMap();
        
        int max=0;
        
        String max_s = "";
        
        
        for(int g=0;g<stem_tokens.length;g++) //filling up the array of stems a HashMap is perfect because you can't have the same key twice and its very quick to find a particular element which is a method we use very regularly
        {
           if(null!=stem_map.putIfAbsent(stem_tokens[g], 1))
            {
                int val = (int)stem_map.get(stem_tokens[g]);
                
                val++;
                
                if(val>max)
                {
                    max =val;
                    max_s = stem_tokens[g];
                }
                if(val>1000)
                {
                    System.out.print("");
                }
                stem_map.put(stem_tokens[g], val);
                               
            }
             
                      
        }
        
        
        
        LinkedList <String>[] ordered_stems = new LinkedList[max+1];
        
//        for(int f=0;f<stem_map.size();f++)
//        {
//            ordered_stems[stem_map.get(stem_tokens[f])].add(stem_map);
//        }
        for (int v=0; v<max+1; v++)//initializing all the indexes of the array as LinkedLists of strings
        {
            ordered_stems[v]=new LinkedList<String>();
        }
             


        
       for (Object key : stem_map.keySet()) 
       {         
           //System.out.println("key: " + key + " value: " + stem_map.get(key));
           int ind = (int)stem_map.get(key);
                
           ordered_stems[ind].add((String) key);
       }
       
       
       

       LinkedList  ordered_list = new LinkedList();
       
       for(int t=max;t>0;t--)
       {     
           for(int u=0; u<ordered_stems[t].size();u++)
           {
               ordered_list.add(ordered_stems[t].get(u));
           }
           
       }
       
       
       System.out.println("The number of words in the text is: " + tokens.length);
       
       System.out.println("The number of unique words/stems in the text is: " + ordered_list.size());

       File myFile = new File("WordCount.txt"); 
       FileOutputStream fos = new FileOutputStream(myFile); 
       OutputStreamWriter osw = new OutputStreamWriter(fos); 
       
       
       for (int a =0 ; a<ordered_list.size();a++) 
       {   if(a<ordered_list.size()-1) 
            {
               osw.write(ordered_list.get(a) +": "+ stem_map.get(ordered_list.get(a))+"\n");
            }
          
       
           else
            {
               osw.write(ordered_list.get(a) +": "+ stem_map.get(ordered_list.get(a)));
            }
           
       }
       osw.close();
        
       
      HashMap myHMap = new HashMap();
      
      for (int a =0 ; a<ordered_list.size();a++) 
       {   
          //System.out.println(ordered_list.get(a));
          myHMap.put(ordered_list.get(a), stem_map.get(ordered_list.get(a)));
       }
       
      try {
       File myFile_2 = new File("WordCount.dat");
       FileOutputStream myFos = new FileOutputStream(myFile_2);
       BufferedOutputStream myBr_2 = new BufferedOutputStream(myFos); 
       ObjectOutputStream myOos = new ObjectOutputStream(myBr_2);

       myOos.writeObject(myHMap);
      
       myOos.close();
      }      
       catch(IOException ex) {
            ex.getMessage();
      }     
            

    }
}
