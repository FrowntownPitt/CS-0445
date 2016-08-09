
/**
 * Use a double recursion to compute the maximum of an array of values.
 * 
 * @author Charles Hoot
 * @version 4.0
 */
public class RecursiveMaxOfArray
{

    
    /**
     * Compute the maximum of a range of values in an array recursively.
     *
     * @param data   An array of integers.
     * @param from  The low index for searching for the maximum.
     * @param to    The high index for searching for the maximum.
     * 
     * preconditions: from <= to, from >=0, to<length, length>0
     *                
     * @return     The maximum value in the array.
     */
    
    public  int max(int data[], int from, int to)
    {
        int result = 0;

        if(data == null || data.length == 0 || from < 0 || to >= data.length || to < from){
            throw new BadArgumentsForMaxException("Fail");
        }
        
        // ADD YOUR CODE HERE

        if(to-from == 0){
            result = data[from];
        } else {
            //System.out.println("from: " + from);
            //System.out.println("to/2: " + to);
            int result1 = 0;
            int result2 = 0;
            //if(!(to/2 < from))
                result1 = max(data, from, (to+from)/2);
            //System.out.println("result1: " + result1);
            result2 = max(data, (to+from)/2+1, to);
            //System.out.println("result2: " + result2);
            result = (result1 > result2) ? result1:result2;
            //System.out.println("result: " + result + "\n#############");
        }

        return result;
    }
    
    
}
