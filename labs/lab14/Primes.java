import java.io.*;
import java.util.*;

/**
 * Primes is a program that will compute prime numbers using the sieve of Erasthenes.
 * 
 * @author Charles Hoot
  * @version 4.0
 */

    
public class Primes
{

    public static void main(String args[])
    {
        ListWithIteratorInterface<Integer> candidates;
        ListWithIteratorInterface<Integer> composites;
        ListWithIteratorInterface<Integer> primes;
        Integer foundPrime;
        int max;
        
        System.out.println("Enter the maximum value to test for primality");
        max = getInt("   It should be an integer value greater than or equal to 2.");
        System.out.println("Constructing list of candidates up to " + max);
        
        candidates = new ArrayListWithIterator<Integer>();
        for(int i=2; i<=max; i++)
            candidates.add(new Integer(i));
        
        System.out.println("The candidates list is " + candidates);
        
        composites = new ArrayListWithIterator<Integer>();
        primes = new ArrayListWithIterator<Integer>();
        
        while ( candidates.getLength() > 0)
        {
            foundPrime = candidates.remove(1);
            System.out.println("Found the prime " + foundPrime);
            
            primes.add(foundPrime);
            
            getComposites(candidates, composites, foundPrime);
            
            System.out.println("The candidates list is ");
            // USE AN ITERATOR TO PRINT OUT THE CANDIDATES
            Iterator<Integer> it = candidates.getIterator();
            while(it.hasNext()){
                System.out.print(it.next()+" ");
            }
            System.out.println();
            
            System.out.println("The primes list is " );
            it = primes.getIterator();
            while(it.hasNext()){
                System.out.print(it.next()+" ");
            }
            System.out.println();

            // USE AN ITERATOR TO PRINT OUT THE PRIMES
            
            System.out.println("The composites list is ");
            it = composites.getIterator();
            while(it.hasNext()){
                System.out.print(it.next()+" ");
            }
            System.out.println();

            // USE AN ITERATOR TO PRINT OUT THE COMPOSITES
    
            System.out.println();           
        }
        
    }
    
    
    /**
     * getComposites - remove the composite values from candidates list and
     * put them in the composites list
     *
     * @param  candidates   a list holding the candidates values
     * @param  composites   a list holding the composite values
     * @param  prime   an Integer that is prime
     */
    public static void getComposites(ListWithIteratorInterface<Integer> candidates, 
                                ListWithIteratorInterface<Integer> composites, Integer prime)
    {
        
        // COMPLETE THIS METHOD
        Iterator<Integer> cand = candidates.getIterator();
        //Iterator<Integer> comp = composites.getIterator();


        while(cand.hasNext()){
            int x = cand.next();
            if(x%prime == 0){
                cand.remove();
                composites.add(x);
            }
        }
        
    }
    
    
    
    /**
     * Get an integer value
     *
     * @return     an integer 
     */
    private static int getInt(String rangePrompt)
    {
        Scanner input;
        int result = 10;        //default value is 10
        try
        {
            input = new Scanner(System.in);
            System.out.println(rangePrompt);
            result = input.nextInt();
            
        }
        catch(NumberFormatException e)
        {
            System.out.println("Could not convert input to an integer");
            System.out.println(e.getMessage());
            System.out.println("Will use 10 as the default value");
        }        
        catch(Exception e)
        {
            System.out.println("There was an error with System.in");
            System.out.println(e.getMessage());
            System.out.println("Will use 10 as the default value");
        }
        return result;
                                    
    }
    
}
