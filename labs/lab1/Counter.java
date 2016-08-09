
/**
 * The counter class implements a counter that will roll over to the initial
 * value when it hits the maximum value.
 * 
 * @author Charles Hoot 
 * @version 4.0
 */
public class Counter
{
    // PUT PRIVATE DATA FIELDS HERE
    private int min;
    private int max;
    private int current;
    private boolean rolled = false;


    /**
     * The default constructor for objects of class Counter.  Minimum is 0 and the maximum
     * is the largest possible integer.
     */
    public Counter()
    {
        min = 0;
        max = Integer.MAX_VALUE;
        current = min;
    }
    
    
    /**
     * The alternate constructor for objects of class Counter.  The minimum and maximum values are given as parameters.
     * The counter starts at the minimum value.
     * @param min The minimum value that the counter can have
     * @param max The maximum value that the counter can have
     * */
    public Counter(int min, int max)
    {
        if(max<=min){
            throw new CounterInitializationException("Wrong. Now do it, again");
        }
        this.min = min;
        this.max = max;
        current = min;
    }
    
    /**
     * Determine if two counters are in the same state
     *
     * @param  otherObject   the object to test against for equality
     * @return     true if the objects are in the same state
     */
    public boolean equals(Object otherObject)
    {
        boolean result = true;
        if (otherObject instanceof Counter) {
            if(min == ((Counter) otherObject).getMin() && max == ((Counter) otherObject).getMax() &&
                    current == ((Counter) otherObject).value() && rolled == ((Counter) otherObject).rolledOver()){
                result = true;
            }
            else{
                result = false;
            }
        }
        return result;
    }

    public int getMin(){
        return min;
    }

    public int getMax(){
        return max;
    }
    
    

    /**
     * Increases the counter by one
     */
    public void increase()
    {
        current++;
        rolled = false;
        if(current > max){
            rolled = true;
            current = min;
        }

    }
 
 
     /**
     * Decreases the counter by one
     */
    public void decrease()
    {
        current --;
        rolled = false;
        if(current < min){
            rolled = true;
            current = max;
        }
    }
    
    /**
     * Get the value of the counter
     *
     * @return     the current value of the counter
     */
    public int value()
    {
        return current;
    }
    
    
    /**
     * Accessor that allows the client to determine if the counter
     *             rolled over on the last count
     *
     * @return     true if the counter rolled over
     */
    public boolean rolledOver()
    {
        return rolled;
    }
    
    /**
     * Override the toString method to provide a more informative
     * description of the counter
     *
     * @return     a descriptive string about the object
     */
    public String toString()
    {
        // CHANGE THE RETURN TO A DESCRIPTION OF THE COUNTER
        return String.format("Current position: %d/%d",current,max);
    }
 
}
