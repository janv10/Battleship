import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Class GameOverScreen - displays image of the winner player 
 * 					   - to exit the game, click anywhere on the screen 
 * @author Jahnvi, Pawel
 *
 */

public class EndCredits implements MouseListener{
	
	private JPanel frame;
	private ImageIcon foreGroundImage;
	private JLabel c;
	JButton exit; 
	
	public EndCredits(JFrame app, boolean playerOneWin){
		frame = (JPanel)app.getContentPane();
		
		/* Player 1 wins */
		if (playerOneWin){
			foreGroundImage = new ImageIcon("player1_wins.png");
		}
		
		/* Player 2 wins */
		else {
			foreGroundImage = new ImageIcon("player2_wins.png");
		}
		
		/* Add image to the container, set size and colors */
		c = new JLabel(foreGroundImage);
		int frameWidth = frame.getWidth();
		int frameHeight = frame.getHeight();
		
		c.setSize(frameWidth, frameHeight);
		c.setLocation(0, 0); 
		c.setBackground(Color.BLACK);
		c.setForeground(Color.BLACK);
		
		/* Click anywhere on the board to end/exit the game */
		c.addMouseListener(this);
	}
	
	public void loadEndScreen() {
		/* Add the container to the frame */
		frame.add(c); 
		frame.setComponentZOrder(c, 0);
		frame.setVisible(true);
		
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * Function: mouseReleased
	 * exit the game whenever called 
	 */
	public void mouseReleased(MouseEvent e) {
		System.exit(0);
	}
	
	
	/* --------------------------------------------- */
	
	/**
	 * Added unimplemented methods for error ceasing 
	 */

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}
}
