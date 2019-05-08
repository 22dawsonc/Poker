public class Game 
{
    private Card[] deck = new Card[52];
    Player[] players;
    public Game(Player[] players)
    {
        this.players = players;
        String[] suits = {"d", "c", "s", "h"};
        int ct = 0;
        for(int i = 1;i<=13;i++)
        {
            for(int j = 0;j<4;j++)
            {
                Card c = new Card(i, suits[j]);
                deck[ct++] = c;
            }
        }
        for(int i = 51;i>0;i--)
        {
            int num = (int)(Math.random()*deck.length);
            Card temp = deck[i];
            deck[i] = deck[num];
            deck[num] = temp;
        }
        Hand h = new Hand(deck[0], deck[1]);
        Hand a = new Hand(deck[2], deck[3]);
        players[0].deal(h);
        players[1].deal(a);
    }
    public void playHand()
    {
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