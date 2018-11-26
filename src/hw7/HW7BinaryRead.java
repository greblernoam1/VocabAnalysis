/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw7;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

public class HW7BinaryRead {

public static void main (String[] args) throws Exception { 
  
        File myFile = new File("WordCount.dat");
        
        FileInputStream myFis = new FileInputStream(myFile); 
        BufferedInputStream myBis = new BufferedInputStream(myFis); 
        ObjectInputStream myOis = new ObjectInputStream(myBis);
        
        
        HashMap myHMap;
        myHMap = (HashMap) myOis.readObject(); 
        
        int max =0;
        for (Object key : myHMap.keySet()) 
        {         
           
          if ((int)myHMap.get(key)>max)
          {
              max = (int)myHMap.get(key);
          }
        }
       
        LinkedList <String>[] ordered_stems = new LinkedList[max+1];
        
        for (int v=0; v<max+1; v++)//initializing all the indexes of the array as LinkedLists of strings
        {
            ordered_stems[v]=new LinkedList<String>();
        }
              
       for (Object key : myHMap.keySet()) 
       {         
           //System.out.println("key: " + key + " value: " + stem_map.get(key));
           int ind = (int)myHMap.get(key);
                
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
       
       for (int a =0 ; a<ordered_list.size();a++) 
       {   
           
               System.out.print(ordered_list.get(a) +": "+ myHMap.get(ordered_list.get(a))+"\n");
           
       }
        myOis.close();
}

    
}