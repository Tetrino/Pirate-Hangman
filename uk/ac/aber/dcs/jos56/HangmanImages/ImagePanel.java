package uk.ac.aber.dcs.jos56.HangmanImages;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;


public class ImagePanel extends JPanel{

	private Image storedImage;
	
	/**
	 * This class loads and stores an image into the system to be painted by the animation board.
	 * 
	 * @param imageSource A string dictating the file location of the image.
	 */
	public ImagePanel(String imageSource){
		storedImage = Toolkit.getDefaultToolkit().getImage(imageSource);
		this.setLayout(null);
		this.setOpaque(false);
		repaint();
	}
	
	/**
	 * This class paints the image at its original size.
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(storedImage, 0, 0, this);
	}
}
