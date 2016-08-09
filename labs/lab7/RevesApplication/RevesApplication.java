import java.awt.*;
import javax.swing.*;

/**
 * This animated application shows a solution to Reve's problem
 *  
 * @author Charles Hoot 
 * @version 4.0
 */
    
public class RevesApplication 
{

    public static void main (String args[]) 
    {
        JPanel myPanel;
        Stepper myStepper;
        ActionThread myThread;
        Object dispatcher;
        
        myThread = new RevesActionThread();                 // Change this line for different
                                                                // applications
        myPanel =  myThread.getAnimationPanel();
 
        dispatcher = new Object();
        AnimatedApplicationFrame myFrame = 
            new AnimatedApplicationFrame(myThread.getApplicationTitle(),
                                        dispatcher, myPanel, 
                                        myThread);
        myStepper = myFrame.getStepper();
        
        // must set the stepper before we start the thread
        myThread.setStepper(myStepper);
        myThread.start();
    }
    
}
