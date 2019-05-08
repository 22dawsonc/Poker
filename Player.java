public class Player
{
    private Hand h;
    private int cash;
    public Player(int cash)
    {
        this.cash = cash;
    }
    public void deal(Hand h)
    {
        this.h = h;
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
        return "";
    }
    public void setCash(int cash)
    {
        this.cash = cash;
    }

}