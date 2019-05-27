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
public class Display extends JFrame
{
    private JFrame frame;
    private RemotePlayer player;
    private JPanel bar;
    private JPanel p;
    private JButton c;
    private JButton f;
    private JButton r;
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

        frame = new JFrame("Poker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        frame.setVisible(true);

    }
<<<<<<< HEAD
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
=======
>>>>>>> parent of 1442e43... Progress in front-end and pocket display.
    public String getInput()
    {
    	String temp = input;
    	input = "";
    	return temp;
    }
}