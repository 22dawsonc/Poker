import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.lang.System;

/**
 * 
 * A display for the solitaire class and for playing solitaire
 * @authour Ms. Datar
 * @authour Harsh Deep
 * @authour Dawson Chen
 * @version 11.2.18
 * 
 */
public class Display extends JComponent implements MouseListener, KeyListener
{
    private static final int CARD_WIDTH = 73;
    private static final int CARD_HEIGHT = 97;
    private static final int SPACING = 5;  //distance between cards
    private static final int FACE_UP_OFFSET = 15;  //distance for cascading face-up cards
    private static final int FACE_DOWN_OFFSET = 5;  //distance for cascading face-down cards
    private int moves;
    private JFrame frame;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private long start;
    private Game game;
    public static void main(String[] args) 
    {

        Player p1 = new Player(100,"test1");
        Player p2 = new Player(100,"test2");
        Player[] players = {p1, p2};
        Game g = new Game(players);
        Display d = new Display(g);
        p1.setDisplay(d);
        p2.setDisplay(d);
        g.playHand();
    }

    /**
     * Constructor for the SolitaireDisplay
     * @param game An instance of a solitaire match
     * 
     */
    public Display(Game game)
    {
        Runnable runnable = new Runnable() {    
                public void run() {  
                    while (true) {      
                        repaint();
                        try {          
                            Thread.sleep(100);       
                        } catch (InterruptedException e) {      
                            e.printStackTrace();      
                        }       
                    }       
                }    
            };
        new Thread(runnable).start();
        start = System.nanoTime();
        this.game = game;

        frame = new JFrame("Poker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setUndecorated(false); 
        //this.setPreferredSize(new Dimension(CARD_WIDTH * 7 + SPACING * 8, CARD_HEIGHT * 2 + SPACING * 3 + FACE_DOWN_OFFSET * 7 + 13 * FACE_UP_OFFSET));
        this.setPreferredSize(new Dimension(400,500));
        frame.setSize(2000,2000);
        this.addMouseListener(this);
        frame.addKeyListener(this);

        //         JButton b = new JButton("Fold");
        //         b.addActionListener(new ActionListener(){  
        //                 public void actionPerformed(ActionEvent e){  
        //                     System.out.println("Folded");  
        //                 }  
        //             });
        //         b.setBounds(50,50,50,50);
        //         frame.add(b);  
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * Paints the field
     * 
     * @param g graphics to paint on
     * 
     */
    public void paintComponent(Graphics g)
    {
        //background
        //rgb(17, 175, 41)
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(0,0,0));
        g.fillOval(25, 25, (int)(frame.size().getWidth() - 50), (int)(frame.size().getHeight()-50));

        g.setColor(new Color(17, 175, 41));
        g.fillOval(100, 100, (int)(frame.size().getWidth() - 200), (int)(frame.size().getHeight()-200));
        Card c = new Card(10, "h");
        c.turnUp();
        Player[] a = game.getPlayers();

        for(int i = 0;i<a.length;i++)
        {
            Hand h = a[i].getHand();
            for(int j = 0;j<2;j++)
            {
                h.getPocket()[j].turnUp();
                this.drawCard(g, h.getPocket()[j], (int)(frame.size().getWidth()/2 - 10)+ (10*j), (int)(frame.size().getHeight() - 200) - 700*i);
            }
        }

    }

    /**
     * Draws Cards
     * @param g the graphics to draw on
     * @param card the card to be drawn
     * @param x the x-coords of the upper left corner
     * @param y the y-coords of the upper left corner
     * 
     */
    private void drawCard(Graphics g, Card card, int x, int y)
    {
        if (card == null)
        {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
        }
        else
        {
            String fileName = card.getFileName();
            if (!new File(fileName).exists())
                throw new IllegalArgumentException("bad file name:  " + fileName);
            Image image = new ImageIcon(fileName).getImage();
            g.drawImage(image, x, y, CARD_WIDTH, CARD_HEIGHT, null);
        }
    }

    public void keyTyped(KeyEvent e) {
        keyPressed(e);
    }

    /** Handle the key-pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_F) {
            System.out.println("fold");
        }
        if (key == KeyEvent.VK_C) {
            System.out.println("call");
        }
        if(key == KeyEvent.VK_R)
        {
            System.out.println("raise");
        }
    }

    /** Handle the key-released event from the text field. */
    public void keyReleased(KeyEvent e) {

    }

    /**
     * handles event
     * 
     * @param e a MouseEvent
     */
    public void mouseExited(MouseEvent e)
    {

    }

    /**
     * handles event
     * 
     * @param e a MouseEvent
     */
    public void mouseEntered(MouseEvent e)
    {

    }

    /**
     * handles event
     * 
     * @param e a MouseEvent
     */
    public void mouseReleased(MouseEvent e)
    {

    }

    /**
     * handles event
     * 
     * @param e a MouseEvent
     */
    public void mousePressed(MouseEvent e)
    {

    }

    /**
     * handles event
     * 
     * @param e a MouseEvent
     */

    public void mouseClicked(MouseEvent e)
    {
        repaint();
    }
}