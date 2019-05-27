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
 * @author Harsh Deep
 * @author Dawson Chen
 * @version 11.2.18
 * 
 */
public class Display extends JComponent
{
    private static final int CARD_WIDTH = 73*2;
	private static final int CARD_HEIGHT = 97*2;
	private JFrame frame;
    private RemotePlayer player;
    private JPanel bar;
    private JPanel p;
    private JButton c;
    private JButton f;
    private JButton r;
    private JTextField t;
    private JLabel pot;
    private String input;
    
    public static void main(String[] args) {new Display(null);}
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

        frame = new JFrame(player.getName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = new Dimension(1700, 1000);
        frame.setPreferredSize(screenSize);
        frame.setSize(screenSize);
        frame.setResizable(false);
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
        r = new JButton("Raise: ");
        t = new JTextField(5);
        
        r.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				input = "r"+t.getText();
			}
        });
        frame.getContentPane().add(this);
        pot = new JLabel("Pot: ");
        bar = new JPanel();
        bar.setBackground(new Color(0,100,0));
        bar.add(c);
        bar.add(f);
        bar.add(r);
        bar.add(t);
        bar.add(pot); 
        p = new JPanel(new GridBagLayout());
        p.setBackground(new Color(0,80,0));
        BufferedImage myPicture = null;
        List<Card> a = player.getCards();
		try {
			myPicture = ImageIO.read(new File(a.get(0).getFileName()));
			myPicture = Display.scaleImage(myPicture, BufferedImage.TYPE_INT_RGB, (int)(myPicture.getWidth()*1.5),(int)( myPicture.getHeight()*1.5));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        
        BufferedImage myPicture1 = null;
		try {
			myPicture1 = ImageIO.read(new File(a.get(1).getFileName()));
			myPicture1 = Display.scaleImage(myPicture1, BufferedImage.TYPE_INT_RGB, (int)(myPicture1.getWidth()*1.5),(int)( myPicture1.getHeight()*1.5));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        JLabel picLabel1 = new JLabel(new ImageIcon(myPicture1));
        p.setLayout(null);
        frame.pack();
        int w = (int)(picLabel1.getPreferredSize().getWidth());
        int h = (int)(picLabel1.getPreferredSize().getHeight());
        int w1 =(int)(830 - w) ;
        int h1 = (int)(700);
        System.out.println("" + w1 + " " + h1 + " " + (w1+w) + " " + (h1+h));
        picLabel1.setBounds(w1,h1, w ,h );
        picLabel.setBounds(w1+w,h1, w+w, h);
        p.add(picLabel);
        p.add(picLabel1);
        
      
        //stuff in the big Panel
        frame.add(p);
        frame.add(bar, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    public static BufferedImage scaleImage(BufferedImage image, int imageType,
        int newWidth, int newHeight) {
        // Make sure the aspect ratio is maintained, so the image is not distorted
        double thumbRatio = (double) newWidth / (double) newHeight;
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double aspectRatio = (double) imageWidth / (double) imageHeight;

        if (thumbRatio < aspectRatio) {
            newHeight = (int) (newWidth / aspectRatio);
        } else {
            newWidth = (int) (newHeight * aspectRatio);
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
    public String getInput()
    {
    	String temp = input;
    	input = "";
    	return temp;
    }

	private void drawCard(Graphics g, Card card, int x, int y) {
		if (card == null) {
			String fileName = ".//cards//imgCards.png";
			Image image = new ImageIcon(fileName).getImage();
			g.drawImage(image, x, y, CARD_WIDTH, CARD_HEIGHT, null);
		} else {
			String fileName = card.getFileName();
			if (!new File(fileName).exists())
				throw new IllegalArgumentException("bad file name:  " + fileName);
			Image image = new ImageIcon(fileName).getImage();
			g.drawImage(image, x, y, CARD_WIDTH, CARD_HEIGHT, null);
		}
	}	
    public void paintComponent(Graphics g) {
    	if(player.getCommunityCards().size()!=0)
    	{
    		List<Card> c = player.getCommunityCards();
    		//System.out.println("drawing cards");

        	int dist = (int)(820 - 109*2.5);
        	for(int i = c.size();i<5;i++)
        		c.add(null);
        	
        	for(int i = 0;i<5;i++) 
        	{
        		drawCard(g, c.get(i), dist+190*i, 350);
        	}	
    	}
    	pot.setText("Pot: "+player.getPot());
    }
}