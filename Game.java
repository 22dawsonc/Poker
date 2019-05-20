import java.util.*;

public class Game 
{
<<<<<<< HEAD
    private int pot;
    private List<Card> deck;
    Player[] players;
=======
    private List<Card> deck;

    Player[] players;

>>>>>>> parent of f76f607... Major Design Changes, added hand evaluation and ranking.
    public Game(Player[] players)
    {
        deck = new ArrayList<Card>();
        this.players = players;
        String[] suits = {"d", "c", "s", "h"};
<<<<<<< HEAD
=======
        int ct = 0;
>>>>>>> parent of f76f607... Major Design Changes, added hand evaluation and ranking.
        for(int i = 1;i<=13;i++)
        {
            for(int j = 0;j<4;j++)
            {
                Card c = new Card(i, suits[j]);
                deck.add(c);
            }
        }
<<<<<<< HEAD
=======
        //shuffles into random deck
>>>>>>> parent of f76f607... Major Design Changes, added hand evaluation and ranking.
        for(int i = 51;i>0;i--)
        {
            int num = (int)(Math.random()*deck.size());
            Card temp = deck.get(i);
            deck.set(i,deck.get(num));
            deck.set(num, temp);
        }
<<<<<<< HEAD

=======
        Hand h = new Hand(deck.remove(0), deck.remove(0), this);
        Hand a = new Hand(deck.remove(0), deck.remove(0), this);
        players[0].deal(h);
        players[1].deal(a);
>>>>>>> parent of f76f607... Major Design Changes, added hand evaluation and ranking.
    }
    public List<Card> getDeck()
    {
        return deck;
    }
    public void playHand()
    {
<<<<<<< HEAD

=======
>>>>>>> parent of f76f607... Major Design Changes, added hand evaluation and ranking.
        int i = 0;
        while(true)
        {
            String s = players[i%players.length].getMove();
            if(s.contains("r"))
            {
                int amt = Integer.valueOf(s.substring(s.indexOf("r")+1));
                System.out.println(amt);
            }
            else if(s.equals("f"))
            {
                System.out.println("fold");
            }
            else
            {
                System.out.println(s);
            }
        }

    }
    public Player[] getPlayers(){return players;}

}