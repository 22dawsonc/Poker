import java.net.*; 
import java.io.*; 
import java.util.*; 
/**
 * Creates a player that can use a different computer
 * 
 * @author Harsh Deep
 * @author Dawson Chen
 * @version 5.24.19
 */
public class RemotePlayer extends Player
{
	public String winner;
	public boolean isTurn;
    private List<Card> communityCards;
    private MulticastSocket socket; 
    private InetAddress group; 
    private int port; 
    private Display d;
    private int pot;
    public int amtToCall;
    private static final int MAX_LEN = 1000;
    
    public static void main(String[] args)
    {
        RemotePlayer r = new RemotePlayer(990, "a");

    }
    public void reset() 
    {
    	winner = "";
    	isTurn = false;
        communityCards = new ArrayList<Card>();
        //sendString("rtg");
    }
    public RemotePlayer(int cash, String n)
    {
        super(cash, n);
        winner = "";
        communityCards = new ArrayList<Card>();
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
        sendString("rtg");
    }
    public int getPot()
    {
    	return pot;
    }

    public void sendString(String s)
    {
        byte[] buffer = s.getBytes(); 
        try
        {
            DatagramPacket datagram = new
                DatagramPacket(buffer,buffer.length,group,port); 
            socket.send(datagram);
            System.out.println("Sent: "+datagram);
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void getInput()
    {
        System.out.println(getName()+"'s move'"+getCards());
        String s = d.getInput();
        while(s.equals("")) 
        {
        	try {Thread.sleep(100);}
        	catch(InterruptedException ie) {ie.printStackTrace();}
        	s = d.getInput();
        }
        sendString(getName()+":"+s);
    }

    public void parseInput(String m)
    {
        if(m.contains("ServerTo"+getName()+":")) // either the initial 2 card deal or a request to input
        {
            if(m.contains("["))
            {
                //it is the deal
                String s = m.substring(m.indexOf(":")+2);
                List<String> a = parseList(s);
                List<Card> cards = new ArrayList<Card>();
                for(String l: a)
                    cards.add(Card.reconstructCard(l));
                deal(cards.get(0), cards.get(1));
                System.out.println(this.getCards());
                if(d==null)
                	d = new Display(this);
                else 
                	d.restart();
                
            }else if(m.contains("?"))// ?<pot><amt to call> is the keyword that will be used to indicate and request to input
            {
            	
                String s = m.substring(m.indexOf("?")+1);
                int pot = Integer.valueOf(s.substring(1, s.indexOf(">")));
                s = s.substring(s.indexOf(">")+1);
                int amt = Integer.valueOf(s.substring(1, s.indexOf(">")));
                if(amt>=0) {
                amtToCall = amt;
                this.pot = pot;
                System.out.println("Your turn! Pot "+pot+" amount to call "+amt);
                isTurn = true;
                getInput();
                isTurn = false;
                }
                else 
                {
                	System.err.println(amt);
                	sendString(getName()+":c");
                }
            }
            else
            {
                System.err.println("BAD SERVERSIDE REQUEST");
            }
        }
        else if(m.contains("Server:"))//addressing all players on multicast(ie the flop, turn and river cards)
        {
            String s = m.substring(m.indexOf(":")+2);
            if(s.contains("]")) 
            {
            List<String> a = parseList(s);
            List<Card> cards = new ArrayList<Card>();
            for(String l: a)
                cards.add(Card.reconstructCard(l));
            communityCards.addAll(cards);
            for(int i = communityCards.size()-1;i>=0;i--) 
            	if(communityCards.get(i)==null)
            		communityCards.remove(i);  
            }
            else 
            {
            	winner = s;
            	try {Thread.sleep(10000);}
            	catch(InterruptedException ie) 
            	{
            		ie.printStackTrace();
            	}
            	reset();
            }
        }	
        else if(m.contains("ServerTo")&&m.contains("?")) 
        {
        	String s = m.substring(m.indexOf("?")+1);
            int pot = Integer.valueOf(s.substring(1, s.indexOf(">")));
            s = s.substring(s.indexOf(">")+1);
            this.pot = pot;
        }
        	
        	
        /*
         * does nothing if it is not addressed
         */

    }

    public List<Card> getCommunityCards()
    {
        return communityCards;
    }

    /*
     * [element1, element2, element3]
     */
    public List<String> parseList(String s)
    {
        s.substring(0, s.length()-1);
        List<String> parsed = new ArrayList<String>();
        while(s.indexOf(",")!=-1)
        {
            parsed.add(s.substring(1, s.indexOf(",")));
            s = s.substring(s.indexOf(",")+1);
        }
        parsed.add(s.substring(1, s.length()-1));
        return parsed;
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
                    /*
                     * Process message here
                     */
                    System.err.println(message);
                    parseInput(message);
                } 
                catch(IOException e) 
                { 
                    System.out.println("Socket closed!"); 
                } 
            } 
        } 
    }
}
