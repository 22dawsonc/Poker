import java.net.*;
import java.util.*;
import java.io.*;

/**
 * Write a description of class Server here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Server
{
    // instance variables - replace the example below with your own
	public String in;
    private Game g;
    private int ct;
    private MulticastSocket socket; 
    private InetAddress group; 
    private int port; 
    private static final int HAND_RANK_SIZE = 32487834;
    private List<Card> communityCards;
    private int pot;
    private int call;
    private int[] evalTable;
    private static final int BIG_BLIND = 10;
    private List<Card> deck;
    private List<Player> p;
    private String[] winTypes;
    private String p1;
    private String p2;
    private int blind;
    private ArrayList<Card> c1;
    private ArrayList<Card> c2;
    private int typeWin;
    public static void main(String[] args)
    {
        Server server = new Server();
        while(!server.ready())   
        {
            try{Thread.sleep(100);}
            catch(InterruptedException ie)
            {ie.printStackTrace();}
        }
        server.playRound();
    
    }
    /**
     * Constructor for objects of class Server
     */
    public Server()
    {
    	typeWin = 0;
    	blind = 1;
    	c1 = new ArrayList<Card>();
    	c2 = new ArrayList<Card>();
        g = new Game();
        p1 = "a";
        p2 = "b";
        String[] args = new String[2];
        args[0] = "239.0.0.0";
        args[1] = "8080";
        try
        {
            group = InetAddress.getByName(args[0]); 
            port = Integer.parseInt(args[1]); 
            socket = new MulticastSocket(port);
            socket.joinGroup(group);
            Runnable r = new ReadThread(socket, group, port);
            Thread t = new Thread(r);
            t.start();
        }catch(SocketException se)
        {
            se.printStackTrace();
        }catch(IOException ie)
        {
            ie.printStackTrace();
        }
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
        winTypes = a;
        evalTable = loadHandRankResource("HandRanks.dat");
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
    public int preflop() 
    {
    	int cont1 = 0;
    	int cont2 = 0;
    	pot+=BIG_BLIND;
    	int player = 1;
    	if(blind==1) 
    	{
    		cont1 +=BIG_BLIND;
    		player = 2;
    	}
    	else 
    	{
    		cont2+=BIG_BLIND;
    		player = 1;
    	}
    	//----------------------------- 
    	if(player == 1) 
		{
			sendString("ServerTo"+p1+": ?<"+pot+"><"+(cont2-cont1)+">");
			String s = awaitResponse();
			s = s.substring(s.indexOf(":")+1);
			if(s.equals("f"))
    		{
    			return 2;
    		}
			else if(s.equals("c")) 
    		{
    			int amt = cont2-cont1;
    			cont1 += amt;
    			pot +=amt;
    		}
			else if(s.contains("r")) 
    		{
    			int amt = Integer.valueOf(s.substring(1));
    			amt+=cont2-cont1;
    			cont1 += amt;
    			pot +=amt;
    		}
			player = 2;
		}
		else 
		{
			sendString("ServerTo"+p2+": ?<"+pot+"><"+(cont1-cont2)+">");
			String s = awaitResponse();
			s = s.substring(s.indexOf(":")+1);
			if(s.equals("f"))
    		{
    			return 1;
    		}
			else if(s.equals("c")) 
    		{
    			int amt = cont1-cont2;
    			cont2 += amt;
    			pot +=amt;
    		}
			else if(s.contains("r")) 
    		{
    			int amt = Integer.valueOf(s.substring(1));
    			amt+=cont1-cont2;
    			cont2 += amt;
    			pot +=amt;
    		}
			player = 1;
		}	
    	
    	do 
    	{
    		if(player == 1) 
    		{
    			sendString("ServerTo"+p1+": ?<"+pot+"><"+(cont2-cont1)+">");
    			String s = awaitResponse();
    			s = s.substring(s.indexOf(":")+1);
    			if(s.equals("f"))
        		{
        			return 2;
        		}
    			else if(s.equals("c")) 
        		{
        			int amt = cont2-cont1;
        			cont1 += amt;
        			pot +=amt;
        		}
    			else if(s.contains("r")) 
        		{
        			int amt = Integer.valueOf(s.substring(1));
        			amt+=cont2-cont1;
        			cont1 += amt;
        			pot +=amt;
        		}
    			player = 2;
    		}
    		else 
    		{
    			sendString("ServerTo"+p2+": ?<"+pot+"><"+(cont1-cont2)+">");
    			String s = awaitResponse();
    			s = s.substring(s.indexOf(":")+1);
    			if(s.equals("f"))
        		{
        			return 1;
        		}
    			else if(s.equals("c")) 
        		{
        			int amt = cont1-cont2;
        			cont2 += amt;
        			pot +=amt;
        		}
    			else if(s.contains("r")) 
        		{
        			int amt = Integer.valueOf(s.substring(1));
        			amt+=cont1-cont2;
        			cont2 += amt;
        			pot +=amt;
        		}
    			player = 1;
    		}
    		
    	}while(cont1!=cont2);
    	return 0;
    	
    }

    public void playRound()
    {
    	pot = 0;
        dealToPlayers();
        preflop();
        List<Card> c = new ArrayList<Card>();
        for(int i = 0;i<3;i++)
        	c.add(communityCards.get(i));
        sendString("Server: "+c);
        //The above code is for the flop
        int result = bettingRound();
        if(result == 1) 
        {
        	System.out.println("1 wins. POT: "+pot);
        	return;
        }
        else if(result == 2) 
        {
        	System.out.println("2 wins. POT: "+pot);
        	return;
        }
        c = new ArrayList<Card>();
        for(int i = 3;i<4;i++)
        	c.add(communityCards.get(i));
        sendString("Server: "+c);
        //above is for turn
        result = bettingRound();
        if(result == 1) 
        {
        	System.out.println("1 wins. POT: "+pot);
        	return;
        }
        else if(result == 2) 
        {
        	System.out.println("2 wins. POT: "+pot);
        	return;
        }
        
        c = new ArrayList<Card>();
        for(int i = 4;i<5;i++)
        	c.add(communityCards.get(i));
        sendString("Server: "+c);
        
        //above is the river
        result = bettingRound();
        if(result == 1) 
        {
        	System.out.println("1 wins. POT: "+pot);
        	return;
        }
        else if(result == 2) 
        {
        	System.out.println("2 wins. POT: "+pot);
        	return;
        }
        String win = getWinner();
        sendString("Server: "+win+"|"+winTypes[typeWin]);
    }
    /**
     * 0 means no one folded
     * 1 means p1 wins
     * 2 means p2 wins
     * @return
     */
    public int bettingRound() 
    {
    	int cont1 = 0;
    	int cont2 = 0;
    	int player = 0;
    	if(blind==1) 
    	{
    		sendString("ServerTo"+p1+": ?<"+pot+"><"+0+">");
    		player = 2;
    		String s = awaitResponse();
    		s = s.substring(s.indexOf(":")+1);
    		if(s.equals("f"))
    		{
    			return 2;
    		}
    		else if(s.contains("r")) 
    		{
    			int amt = Integer.valueOf(s.substring(1));
    			cont1 += amt;
    			pot+=amt;
    		}
    	}
    	else
    	{
    		sendString("ServerTo"+p2+": ?<"+pot+"><"+0+">");
    		player = 1;
    		String s = awaitResponse();
    		s = s.substring(s.indexOf(":")+1);
    		if(s.equals("f"))
    		{
    			return 1;
    		}
    		else if(s.contains("r")) 
    		{
    			int amt = Integer.valueOf(s.substring(1));
    			cont2 += amt;
    			pot +=amt;
    		}
    	}
    	do 
    	{
    		if(player == 1) 
    		{
    			sendString("ServerTo"+p1+": ?<"+pot+"><"+(cont2-cont1)+">");
    			String s = awaitResponse();
    			s = s.substring(s.indexOf(":")+1);
    			if(s.equals("f"))
        		{
        			return 2;
        		}
    			else if(s.equals("c")) 
        		{
        			int amt = cont2-cont1;
        			cont1 += amt;
        			pot +=amt;
        		}
    			else if(s.contains("r")) 
        		{
        			int amt = Integer.valueOf(s.substring(1));
        			amt+=cont2-cont1;
        			cont1 += amt;
        			pot +=amt;
        		}
    			player = 2;
    		}
    		else 
    		{
    			sendString("ServerTo"+p2+": ?<"+pot+"><"+(cont1-cont2)+">");
    			String s = awaitResponse();
    			s = s.substring(s.indexOf(":")+1);
    			if(s.equals("f"))
        		{
        			return 1;
        		}
    			else if(s.equals("c")) 
        		{
        			int amt = cont1-cont2;
        			cont2 += amt;
        			pot +=amt;
        		}
    			else if(s.contains("r")) 
        		{
        			int amt = Integer.valueOf(s.substring(1));
        			amt+=cont1-cont2;
        			cont2 += amt;
        			pot +=amt;
        		}
    			player = 1;
    		}
    		
    	}while(cont1!=cont2);
    	return 0;
    	
    	
    }
    public String awaitResponse() 
    {
    	in = "";
    	while(in.equals("")) 
    	{
    		try {Thread.sleep(100);}
    		catch(InterruptedException e) 
    		{
    			e.printStackTrace();
    		}
    	}
    	return in;
    }

    public void dealToPlayers()
    {
        String s1 = "ServerTo"+p1+": ";
        String s2 = "ServerTo"+p2+": ";
        List<String> car = new ArrayList<String>();
        
        c1.add(deck.remove(0));
        c1.add(deck.remove(0));
        car.add(c1.get(0).toString());
        car.add(c1.get(1).toString());
        s1+= car.toString();
        car.remove(0);
        car.remove(0);
        c2.add(deck.remove(0));
        c2.add(deck.remove(0));
        car.add(c2.get(0).toString());
        car.add(c2.get(1).toString());
        s2+= car.toString();
        sendString(s1);
        try {Thread.sleep(300);}
        catch(Exception e)
        {e.printStackTrace();}
        sendString(s2);
        
    }

    public void sendString(String s)
    {
        byte[] buffer = s.getBytes(); 
        try
        {
        	System.out.println("Sent: "+s);
            DatagramPacket datagram = new
                DatagramPacket(buffer,buffer.length,group,port); 
            socket.send(datagram);
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public boolean ready()
    {
        if(ct>=2)
            return true;
        return false;   
    }
    public void ct()
    {
        ct++;
    }

    public List<Card> getCommunityCards()
    {
        return communityCards;
    }

    public String getWinner()
    {   
	
            List<Card> b = new ArrayList<Card>(communityCards);
            b.addAll(c1);
            int[] a = new int[7];
            for(int j = 0;j<a.length;j++)
                a[j] = b.get(j).getEvaluation();
            int score1 = eval(a);
            b = new ArrayList<Card>(communityCards);
            b.addAll(c2);
            a = new int[7];
            for(int j = 0;j<a.length;j++)
                a[j] = b.get(j).getEvaluation();
            int score2 = eval(a);
       if(score1>score2) 
       {
    	   typeWin = score1>>12;
    	   return p1;
       }
       typeWin = score2 >> 12;
       
        return p2;
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
    public class ReadThread implements Runnable 
    { 
        private MulticastSocket socket; 
        private InetAddress group; 
        private int port; 
        private static final int MAX_LEN = 1000; 
        ReadThread(MulticastSocket socket,InetAddress group,int port) 
        { 
            this.socket = socket; 
            this.group = group; 
            this.port = port; 
            System.out.println("read thread created");
        } 

        @Override
        public void run() 
        { 
            while(true) 
            { 
                byte[] buffer = new byte[ReadThread.MAX_LEN]; 
                DatagramPacket datagram = new
                    DatagramPacket(buffer,buffer.length,group,port); 
                String message; 
                try
                { 
                    socket.receive(datagram); 
                    message = new
                    String(buffer,0,datagram.getLength(),"UTF-8"); 
                    if(message.contains("rtg"))
                        ct();
                    in = message;
                } 
                catch(IOException e) 
                { 
                    System.out.println("Socket closed!"); 
                } 
            } 
        } 
    }
}
