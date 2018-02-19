import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
/**
 * Creates the main grid where the game is played 
 * @author jahnvi, 
 *
 */
public class Grid extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	private BufferedImage gridImage;
	private Object[][] array;

	/*
	 * Create Globals for x - width of the image y - height of the image tileSize -
	 * size of ship tiles borderSize - incorporate for the space between 2 squares
	 * of the grid
	 */
	public static final int widthImage = 54;
	public static final int heightImage = 56;
	public static final int tileSize = 47;
	public static final int borderSize = 5;
	private volatile boolean validNextMove;
	private boolean val;
	
	public Color oceanBlue = new Color (43, 101, 236);

	/**
	 * Function: Grid Create a default 10x10 array
	 */
	public Grid() {
		this(new Object[10][10], "GameGrid.png");
	}

	/**
	 * Function: Grid
	 * 
	 * @param arr
	 *            - array that goes with the image
	 * 
	 *            Combine array and image
	 */
	public Grid(Object[][] arr) {
		this(arr, "GameGrid.png");
	}



	/**
	 * Function: Grid
	 * 
	 * @param arr
	 * @param path
	 * 
	 *            Create an array board with an image
	 */
	public Grid(Object[][] arr, String path) {
		array = arr;
		validNextMove = true;
		val = false;

		/* set background black */
		setBackground(Color.black);

		int arrLength = arr.length;
		int calc1 = (tileSize + borderSize) * arrLength;
		int setWidth = widthImage + arrLength + 1 + (calc1);
		int setHeight = heightImage + arrLength + 1 + (calc1);

		setPreferredSize(new Dimension(setWidth, setHeight));
		setSize(getPreferredSize());
		setLocation(0, 0);

		try {
			gridImage = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("Failed to load image");
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.drawImage(gridImage, 0, 0, this);

		/* Create a loop to go through all the squares of the grid */
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {

				int titleAndBoardAndI = (tileSize + borderSize) * i;
				int titleAndBoardAndJ = (tileSize + borderSize) * j;
				/* Check if a ship has been hit or not */
				if (array[i][j].equals((Object) 1) || ((array[i][j]).getClass().getName().equals("ShipPiece")
						&& !((ShipPiece) array[i][j]).destroyedShip())) {

					/* When the game starts make all the board pieces blue, water-like */
					g2.setColor(oceanBlue);
					
					int doWidth = widthImage + i + 1 + (titleAndBoardAndI);
					int doHeight = heightImage + j + 1 + (titleAndBoardAndJ);
					int calc1 = tileSize + (borderSize / 2) - 1;

					g2.fillRect(doWidth, doHeight, calc1, calc1);

				}
				/* If there was a ship there, and is now destroyed */
				else if ((array[i][j]).getClass().getName().equals("ShipPiece")) {
					
					/* Display the image of the destroyed ship */
					g2.drawImage(((ShipPiece) array[i][j]).getShipImage(),
							widthImage + i + titleAndBoardAndI + borderSize / 2,
							heightImage + j + titleAndBoardAndJ + borderSize / 2, this);
				}
			}
		}

	}

	/* Left Click turns the ships Vertical */
	public void mouseReleased(MouseEvent e) {
		int tileBor = tileSize + borderSize; 
		
		if (validNextMove && e.getButton() == MouseEvent.BUTTON1) {

			
			int value = e.getX();
			int counter1 = 0;
			while (widthImage + ((tileBor) * counter1) + borderSize < value) {
				counter1++;
			}
			counter1--;

			int value2 = e.getY() - (tileSize / 2);
			int counter2 = 0;
			while (heightImage + ((tileBor) * counter2) + borderSize < value2) {
				counter2++;
			}
			counter2--;

			if (counter1 < array.length && counter1 >= 0) {
				if (counter2 < array[0].length && counter2 >= 0) {
					if (array[counter1][counter2].equals((Object) 1)) {
						
						array[counter1][counter2] = 0;
						repaint();
						validNextMove = false;
						
					} else if ((array[counter1][counter2]).getClass().getName().equals("ShipPiece")
							&& !((ShipPiece) array[counter1][counter2]).destroyedShip()) {
						((ShipPiece) array[counter1][counter2]).destroy();
						repaint();

						validNextMove = false;
					}
					val = false;
				}
			}
		}

		else if ((!validNextMove) && (e.getButton() == MouseEvent.BUTTON1)) {
			val = true;
		}
	}

	/**
	 * Function: isTurn
	 * 
	 * @return
	 * 
	 * 		Returns true/false for valid or invalid turn
	 */
	public boolean isTurn() {
		return validNextMove;
	}

	/**
	 * Function: setTurn
	 * 
	 * @param t
	 * 
	 *            Sets true/false value for valid or invalid turn
	 */
	public void setTurn(boolean t) {
		validNextMove = t;
	}

	/**
	 * Function: getArray
	 * 
	 * @return
	 * 
	 * 		Gets the current state of the array
	 */
	public Object[][] getArray() {
		return array;
	}

	/**
	 * Function: setArray
	 * 
	 * @param arr
	 * 
	 *            set array according to the grid value
	 */
	public void setArray(Object[][] arr) {
		array = arr;
	}

	public boolean getState() {
		return val;
	}

	public void setState(boolean s) {
		val = s;
	}

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
