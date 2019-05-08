/**
 * Card that has a side  
 * @author Harsh Deep 
 * @version 10.31.18
 */
public class Card
{
    private int rank;
    private String suit;
    private boolean isFaceUp;
    /**
     * default constructor for a card
     * @param rank the integer that is the rank of the card
     * @param suit a string that is the suit
     */
    public Card(int rank, String suit)
    {
        isFaceUp = false;
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * returns the rank
     * @return integer that is the rank
     */
    public int getRank()
    {
        return rank;
    }

    /**
     * returns the suit
     * @return String that is the suit of the card
     */
    public String getSuit()
    {
        return suit;
    }

    /**
     * checks if a card is Red
     * @return true if red
     *          false if black
     */
    public boolean isRed()
    {
        return(suit.equals("d")||suit.equals("h"));
    }

    /**
     * checks if a card is face up
     * @return true if it is face up
     *         else false;
     * 
     */
    public boolean isFaceUp()
    {
        return isFaceUp;
    }

    /**
     * turns the card up
     * 
     */
    public void turnUp()
    {
        isFaceUp = true;
    }

    /**
     * turns the card down
     * 
     */
    public void turnDown()
    {
        isFaceUp = false;
    }
    /**
     * gets the File for the image of the Card
     * @return String a path to the file of the card
     * 
     */
    public String getFileName()
    {
        if(!isFaceUp)
        {
            return "cards\\backapcsds.gif";
        }
        String temp;
        String r = String.valueOf(rank);
        if(r.equals("1"))
            temp = "a";
        else if(r.equals("11"))
            temp = "j";
        else if(r.equals("12"))
            temp = "q";
        else if(r.equals("13"))
            temp = "k";
        else if(r.equals("10"))
            temp = "t";
        else
            temp = ""+rank;
        return "cards/"+temp+suit+".gif";

    }
    /**
     * gets a string representation of the obj
     * @return String that is the string representation of the obj
     * 
     */
    public String toString()
    {
        return rank+suit;

    }

}
