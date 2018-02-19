import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
/**
 * Wait for the opponent player after each turn 
 * @author Jahnvi, Pawel 
 *
 */
public class WaitingForPlayer implements MouseListener {

	private JPanel panel;
	private ImageIcon backgroundImageIcon;
	private JLabel c;
	private volatile boolean isImageDisplayed;
	private Grid mainGameGrid;
	private HitShipGrid hitShipGrid;

	public WaitingForPlayer(JPanel gameScreen, Grid mainGameGrid, HitShipGrid hitShipGrid) {
		panel = gameScreen;
		backgroundImageIcon = new ImageIcon("WaitingOpponent.png");
		Image bkgImage = backgroundImageIcon.getImage();
		Image scaledBkgImage = bkgImage.getScaledInstance(panel.getWidth(), panel.getHeight(),
				BufferedImage.SCALE_FAST);
		ImageIcon scaledBkgImageIcon = new ImageIcon(scaledBkgImage);
		c = new JLabel(scaledBkgImageIcon);
		
		int getPanelWidth = panel.getWidth(); 
		int getPanelHeight = panel.getHeight();
		
		c.setSize(getPanelWidth, getPanelHeight);
		
		c.setLocation(0, 0);
		isImageDisplayed = true;
		this.mainGameGrid = mainGameGrid;
		this.hitShipGrid = hitShipGrid;
	}

	/**
	 * Function: loadWaitingScreen add container, and mouse listener to the game
	 * 
	 */
	public void loadWaitingScreen() {
		panel.add(c);
		c.addMouseListener(this);
		panel.setVisible(true);
		panel.repaint();
	}

	/**
	 * Function: isImageVisible
	 * 
	 * @return
	 * 
	 * 		return true or false for visible image
	 */
	public boolean isImageDisplayed() {
		return isImageDisplayed;
	}

	/**
	 * Remove the "waiting for opponent image when clicked anywhere on the screen
	 */
	public void mouseReleased(MouseEvent arg) {
		/* remove the container from the plane */
		panel.remove(c);
		panel.revalidate();
		panel.repaint();
		mainGameGrid.setTurn(true);
		mainGameGrid.setVisible(true);
		hitShipGrid.setVisible(true);
	}

	/* --------------------------------------------------- */
	/**
	 * Added unimplemented methods for error ceasing
	 */

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
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
