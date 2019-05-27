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
    public static Card reconstructCard(String s)
    {
        String suit = s.substring(s.indexOf("of ")+3);
        String tmp = s.substring(0, s.indexOf(" "));
        int rank = -1;
        if(tmp.equals("ace"))
            rank = 1;
        else if(tmp.equals("king"))
            rank = 13;
        else if(tmp.equals("queen"))
            rank = 12;
        else if(tmp.equals("jack"))
            rank = 11;
        else if(tmp.equals("ten"))
            rank = 10;
        else
            rank = Integer.valueOf(tmp);
        return new Card(rank, suit);
    }
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
     * Gets the ranking value of this card;
     *  the ranking Value is between 1 and 52 
     *  inclusive and values the 2 of clubs the lowest and the ace of spades the highest
     */
    public int getEvaluation()
    {
        int i = 0;
        String s = getSuit();
        if(s.equals("c"))
            i = 1;
        else if(s.equals("d"))
            i = 2;
        else if(s.equals("h"))
            i = 3;
        else if(s.equals("s"))
            i = 4;
        if(rank == 1)
            return i + 48;
        else 
            return i + (rank-2)*4; 
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
        String temp;
        String r = String.valueOf(rank);
        if(r.equals("1"))
            temp = "ace";
        else if(r.equals("11"))
            temp = "jack";
        else if(r.equals("12"))
            temp = "queen";
        else if(r.equals("13"))
            temp = "king";
        else if(r.equals("10"))
            temp = "ten";
        else
            temp = ""+rank;
        return temp+" of "+suit;
        

    }

}
