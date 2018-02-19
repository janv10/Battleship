import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Class displays current player's ships on the right hand panel of the frame
 * 
 * @author Jahnvi
 *
 */
public class HitShipGrid extends JPanel {
	private static final long serialVersionUID = 1L;
	private Object[][] array;
	private BufferedImage gridImage;

	/*
	 * Create Globals for x - width of the image y - height of the image tileSize -
	 * size of ship tiles borderSize - incorporate for the space between 2 squares
	 * of the grid pieceVal - size of the small piece on the small grid
	 */

	public static final int x = 23;
	public static final int y = 39;
	public static final int tileSize = 20;
	public static final int borderSize = 3;
	public static final int pieceVal = 18;

	/*
	 * Constructor that takes a 2d array object
	 */
	public HitShipGrid(Object[][] array) {
		this.array = array;

		/* Set the background of the array to white */
		setBackground(Color.WHITE);
		int calc1 = (tileSize + borderSize) * array.length;
		int setWidth = x + 2 + calc1;
		int setHeight = y + 2 + calc1;

		setPreferredSize(new Dimension(setWidth, setHeight));
		setSize(getPreferredSize());

		/* Load File with error checking */
		try {
			gridImage = ImageIO.read(new File("shipGameGrid.png"));
		} catch (IOException e) {
			System.out.println("Error: Image not found.");
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		/* Display the grid */
		g2.drawImage(gridImage, 0, 15, this);

		/* Loop through the entire array of the small board and place the ships */
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				// if there is a ship piece at the location
				if ((array[i][j]).getClass().getName().equals("ShipPiece")) {
					// draw the image of the ship piece at the proper grid
					// location
					g2.drawImage(((ShipPiece) array[i][j]).getShipImage(),
							x + 2 + ((tileSize + borderSize) * i) + borderSize / 2,
							y + 2 + ((tileSize + borderSize) * j) + borderSize / 2, pieceVal, pieceVal, this);
				}
			}
		}
	}

	/*
	 * Returns the array
	 */
	public Object[][] getArray() {
		return array;
	}

	/*
	 * Sets the array
	 */
	public void setArray(Object[][] array) {
		this.array = array;
	}

}
