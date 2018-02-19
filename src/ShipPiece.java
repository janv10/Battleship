import java.awt.*;
import javax.swing.ImageIcon;
/**
 * Assign ship pieces to each player
 * 	blue ship is player 1
 * 	green ship is player 2
 * @author Jahnvi
 *
 */
public class ShipPiece {
	private Image shipPieceAlive;
	private boolean shipIsDead;
	boolean isPlayer1;

	/*
	 * Constructor that has a boolean to determine which player the ship piece
	 * belongs to. false is player 2, true is player 1
	 */
	public ShipPiece(boolean isPlayer1) {
		this.isPlayer1 = isPlayer1;
		// sets the image based on which player it belongs too
		if (isPlayer1)
			shipPieceAlive = new ImageIcon("player1_blue.png").getImage();
		else
			shipPieceAlive = new ImageIcon("player2_green.png").getImage();
		shipIsDead = false;
	}

	
	/**
	 * setImage
	 * @param file
	 * 
	 * set image according to the given file 
	 */
	public void setImage(String file) {
		shipPieceAlive = new ImageIcon(file).getImage();

	}


	/**
	 * Function: getShipImage 
	 * @return
	 * 
	 * returns the ship's image 
	 */
	public Image getShipImage() {
		return shipPieceAlive;
	}

	
	/**
	 * Function: destroy
	 * Display the correct player's ship when the ship is hit 
	 */
	public void destroy() {
		shipIsDead = true;
		if (isPlayer1) {
			setImage("player1_hit.png");
		} else {
			setImage("player2_hit.png");
		}
	}


	/**
	 * Function: destroyedShip
	 * @return
	 * 
	 * return if ship is destroyed 
	 */
	public boolean destroyedShip() {
		return shipIsDead;
	}

}
