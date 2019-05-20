import java.util.Scanner;

public class Player
{
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    private Card a;
    private Card b;
=======
    private Hand h;
>>>>>>> parent of f76f607... Major Design Changes, added hand evaluation and ranking.
=======
    private Hand h;
>>>>>>> parent of f76f607... Major Design Changes, added hand evaluation and ranking.
=======
    private Hand h;
>>>>>>> parent of f76f607... Major Design Changes, added hand evaluation and ranking.
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
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    public void deal(Card a, Card b)
    {
        this.a = a;
        this.b = b;
=======
    public void deal(Hand h)
    {
        this.h = h;
>>>>>>> parent of f76f607... Major Design Changes, added hand evaluation and ranking.
=======
    public void deal(Hand h)
    {
        this.h = h;
>>>>>>> parent of f76f607... Major Design Changes, added hand evaluation and ranking.
=======
    public void deal(Hand h)
    {
        this.h = h;
>>>>>>> parent of f76f607... Major Design Changes, added hand evaluation and ranking.
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
<<<<<<< HEAD
        
=======
        Scanner sc = new Scanner(System.in);
        System.out.println(name+"'s move'");
        return sc.next();
>>>>>>> parent of f76f607... Major Design Changes, added hand evaluation and ranking.
    }
    public void setCash(int cash)
    {
        this.cash = cash;
    }

}