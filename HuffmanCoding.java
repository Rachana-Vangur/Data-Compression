import java.io.*;
import java.util.*;

class TreeNode{
   int freq;
   char ch;
   TreeNode left;
   TreeNode right;

   TreeNode(char ch, int freq){
        this.freq = freq;
        this.ch = ch;
        left = null;
        right = null;
   }

   TreeNode(TreeNode left, TreeNode right){
        this.left = left;
        this.right = right;
        freq = left.freq + right.freq;
        ch = '\0';
   }
   
}


class Encoder{
    Map<Character, Integer> map = new HashMap<>();

    void freqMapping(String inputStr){
        for(int i = 0; i < inputStr.length(); i++){
            char ch = inputStr.charAt(i);
            if(!map.containsKey(ch)){
                map.put(ch, 1);
            }
            else{
                map.put(ch, (map.get(ch))+ 1);
            }
        }
    }




   void priorityqueue(){
        Queue<Character> queue = new PriorityQueue<>();
        for(char c: map.keySet()){
            queue.offer(c);
        }
    }

   for(int i = 0; i < inputStr.length(); i++){
        
   }
}

class Decoder{

}

class HuffmanCoding{
    public static void main(String[] args){
        System.out.println("Enter the message: ");
        String inputString = "";
        try{
            BufferedReader fr = new BufferedReader(new FileReader("InputMessage.txt"));
            String word;
            while((word = fr.readLine()) != null){
                inputString += word;
            }
            //call freqmapping

            fr.close();
        }
        catch (IOException e){
            System.out.println("Exception occured!");
        }
    }
}