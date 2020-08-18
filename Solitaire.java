import java.util.*;
/**
 * Controls the underlying features of the Solitare game
 * @author Rohan Rashingkar
 * @version 11/7
 */
public class Solitaire
{
    /**
     * Main tester method for Solitaire class
     * @param args the array of Strings
     */
    public static void main(String[] args)
    {
        new Solitaire();
    }

    private Stack<Card> stock;
    private Stack<Card> waste;
    private Stack<Card>[] foundations;
    private Stack<Card>[] piles;
    private SolitaireDisplay display;
    private int foundationSize;
    /**
     * Constructor for Objects of class Solitaire
     */
    public Solitaire()
    {

        stock = new Stack<Card>();
        createStock();
        waste = new Stack<Card>();
        foundations = new Stack[4];
        for (int i = 0; i < 4; i++)
        {
            foundations[i] = new Stack<Card>();
        }
        piles = new Stack[7];
        for (int i = 0; i < 7; i++)
        {

            piles[i] = new Stack<Card>();
        }
        deal();
        display = new SolitaireDisplay(this);
    }

    /**
     * Creates a random stock of cards
     */
    private void createStock()
    {

        ArrayList<Card> temp = new ArrayList<Card>();
        for (int i = 1; i < 14; i++)
        {
            temp.add(new Card(i, "h"));
            temp.add(new Card(i, "d"));
            temp.add(new Card(i, "c"));
            temp.add(new Card(i, "s"));
        }

        while (temp.size() > 0)
        {
            int removedIndex = (int)(temp.size() * Math.random());
            stock.push(temp.remove(removedIndex));
        }
    }

    /**
     * Checks if the game is won
     * @return true if the game is won else return false
     */
    private boolean win()
    {
        for (int i = 0; i < 4; i++)
        {
            if (foundations[i].isEmpty() || foundations[i].peek().getRank() != 13)
                return false;
        }
        return true;
    }

    /**
     * Deals out cards into the stock
     */
    private void deal()
    {
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < i+1; j++)
            {
                piles[i].push(stock.pop());
            }
            piles[i].peek().turnUp();
        }
    }

    /**
     * Puts three cards from the stock into the waste
     */
    private void dealThreeCards()
    {
        int i = 0;
        while (!stock.isEmpty() && i < 3)
        {
            waste.push(stock.pop());
            waste.peek().turnUp();
            i++;
        }
    }

    /**
     * Resets the stock's cards
     */
    private void resetStock()
    {
        while (!waste.isEmpty())
        {
            waste.peek().turnDown();
            stock.push(waste.pop());
        }
    }

    /**
     * Checks if the card can be added to the pile
     * @param index the index of the pile, 0 <= index < 7
     * @param card the card to be checked
     * @return true if the given card can be
     * legally moved to the top of the given pile
     */
    private boolean canAddToPile(Card card, int index)
    {
        if (piles[index].isEmpty())
            return card.getRank() == 13;

        else if (piles[index].peek().isFaceUp())
        {
            if (piles[index].peek().getRank()-card.getRank() == 1)
            {
                if (piles[index].peek().isRed() == !card.isRed())
                    return true;
            }
        }
        return false;
    }

    /**
     * Removes all of the face up cards in the given pile
     * @param index the index of the pile 0 <= index < 7
     * @return a stack of all the removed cards
     */
    private Stack<Card> removeFaceUpCards(int index)
    {
        Stack<Card> temp = new Stack<Card>();
        while (!piles[index].isEmpty() && piles[index].peek().isFaceUp())
        {
            temp.push(piles[index].pop());
        }
        return temp;
    }

    /**
     * Removes elements from cards and adds them to the given pile.
     * @param cards the stack of cards to be added to the pile
     * @param index the index of the pile, 0 <= index < 7
     */
    private void addToPile(Stack<Card> cards, int index)
    {
        while (!cards.isEmpty())
        {
            piles[index].push(cards.pop());
        }
    }
    
    /** 
     * Retrieves the top card on the stock
     * @return the card on top of the stock,
     * or null if the stock is empty
     **/
    public Card getStockCard()
    {
        if (stock.isEmpty())
            return null;
        return stock.peek();
    }

    /**
     * Retrieves the card on the top of the waste
     * @return the card on top of the waste,
     * or null if the waste is empty
     */
    public Card getWasteCard()
    {
        if (waste.isEmpty())
            return null;
        return waste.peek();
    }

    /**
     * Retrieves the top card of the foundation
     * @param index the foundation's index, 0 <= index < 4
     * @return the card on top of the given
     * foundation, or null if the foundation is empty
     */
    public Card getFoundationCard(int index)
    {
        if (foundations[index].isEmpty())
            return null;
        return foundations[index].peek();
    }

    /**
     * Retrieves a pile at a specified index
     * @param index the index of the pile, 0 <= index < 7
     * @return a reference to the given pile
     */
    public Stack<Card> getPile(int index)
    {
        return piles[index];
    }

    /**
     * Retrieves a foundation at a specified index
     * @param index the index of foundation, 0 <= index < 4
     * @return the foundation at index 
     */
    public Stack<Card> getFoundation(int index)
    {
        return foundations[index];
    }

    /**
     * Checks if a card can be added to a foundation
     * @param card the card to be checked
     * @param index the index of the foundation, 0 <= index < 4
     * @return true if the given card can be
     * legally moved to the top of the given foundation otherwise false
     */
    private boolean canAddToFoundation(Card card, int index)
    {
        foundationSize++;
        if (foundations[index].isEmpty()) 
        {
            if (card.getRank() == 1)
                return true;
        }
        else if (card.getRank() - foundations[index].peek().getRank() == 1
            && card.getSuit().equals(foundations[index].peek().getSuit()))
            return true;
        foundationSize--;
        return false;
    }

    /**
     * Called when the stock is clicked
     */
    public void stockClicked()
    {
        if (display.isWasteSelected() || display.isPileSelected() || display.isFoundationSelected())
        {
            display.unselect();
        }
        else if (!stock.isEmpty())
            dealThreeCards();
        else
            resetStock();
    }

    /**
     * Attempts to add a stack of cards to all foundations
     * @param selectedStack the stack of cards to check
     */
    public void tryAndAddToFoundation(Stack<Card> selectedStack)
    {
        for (int i = 0; i < 4; i++)
        {
            if (canAddToFoundation(selectedStack.peek(), i))
            {
                foundations[i].push(selectedStack.pop());
                i = 4; //mimics a break
            }
        }
        if (win())
        {
            WinMessage.messageBox("Congratulations! You Win!", "A Message For the User");
        }
    }

    /**
     * Called when the waste is clicked
     */
    public void wasteClicked()
    {
        if (!waste.isEmpty() && (!display.isWasteSelected() && !display.isPileSelected() 
            && !display.isFoundationSelected()))
            display.selectWaste();
        else if (display.isWasteSelected())
        {
            tryAndAddToFoundation(waste);
            display.unselect();
        }
        else
            display.unselect();
    }

    /**
     * Called when a foundation is clicked
     * @param index of the foundations index, 0 <= index < 7
     */
    public void foundationClicked(int index)
    {
        if (display.isWasteSelected())
        {
            if (canAddToFoundation(waste.peek(),index))
            {
                foundations[index].push(waste.pop());
                if (!waste.isEmpty())
                    waste.peek().turnUp();  
            }
            if (win())
                WinMessage.messageBox("Congratulations! You Win!", "A Message to the User");
            display.unselect();
        }
        else if (display.isPileSelected())
        {
            if (canAddToFoundation(piles[display.selectedPile()].peek(),index))
            {
                foundations[index].push(piles[display.selectedPile()].pop());
            }
            if (win())
                WinMessage.messageBox("Congratulations! You Win!", "A Message For the User");
            display.unselect();
        }   
        else if (display.isFoundationSelected())
            display.unselect();
        else if (!foundations[index].isEmpty())
        {
            display.selectFoundation(index);
        }
        else
            display.unselect();
    }

    /**
     * Called when a pile is clicked
     * @param index of the piles index, 0 <= index < 7
     */

    public void pileClicked(int index)
    {
        if (piles[index].isEmpty())
        {
            if (display.isWasteSelected())
            {
                if (canAddToPile(waste.peek(),index))
                    piles[index].push(waste.pop());
            }
            else if (display.isPileSelected())
            {
                Stack<Card> s = removeFaceUpCards(display.selectedPile());
                Card c = s.peek();
                if (!s.isEmpty() && canAddToPile(c,index))
                {
                    addToPile(s,index);
                }
                else
                {
                    while (!s.isEmpty())
                    {
                        piles[display.selectedPile()].push(s.pop());
                    }
                }
            }
            display.unselect();
        }
        else if (display.isFoundationSelected())
        {
            if(canAddToPile(foundations[display.selectedFoundation()].peek(),index))
            {
                piles[index].push(foundations[display.selectedFoundation()].pop());
            }
            display.unselect();
        }
        else if (display.selectedPile() == index)
        {
            tryAndAddToFoundation(piles[index]);
            display.unselect();
        }
        else if (display.isPileSelected())
        {
            Stack<Card> s = removeFaceUpCards(display.selectedPile());
            Card c = s.peek();
            if (!s.isEmpty() && canAddToPile(c,index))
            {
                addToPile(s,index);
            }
            else
            {
                while (!s.isEmpty())
                {
                    piles[display.selectedPile()].push(s.pop());
                }
            }
            display.unselect();
        }
        else if (!display.isWasteSelected() && !piles[index].isEmpty()
            && piles[index].peek().isFaceUp())
        {
            display.selectPile(index);
        }
        else if (display.isWasteSelected() && canAddToPile(waste.peek(),index))
        {
            piles[index].push(waste.pop());
            if (!waste.isEmpty())
                waste.peek().turnUp();
            display.unselect();
        }
        else if (!display.isWasteSelected() && !display.isPileSelected())
        {
            piles[index].peek().turnUp();
        }
        else
            display.unselect();
    }
}
