import java.io.*;
import java.util.*;

/**
 * CountingGame is a program that will simulate a children's counting game used to select
 * someone.
 * 
 * @author Charles Hoot
 * @version 4.0
 */
    
public class CountingGame
{

    public static void main(String args[])
    {
        ListInterface<Integer> players = new AList<Integer>();
        ListInterface<String> rhyme = null;
        //Alist<Integer> players = new AList<Integer>();
        
        int max;
        int position = 1;       // always start with the first player
        
        System.out.println("Please enter the number of players.");
        max = getInt("   It should be an integer value greater than or equal to 2.");
        System.out.println("Constructing list of players");
        
        // ADD CODE HERE TO CREATE THE LIST OF PLAYERS
        for(int i=0; i<max; i++){
            players.add(i+1);
        }
        
        System.out.println("The players list is " + players);
        
        rhyme = getRhyme();

        //System.out.println("Removing player "+doRhyme(players, rhyme, 0));

        int startAt = 1;
        while(players.getLength() > 1){
            startAt = doRhyme(players, rhyme, startAt-1);
            System.out.println("Removing player "+players.getEntry(startAt));
            players.remove(startAt);
        }
        // ADD CODE HERE TO PLAY THE GAME
        


        System.out.println("The winner is " + players.getEntry(1));
    }
    
    
    /**
     * Do the rhyme with the players in the list and remove the selected
     * player.
     *
     * @param  players   A list holding the players.
     * @param  rhyme   A list holding the words of the rhyme.
     * @param  startAt A position to start the rhyme at.
     * 
     * @return The position of the player eliminated.
     */
   public static int doRhyme(ListInterface<Integer> players, ListInterface<String> rhyme, int startAt)
    {
        // COMPLETE THIS METHOD
        
        if(players.getLength() <= 1)
            return -1;

        //int pos = (rhyme.getLength()-1+startAt)%players.getLength();
        //System.out.println("Pos: "+pos);
        int pos = startAt;
        int rhymePos = 0;

        while(rhymePos < rhyme.getLength()){
            if(pos>= players.getLength()){
                pos = 0;
            }
            System.out.println("Player "+players.getEntry(pos+1)+": "+rhyme.getEntry(rhymePos+1));
            pos++;
            rhymePos++;
        }

        return pos;
     }
    
    
    
    /**
     * Get an integer value.
     *
     * @return     An integer. 
     */
    private static int getInt(String rangePrompt)
    {
        Scanner input;
        int result = 10;        //Default value is 10
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
    
    /**
     * getRhyme - Get the rhyme.
     *
     * @return    A list of words that is the rhyme.
     */
    private static ListInterface<String> getRhyme()
    {
        Scanner input;
        String inString = "";
        ListInterface<String> rhyme = new AList<String>();
        
        try
        {
            input = new Scanner( System.in );
            
            System.out.println("Please enter a rhyme");
            inString = input.nextLine().trim();
            
            Scanner rhymeWords = new Scanner(inString);
            while(rhymeWords.hasNext())
            {
                rhyme.add(rhymeWords.next());
            }
            
        }
        catch(Exception e)
        {
            System.out.println("There was an error with System.in");
            System.out.println(e.getMessage());
            System.out.println("Will use a rhyme of size one");
        }

        // Make sure there is at least one word in the rhyme
        if(rhyme.getLength() < 1)
            rhyme.add("Default");
            
        return (ListInterface<String>)rhyme;
                                    
    }
    
}
