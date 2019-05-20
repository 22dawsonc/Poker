import java.util.*;
public class Hand
{
    private Card[] pocket = new Card[2];

    private List<Card> cards = new ArrayList<Card>();
    
    private Game game;

     public Hand(Card a, Card b, Game g)
     {
         game = g;
         pocket[0] = a;
         pocket[1] = b;
         cards.add(pocket[0]);
         cards.add(pocket[1]);
     }

     public Card[] getPocket()
     {
         return pocket;
     }

     public Card[] flop()
     {
         Card[] flop = new Card[3];
         int rand = (int)(Math.random()*game.getDeck().size());
         flop[0] = game.getDeck().remove(rand);
         flop[1] = game.getDeck().remove(rand);
         flop[2] = game.getDeck().remove(rand);
         cards.add(flop[0]);
         cards.add(flop[1]);
         cards.add(flop[2]);
        return flop;
     }

     public Card turn()
     {
        int rand = (int)(Math.random()*game.getDeck().size());
        Card turn = game.getDeck().remove(rand);
        cards.add(turn);
        return turn;
     }

     public Card river()
     {
        int rand = (int)(Math.random()*game.getDeck().size());
        Card river = game.getDeck().remove(rand);
        cards.add(river);
        return river;
     }
     
     public double compareTo()
     {
         //the big boy method
         return 0.0;
     }
}