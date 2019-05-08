public class Game 
{
    private Card[] deck = new Card[52];
    public Game()
    {
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
        for(Card c: deck)
            System.out.println(c);
    }
    public static void main(String[] args){new Game();}

}