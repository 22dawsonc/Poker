import java.awt.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.lang.System;

/**
 * 
 * A display for the solitaire class and for playing solitaire
<<<<<<< HEAD
 * @authour Ms. Datar
 * @authour Harsh Deep
 * @authour Dawson Chen
 * @version 11.2.18
 * 
 */
public class Display extends JComponent implements MouseListener, KeyListener
=======
 * @author Harsh Deep
 * @author Dawson Chen
 * @version 11.2.18
 * 
 */
public class Display extends JFrame implements MouseListener
>>>>>>> Merger
{
    private JFrame frame;
<<<<<<< HEAD
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

=======
    private RemotePlayer player;
    private JPanel bar;
    private JPanel p;
    private JButton c;
    private JButton f;
    private JButton r;
    private JLabel pot;
    private String input;
    
    public static void main(String[] args) {new Display(null);}
>>>>>>> Merger
    /**
     * Constructor for the Display
     * @param game An instance of a solitaire match
     * 
     */
    public Display(RemotePlayer plr)
    {
    	input = "";
    	player = plr;
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

        frame = new JFrame("Poker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
<<<<<<< HEAD
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
=======
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(screenSize);
        frame.setSize(screenSize);
        c = new JButton("Check/Call");
        c.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				input = "c";
			}
        });
        f = new JButton("Fold");
        f.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				input = "f";
			}
        });
        r = new JButton("Raise $10");
        r.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				input = "r10";
			}
        });
        pot = new JLabel("Pot: $10");
        bar = new JPanel();
        bar.setBackground(new Color(0,100,0));
        bar.add(c);
        bar.add(f);
        bar.add(r);
        bar.add(pot); 
        p = new JPanel(new GridBagLayout());
        p.setBackground(new Color(0,80,0));
        BufferedImage myPicture = null;
        List<Card> a = player.getCards();
		try {
			myPicture = ImageIO.read(new File(a.get(0).getFileName()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        
        BufferedImage myPicture1 = null;
		try {
			myPicture1 = ImageIO.read(new File(a.get(1).getFileName()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        JLabel picLabel1 = new JLabel(new ImageIcon(myPicture1));
        p.setLayout(null);
        int w = (int)(picLabel1.getPreferredSize().getWidth());
        int h = (int)(picLabel1.getPreferredSize().getHeight());
        int w1 =(int)(frame.getBounds().getWidth()/2) ;
        int h1 = (int)(frame.getBounds().getHeight()*0.69);
        System.out.println("" + w1 + " " + h1 + " " + (w1+w) + " " + (h1+h));
        picLabel1.setBounds(w1,h1,w1+w ,h1+h );
        //picLabel.setBounds((int)(frame.getPreferredSize().getWidth()/2),(int)(frame.getPreferredSize().getHeight()*.75), (int)(picLabel.getPreferredSize().getWidth()), (int)(picLabel.getPreferredSize().getHeight()));
        p.add(picLabel);
        p.add(picLabel1);
        
      
        //stuff in the big Panel
        frame.add(p);
        frame.add(bar, BorderLayout.SOUTH);
>>>>>>> Merger
        frame.setVisible(true);

    }
<<<<<<< HEAD
<<<<<<< HEAD
    public static BufferedImage scaleImage(BufferedImage image, int imageType,
        int newWidth, int newHeight) {
        // Make sure the aspect ratio is maintained, so the image is not distorted
        double thumbRatio = (double) newWidth / (double) newHeight;
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double aspectRatio = (double) imageWidth / (double) imageHeight;

<<<<<<< HEAD
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
=======
        if (thumbRatio < aspectRatio) {
            newHeight = (int) (newWidth / aspectRatio);
        } else {
            newWidth = (int) (newHeight * aspectRatio);
>>>>>>> Merger
        }

        // Draw the scaled image
        BufferedImage newImage = new BufferedImage(newWidth, newHeight,
                imageType);
        Graphics2D graphics2D = newImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image, 0, 0, newWidth, newHeight, null);

        return newImage;
  	}
=======
>>>>>>> parent of 1442e43... Progress in front-end and pocket display.
=======
>>>>>>> parent of 1442e43... Progress in front-end and pocket display.
    public String getInput()
    {
    	String temp = input;
    	input = "";
    	return temp;
    }
    public void paintComponent(Graphics g) {
    	
    }
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

<<<<<<< HEAD
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
=======
>>>>>>> Merger
}