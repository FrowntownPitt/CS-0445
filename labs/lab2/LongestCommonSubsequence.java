import java.io.*;
import java.util.*;

/**
 * LongestCommonSubsequence is a program that will determine the longest string that is 
 * a subsequence of two input strings. This program applies a brute force solution technique.
 * 
 * @author Charles Hoot
 * @version 4.0
 */
public class LongestCommonSubsequence {

    public static void main(String args[]) {
        BagInterface<String> toCheckContainer = new ArrayBag();

        Scanner input;
        input = new Scanner(System.in);

        System.out.println("This program determines the longest string that is a subsequence of two input string.");
        System.out.println("Please enter the first string:");
        String first = input.next();

        System.out.println("Please enter the second string:");
        String second = input.next();

        toCheckContainer.add(first);

        // ADD CODE HERE TO CREATE THE BAG WITH THE INITIAL STRING

        System.out.println("The strings to check are: " + toCheckContainer);
        String bestSubsequence = new String("");
        
        // ADD CODE HERE TO CHECK THE STRINGS IN THE BAG
        
        while(!toCheckContainer.isEmpty()){
        		String item = toCheckContainer.remove();
        		if(item.length() > bestSubsequence.length()){
        			if(isSubsequence(item,second)){
        				bestSubsequence = item;
        			} else if(item.length() > bestSubsequence.length()-2) {
        				for(int i=0; i<item.length(); i++){
        					String x = item.substring(0,i)+item.substring(i+1,item.length());
        					toCheckContainer.add(x);
        				}
        			}
        		}
        		//System.out.println(toCheckContainer);
        }
        System.out.println("Found \"" + bestSubsequence + "\" for the longest common subsequence");

    }

    /**
     * Determine if one string is a subsequence of the other.
     *
     * @param check See if this is a subsequence of the other argument.
     * @param other The string to check against. 
     * @return     A boolean if check is a subsequence of other. 
     */
    public static boolean isSubsequence(String check, String against) {
        boolean result = true;
        
        if(check.equals("")){
        	return true;
        }
        if(check.length() >= against.length() && !check.equals(against)){
        	return false;
        }
        
        int last = 0;
        
        String sub = against;

        // ADD CODE HERE TO CHECK IF WE HAVE A SUBSEQUENCE
        for(int i=1; i<check.length()+1; i++){
        	//c[i] = 
        	//if(check.substring(i)){}
        	//System.out.println("Last: "+last+".... Char: "+check.substring(i-1,i)+".... Substr: "+
        	//					against.substring(last,against.length()));
        	if(against.substring(last,against.length()).contains(check.substring(i-1,i))){
        		last += against.substring(last,against.length()).indexOf(check.substring(i-1,i))+1;
        	}
        	else{
        		result = false;
        		break;
        	}
        	
        }

        return result;
    }
}
