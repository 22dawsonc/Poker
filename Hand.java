public class Hand
{
    private Card[] cards = new Card[2];
     public Hand(Card a, Card b)
     {
         cards[0] = a;
         cards[1] = b;
     }
     public Card[] getHand()
     {
         return cards;
     }

}