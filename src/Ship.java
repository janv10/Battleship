import java.awt.*;
import java.util.*;

/**
 * Class ship - puts ship pieces in list, array - gets starting position of the
 * grid - Checks for dead ships and marks dead, if dead
 * 
 * @author Jahnvi, Pawel
 *
 */

public class Ship {

	private ShipPiece[] pieces;
	private Point startPos;

	/**
	 * 
	 * @param list
	 * 
	 *            Turn/store game (ship) pieces into a list
	 */
	Ship(ShipPiece[] list) {
		pieces = list;

		/* Set default starting position to 0,0 */
		startPos = new Point(0, 0);
	}

	/**
	 * 
	 * @param list
	 * 
	 *            Turn/store the game(ship) piece into an array
	 */
	Ship(ArrayList<ShipPiece> list) {
		pieces = list.toArray(new ShipPiece[0]);
		startPos = new Point(0, 0);
	}

	/**
	 * Function: CheckIfDead
	 * 
	 * @return
	 * 
	 * 		If the ship is dead - return this information
	 */
	public boolean checkIfDead() {
		boolean deadShip = true;
		int getpieceLength = pieces.length;

		/* Loop through the pieces of the ship to check if it is hit or not */
		for (int i = 0; i < getpieceLength; i++) {
			if (!pieces[i].destroyedShip()) {
				deadShip = false;
			}
		}
		return deadShip;
	}

	/**
	 * Function: GetShipPieces
	 * 
	 * @return
	 * 
	 * 		returns the pieces of ships
	 */
	public ShipPiece[] getShipPieces() {
		return pieces;
	}

	/**
	 * Function: setStartingOffGridPosition
	 * 
	 * @param sp
	 * 
	 *            "set" the starting position of the grid
	 */
	public void setStartingOffGridPosition(Point sp) {
		startPos = sp;
	}

	/**
	 * Function: getStartingOffGridPosition
	 * 
	 * @return
	 * 
	 * 		return the grid board's starting position
	 */
	public Point getStartingOffGridPosition() {
		return startPos;
	}
}