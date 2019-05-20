import java.util.Scanner;

public class Player
{
    private Card a;
    private Card b;
    private int cash;
    private Display d;
    private String name;
    public Player(int cash, String n)
    {
        this.name = n;
        this.cash = cash;
    }
    public Hand getHand()
    {
        return h;
    }
    public void setDisplay(Display d)
    {
        this.d= d;
    }
    public void deal(Card a, Card b)
    {
        this.a = a;
        this.b = b;
    }
    public int getCash()
    {   
        return cash;
    }
    /**
     * The String codes for the command that the Player wants to do
     *   "c" - means call
     *   "r"+ number - the number is how much this player wishes to raise by.
     *   "f" - folds for this player
     */
    public String getMove()
    {
        //implement later
        
    }
    public void setCash(int cash)
    {
        this.cash = cash;
    }

}