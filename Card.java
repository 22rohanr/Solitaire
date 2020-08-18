
/**
 * Mimics the behavior of a playing card
 * 
 * @author Rohan Rashingkar 
 * @version 11/7
 */
public class Card
{
    private int rank;
    private String suit;
    private boolean isFaceUp;

    /**
     * Constructor for objects of class Card
     * @param r the number rank of the card
     * @param s the suit of the card
     */
    public Card(int r, String s)
    {
        rank = r;
        suit = s;
        isFaceUp = false;
    }

    /**
     * Gives the number rank (from 1-13) of the card
     * @return the card's rank
     */
    public int getRank()
    {
        return rank;
    }

    /**
     * Gives the card's suit as a one character String
     * @return the card's suit
     */
    public String getSuit()
    {
        return suit;
    }

    /**
     * Tells if a card's suit is red (diamond or hearts)
     * @return true if the card is red otherwise return false
     */
    public boolean isRed()
    {
        return suit.equals("d") || suit.equals("h");
    }

    /**
     * Tells if a card is face up with its number and suit showing
     * @return true if the card is face up otherwise return false
     */
    public boolean isFaceUp()
    {
        return isFaceUp;
    }

    /**
     * Turns a card up with its number and suit showing
     */
    public void turnUp()
    {
        isFaceUp = true;
    }

    /**
     * Turns a card down with its back design showing
     */
    public void turnDown()
    {
        isFaceUp = false;
    }

    /**
     * Gives the file name of the card's front side
     * @return the file name
     */
    public String getFileName()
    {
        if (isFaceUp)
        {
            if (rank == 1)
                return "C:\\Users\\scare\\OneDrive\\Desktop\\Solitaire (2)\\cards\\a" 
                    + suit + ".gif";
            if (rank == 10)
                return "C:\\Users\\scare\\OneDrive\\Desktop\\Solitaire (2)\\cards\\t" 
                    + suit + ".gif";
            if (rank == 11)
                return "C:\\Users\\scare\\OneDrive\\Desktop\\Solitaire (2)\\cards\\j" 
                    + suit + ".gif";
            if (rank == 12)
                return "C:\\Users\\scare\\OneDrive\\Desktop\\Solitaire (2)\\cards\\q" 
                    + suit + ".gif";
            if (rank == 13)
                return "C:\\Users\\scare\\OneDrive\\Desktop\\Solitaire (2)\\cards\\k" 
                    + suit + ".gif"; 
            return "C:\\Users\\scare\\OneDrive\\Desktop\\Solitaire (2)\\cards\\" + rank 
                + suit + ".gif"; 
        }
        else
            return "C:\\Users\\scare\\OneDrive\\Pictures\\arnjcslab3.jpg"; 
    }
}
