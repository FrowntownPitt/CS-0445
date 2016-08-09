 


/**
 * Indicate that the arguments for max were bad. It is unchecked.
 * 
 * @author Charles Hoot
 * @version 4.0
 */
public class BadArgumentsForMaxException extends RuntimeException
{
	public BadArgumentsForMaxException(String reason)
	{
		super(reason);
	}
}
