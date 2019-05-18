import java.util.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
public class Game 
{
    private static final int HAND_RANK_SIZE = 32487834;
    private List<Card> communityCards;
    private int pot;
    private int call;
    private int[] evalTable;
    private static final int BIG_BLIND = 50;
    private static final int SMALL_BLIND = 30;
    public static void main(String[] args)
    {

        Game g = new Game();
        Player p1 = new Player(1000, "hdeep03");
        Player p2 = new Player(3000, "a");
        g.addPlayer(p1);
        g.addPlayer(p2);
        g.play();
    }
    private List<Card> deck;
    private List<Player> p;
    private String[] winTypes;
    private int bBlind;
    private int sBlind;
    public Game()
    {
        String[] a = {
                "BAD",
                "HIGH_CARD",
                "PAIR", 
                "TWO_PAIR",
                "THREE_OF_A_KIND",
                "STRAIGHT",
                "FLUSH",
                "FULL_HOUSE",
                "FOUR_OF_A_KIND",
                "STRAIGHT_FLUSH"
            };
        call = 0;
        bBlind = 0;
        sBlind = 1;
        winTypes = a;
        evalTable = loadHandRankResource("HandRanks.dat");
        communityCards = new ArrayList<Card>();
        p = new ArrayList<Player>();
        deck = new ArrayList<Card>();
        String[] suits = {"d", "c", "s", "h"};
        int ct = 0;
        for(int i = 1;i<=13;i++)
        {
            for(int j = 0;j<4;j++)
            {
                Card c = new Card(i, suits[j]);
                deck.add(c);
            }
        }
        //shuffles into random deck
        for(int i = 51;i>0;i--)
        {
            int num = (int)(Math.random()*(i+1));
            Card temp = deck.get(i);
            deck.set(i,deck.get(num));
            deck.set(num, temp);
        }
        for(int i = 0;i<5;i++)
            communityCards.add(deck.remove(0));
    }
    /**
     * Plays 1 game of Texas hold'em poker
     */
    public void play()
    {
        dealToPlayers();
        if(p.size()<2)
            return;
        p.get(bBlind).widthdraw(BIG_BLIND);
        p.get(sBlind).widthdraw(SMALL_BLIND);
        bBlind++;
        sBlind++;
        bBlind%=p.size();
        sBlind%=p.size();
        pot+=BIG_BLIND+SMALL_BLIND;
        getMoves();
        System.out.print("FLOP: ");
        for(int i = 0;i<3;i++)
            System.out.print(communityCards.get(i)+" ");
        System.out.println();
        getMoves();
        System.out.println("TURN: "+communityCards.get(3));
        getMoves();
        System.out.println("RIVER: "+communityCards.get(4));
        getMoves();
        getWinner();
        for(Player player: p)
            System.out.println(player.getName()+" "+player.getCash());
        
    }

    public List<Player> getPlayers()
    {
        return p;
    }
    public void getMoves()
    {
        for(int i = 0;i<p.size();i++)
        {
            String s = p.get(i).getMove();
            if(s.equals("f"))
            {
                p.remove(i);
                i--;
            }
            else if(s.equals("c"))
            {
                p.get(i).widthdraw(call);
                pot+=call;
            }
            else if(s.indexOf("r")!=-1)
            {
                int amt = Integer.valueOf(s.substring(1)).intValue();
                p.get(i).widthdraw(amt);
                call = amt;
                pot+=amt;
            }
        }
    }

    public void addPlayer(Player p)
    {
        this.p.add(p);
    }

    public void dealToPlayers()
    {
        for(Player player: p)
        {
            player.deal(deck.remove(0), deck.remove(0)); 
        }
    }

    public List<Card> getCommunityCards()
    {
        return communityCards;
    }

    public Player getWinner()
    {   
        int[] vals = new int[p.size()];
        for(int i = 0;i<vals.length;i++)
        {
            List<Card> b = new ArrayList<Card>(communityCards);
            b.addAll(p.get(i).getCards());
            System.out.println(b);
            int[] a = new int[7];
            for(int j = 0;j<a.length;j++)
                a[j] = b.get(j).getEvaluation();
            vals[i] = eval(a);
        }
        int maxInd = 0;
        for(int i = 1;i<vals.length;i++)
            if(vals[i]>vals[maxInd])
                maxInd = i;
        System.out.println(maxInd);
        int i = vals[maxInd]>>12;
        System.out.println(p.get(maxInd).getName()+" wins with a "+winTypes[i]);
        p.get(maxInd).widthdraw(-pot);
        pot = 0;
        return p.get(maxInd);
    }

    public int eval(int[] a) 
	{
		int b = 53;
		for(int i:a) 
		{
			b = evalTable[b+i];
		}
		return b;
	}

    private static int[] loadHandRankResource(String name)
    throws RuntimeException {
        int handRankArray[] = new int[HAND_RANK_SIZE];
        try {
            int tableSize = HAND_RANK_SIZE * 4;
            byte[] b = new byte[tableSize];
            InputStream br = null;
            try {
                br = new BufferedInputStream(new FileInputStream(name));
                int bytesRead = br.read(b, 0, tableSize);
                if (bytesRead != tableSize) {
                    System.err.print("Read " + bytesRead + " bytes out of " + tableSize);
                }
               // System.err.print("Read " + bytesRead + " bytes out of " + tableSize);
            } finally {
                br.close();
            }
            for (int i = 0; i < HAND_RANK_SIZE; i++) {
                handRankArray[i] = littleEndianByteArrayToInt(b, i * 4);
            }
            return handRankArray;
        } catch (IOException e) {
            throw new RuntimeException("cannot read resource " + name, e);
        }
    }

    private static final int littleEndianByteArrayToInt(byte[] b, int offset) {
        return (b[offset + 3] << 24) + ((b[offset + 2] & 0xFF) << 16)
        + ((b[offset + 1] & 0xFF) << 8) + (b[offset] & 0xFF);
    }
}