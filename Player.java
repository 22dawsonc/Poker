import java.util.Scanner;
import java.util.*;
public class Player
{
    private Card o;
    private Card t;
    private int cash;
    private Display d;
    private String name;
    public Player(int cash, String n)
    {
        this.name = n;
        this.cash = cash;
    }
    public String getName()
    {
        return name;
    }
    public void setDisplay(Display d)
    {
        this.d= d;
    }
    public void deal(Card o, Card t)
    {
        this.o = o;
        this.t = t;
    }
    public List<Card> getCards()
    {
        List<Card> arr = new ArrayList<Card>();
        arr.add(o);
        arr.add(t);
        return arr;
    }
    public int getCash()
    {   
        return cash;
    }
    public void widthdraw(int amt)
    {
        cash-=amt;
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
        Scanner sc = new Scanner(System.in);
        System.out.println(name+"'s move'"+getCards());
        return sc.next();
    }
    public void setCash(int cash)
    {
        this.cash = cash;
    }

}