import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

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
public class Display extends JFrame implements MouseListener
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
        frame.setSize(new Dimension(2000,2000));
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
		try {
			myPicture = ImageIO.read(new File(".//Cards//2c.gif"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        
        BufferedImage myPicture1 = null;
		try {
			myPicture1 = ImageIO.read(new File(".//Cards//2s.gif"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        JLabel picLabel1 = new JLabel(new ImageIcon(myPicture1));
        
        
        GridBagConstraints c = new GridBagConstraints();
                
        c.gridx = 1;
        c.gridy = 1;
        
        picLabel.setBounds(25,25,125,125);
        picLabel1.setBounds(125,125,225,225);
        
        p.add(picLabel);
        p.add(picLabel1);
        
      
        //stuff in the big Panel
        frame.add(p);
        frame.add(bar, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
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

}