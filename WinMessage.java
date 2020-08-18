import javax.swing.JOptionPane;
/**
 * Displays a win message when the user wins
 * 
 * @author Rohan Rashingkar
 * @version 11/16
 */
public class WinMessage
{
    
    /**
     * Constructor for objects of class WinMessage
     */
    public WinMessage()
    {
    }

    /**
     * Creates a pop up message with speficied text
     * 
     * @param message the body of the message
     * @param title the message's title or header
     */
    public static void messageBox(String message, String title)

    {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
