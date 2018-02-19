import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;
/**
 * Creates a grid array for main game panel and hit ship game panel
 * 	 that goes on the grid
 * @author janvi
 *
 */

public class GridCreator extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage gridImage = null;
	private Object[][] gridArray;
	private Ship[] shipArray;
	private JPanel[] panelArray;
	private JButton donePlacingShips; 
	private JButton randShipPlacer;
	private JFrame frame;
	private volatile boolean completed = false;
	public static final int width = 54;
	public static final int height = 56;
	public static final int spaceSize = 47;
	public static final int border = 5;
	public static boolean currentlyPlacingShip = false;

	public GridCreator(Ship[] shipArray, JFrame app) {
		this(shipArray, 10, app);
	}

	public GridCreator(Ship[] shipArray, int gridSize, JFrame app) {
		this(shipArray, gridSize, "GameGrid.png", app);
	}

	public GridCreator(Ship[] shipArray, int gridSize, String path, JFrame app) {
		setLayout(null);
		setBackground(new Color(199, 176, 151));
		setLocation(0,0);
		frame = app;

		Object[][] grid = new Object[gridSize][gridSize];
		int gridLength = grid.length; 
		
		for (int i = 0; i < gridLength; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = 1;
			}
		}

		gridArray = grid;
		this.shipArray = shipArray;
		panelArray = new JPanel[shipArray.length];

		try {
			gridImage = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("Failed to load image");
		}

	}


	
	/**
	 * Set up the Main Game Grid 
	 */
	public void setup() {
		int largestShipSize = 0;
		for (int i = 0; i < shipArray.length; i++){
			int temp = shipArray[i].getShipPieces().length;
			if (temp > largestShipSize){
				largestShipSize = temp;
			}
		}
		
		int spaceBorder = spaceSize + border; 
		int getGridLength = gridArray.length; 
		int getArrLength = shipArray.length; 
		
		int setWidth = width + ((spaceBorder) * getGridLength) + (2 * border) + 50 + ((largestShipSize + 1) * spaceSize);
		int setHeight = height + ((spaceBorder) * (getGridLength + 1));
		
		int withHeightChanged = 2 * spaceSize + (getArrLength * (spaceBorder+ 2)); 
		if (setHeight < withHeightChanged) {
			
			setHeight = withHeightChanged;
		}
		frame.setPreferredSize(new Dimension(setWidth, setHeight));
		frame.setMinimumSize(new Dimension(setWidth, setHeight));
		frame.pack();
		setSize(frame.getContentPane().getSize());

		JLabel gridLabel = new JLabel(new ImageIcon(gridImage));
		gridLabel.setSize(width + gridArray.length + 1 + ((spaceSize + border) * gridArray.length),
				height + gridArray.length + 1 + ((spaceSize + border) * (gridArray.length)));
		gridLabel.setLocation(0, 0);
		gridLabel.setHorizontalAlignment(SwingConstants.LEFT);
		gridLabel.setVerticalAlignment(SwingConstants.TOP);
		add(gridLabel);

		int buttonXPos = gridLabel.getWidth();

		randShipPlacer = new JButton("Generate Random Ship Placement");
		randShipPlacer.setBounds(buttonXPos, 0, frame.getWidth() - buttonXPos, spaceSize - 5);
		randShipPlacer.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				Random rand = new Random();
				for (int i = 0; i < panelArray.length; i++) {
					panelArray[i].setLocation(shipArray[i].getStartingOffGridPosition());
				}
				for (int i = 0; i < panelArray.length; i++) {
					int timeout = 0;
					while (timeout < 500
							&& shipArray[i].getStartingOffGridPosition().equals(panelArray[i].getLocation())) {
						int x = rand.nextInt(gridArray.length);
						int y = rand.nextInt(gridArray.length);
						timeout++;
						lClick(i, x, y);
						if (rand.nextInt(2) == 0
								&& !shipArray[i].getStartingOffGridPosition().equals(panelArray[i].getLocation())) {
							rClick(i, x, y);
						}
					}
				}
			}
		});
		add(randShipPlacer);
		randShipPlacer.setVisible(true);
		repaint();

		/* Button ends setup when called */
		donePlacingShips = new JButton("Done Placing Battleships");
		
		/* Set the button to green */
		donePlacingShips.setBackground(new Color(49, 218, 57)); 
		donePlacingShips.setBorderPainted(false);
		donePlacingShips.setOpaque(true);
		donePlacingShips.setBounds(buttonXPos, spaceSize - 5, frame.getWidth() - buttonXPos, spaceSize - 5);
		donePlacingShips.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				completed = true;
			}
		});
		
		add(donePlacingShips);
		donePlacingShips.setVisible(false);

		/* Loop through all the ships and set each chunk of a ship horizontally */
		for (int j = 0; j < shipArray.length; j++) {
			final int shipNum = j;

			JPanel panel = new JPanel();
			panel.setBackground(new Color(131, 92, 59));
			panel.setOpaque(false);
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			panel.add(Box.createRigidArea(new Dimension(0, 0)));

			/* Loop through all the grid boxes of the ship */
			for (int i = 0; i < shipArray[j].getShipPieces().length; i++) {
				
				ImageIcon icon = new ImageIcon(shipArray[j].getShipPieces()[i].getShipImage());
				JLabel label = new JLabel(icon);
				panel.add(label);
				panel.add(Box.createRigidArea(new Dimension(border + 2, 0)));
		

			}
			
			int spaceBorder1 = spaceSize + border; 
			int width1 = width + ((spaceBorder1) * gridArray.length) + (2 * border) + 50;
			int height1 = spaceBorder1 + 2 + (j * (spaceBorder1 + 2));
			panel.setLocation(width1, height1);
			panel.setSize(shipArray[j].getShipPieces().length * (spaceBorder1), spaceSize);
			shipArray[shipNum].setStartingOffGridPosition(panel.getLocation());
			add(panel);
			panelArray[j] = panel;
			setComponentZOrder(panel, 0);

			panel.addMouseMotionListener(new MouseMotionAdapter() {

				@Override
				public void mouseDragged(MouseEvent e) {
					if (SwingUtilities.isLeftMouseButton(e)) {
						JPanel component = (JPanel) e.getComponent().getParent().getParent();
						Point pt = new Point(SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), component));
						int halfSpace = spaceSize / 2;
						panel.setLocation((int) pt.getX() - (halfSpace), (int) pt.getY() - (halfSpace));
						currentlyPlacingShip = true;
					}
				}

			});
			
			/* Adding mouse listener to the panel */
			panel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					// gets the coordinates of the mouse in terms of the window
					JPanel component = (JPanel) e.getComponent().getParent().getParent();
					Point pt = new Point(SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), component));
					int c1 = 0;
					int c2 = 0;
					int spaceBorder = spaceSize + border; 

					int value = (int) pt.getX();

					while (width + ((spaceSize + border) * c1) < value) {
						c1++;
					}
					c1--;

					int value2 = (int) (pt.getY());
					while (height + ((spaceBorder) * c2) < value2) {
						c2++;
					}
					c2--;
					
					if (e.getButton() == MouseEvent.BUTTON1) {

						currentlyPlacingShip = false;
						lClick(shipNum, c1, c2);

					} 
					
					else if (e.getButton() == MouseEvent.BUTTON3) {
						rClick(shipNum, c1, c2);
					}

					donePlacingShips.repaint();

				}

			});
		}
	}

	/*
	 * Function: rotatePanel
	 * Gets called when right click is pressed on a ship panel. Attempts to
	 * rotate the panel
	 */
	private void rClick(int shipNum, int x, int y) {
	
		boolean isVertical = false;
		if (((BoxLayout) panelArray[shipNum].getLayout()).getAxis() == BoxLayout.Y_AXIS) {
			isVertical = true;
		}
		
		removeShip(shipArray[shipNum], isVertical);

		if (rotGPanel(panelArray[shipNum]) && !currentlyPlacingShip) {
			
			shipToGrid(shipArray[shipNum], new Point(x, y), !isVertical);
		} else if (!currentlyPlacingShip) {
			panelArray[shipNum].setLocation(shipArray[shipNum].getStartingOffGridPosition());
			rotGPanel(panelArray[shipNum]);
		}

		displayDoneButton();
	}

	
	/*Function: leftClick
	 * Gets called when left mouse is pressed on a ship panel
	 * 		used to make horizontal ships vertical 
	 */
	private void lClick(int shipNum, int x, int y) {

		if ((((BoxLayout) panelArray[shipNum].getLayout()).getAxis() == BoxLayout.X_AXIS)) {
			if (x < gridArray.length - panelArray[shipNum].getWidth() / spaceSize + 1 && x >= 0) {
				if (y < gridArray[0].length - panelArray[shipNum].getHeight() / spaceSize + 1 && y >= 0) {
					
					placeShipOnGrid(x, y, shipNum, false);
				} else {
					panelArray[shipNum].setLocation(shipArray[shipNum].getStartingOffGridPosition());
					removeShip(shipArray[shipNum], false);
				}
			} else {
				panelArray[shipNum].setLocation(shipArray[shipNum].getStartingOffGridPosition());
				removeShip(shipArray[shipNum], false);
			}
		} 
		else {
			
			if (x < gridArray.length - panelArray[shipNum].getWidth() / spaceSize + 1 && x >= 0) {
				if (y < gridArray[0].length - panelArray[shipNum].getHeight() / spaceSize + 1 && y >= 0) {
					
					placeShipOnGrid(x, y, shipNum, true);
				} else {
					rotGPanel(panelArray[shipNum]);
					panelArray[shipNum].setLocation(shipArray[shipNum].getStartingOffGridPosition());
					removeShip(shipArray[shipNum], true);
				}
			} else {
				rotGPanel(panelArray[shipNum]);
				panelArray[shipNum].setLocation(shipArray[shipNum].getStartingOffGridPosition());
				removeShip(shipArray[shipNum], true);
			}
		}

		displayDoneButton();
	}

	/*
	 * Method for placing a ship panel on the grid image
	 */
	private void placeShipOnGrid(int x, int y, int shipNum, boolean isVertical) {
		panelArray[shipNum].setLocation(width + x + (((spaceSize + border) * x) + border / 2),
				height + y + ((spaceSize + border) * y) + border / 2);
		if (isIntersection(panelArray[shipNum])) {
			if (isVertical) {
				rotGPanel(panelArray[shipNum]);
			}

			removeShip(shipArray[shipNum], false);
			panelArray[shipNum].setLocation(shipArray[shipNum].getStartingOffGridPosition());

		} else {
			removeShip(shipArray[shipNum], isVertical);
			shipToGrid(shipArray[shipNum], new Point(x, y), isVertical);

		}
	}



	/**
	 * Function: displayDoneButton
	 * 
	 * Checks if the show finish button should be added, and if it should be add
	 */
	private void displayDoneButton() {
		boolean showButton = true;
		if (!currentlyPlacingShip) {
			for (int i = 0; i < shipArray.length; i++) {
				if (shipArray[i].getStartingOffGridPosition().equals(panelArray[i].getLocation())) {
					showButton = false;
				}
			}
			donePlacingShips.setVisible(showButton);
		}
	}

	
	/**
	 *  isIntersection
	 * @param p
	 * @return
	 * 
	 * checks for intersections with other panels
	 */
	private boolean isIntersection(JPanel p) {
		for (int i = 0; i < panelArray.length; i++) {
			
			if (p.getBounds().intersects(panelArray[i].getBounds()) && !p.equals(panelArray[i])) {
				return true;
			}
		}
		return false;
	}


	/**
	 * removeShip
	 * @param ship
	 * @param isVertical
	 * 
	 * remove said ship from the array 
	 */
	private void removeShip(Ship ship, boolean isVertical) {
		for (int i = 0; i < gridArray.length; i++) {
			for (int j = 0; j < gridArray[i].length; j++) {
				for (int k = 0; k < ship.getShipPieces().length; k++) {
					if (gridArray[j][i] == (ShipPiece) ship.getShipPieces()[k]) {
						gridArray[j][i] = 1;
					}
				}
			}
		}
	}

	
	/**
	 * shipToGrid
	 * @param ship
	 * @param location
	 * @param isVertical
	 * 
	 * add ships to the grid 
	 */
	private void shipToGrid(Ship ship, Point location, boolean isVertical) {

	
		if (location.getX() < gridArray.length && location.getX() >= 0 && location.getY() < gridArray.length
				&& location.getY() >= 0) {
			for (int i = 0; i < ship.getShipPieces().length; i++) {
				if (isVertical) {
				
					gridArray[(int) location.getX()][(int) location.getY() + i] = ship.getShipPieces()[i];
				} else {
			
					gridArray[(int) location.getX() + i][(int) location.getY()] = ship.getShipPieces()[i];
				}
			}
		}
	}


	
	/**
	 * rotGPanel
	 * @param panel
	 * @return
	 * 
	 * rotate game panel (attempt)
	 */
	private boolean rotGPanel(JPanel panel) {
	
		if (((BoxLayout) panel.getLayout()).getAxis() == BoxLayout.X_AXIS) {
			if (panel.getX() > width + ((spaceSize + border) * gridArray.length) && !currentlyPlacingShip) {
				return false;
			}
		
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			
			int temp = panel.getWidth();
			int temp2 = panel.getHeight();
			panel.setSize(temp2, temp);
	
			for (int i = 0; i < panel.getComponentCount(); i++) {
				if (!panel.getComponent(i).getClass().toString().equals("JPanel")) {
					panel.add(Box.createRigidArea(new Dimension(0, border + 2)), i);
					panel.remove(++i);
				}
			}
			panel.add(Box.createRigidArea(new Dimension(0, 0)), 0);
			panel.remove(1);
		
			panel.validate();


			panel.setLocation(panel.getX(), panel.getY());

			
			int counter = 0;
			while (height + ((spaceSize + border) * counter) < panel.getY() + panel.getWidth()) {
				counter++;
			}
			counter--;

			if (!(counter <= gridArray[0].length - panel.getHeight() / spaceSize && counter >= 0)
					|| isIntersection(panel)) {
				return false;
			}
		} else if (((BoxLayout) panel.getLayout()).getAxis() == BoxLayout.Y_AXIS) {
		
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
	
			int temp = panel.getWidth();
			int temp2 = panel.getHeight();
			panel.setSize(temp2, temp);
			
			for (int i = 0; i < panel.getComponentCount(); i++) {
				if (!panel.getComponent(i).getClass().toString().equals("JPanel")) {
					panel.add(Box.createRigidArea(new Dimension(border + 2, 0)), i);
					panel.remove(++i);
				}
			}
			panel.add(Box.createRigidArea(new Dimension(0, 0)), 0);
			panel.remove(1);
		
			panel.validate();


			panel.setLocation(panel.getX(), panel.getY());

		
			int counter = 0;
			while (width + ((spaceSize + border) * counter) < panel.getX() + panel.getHeight()) {
				counter++;
			}
			counter--;

			if (!(counter <= gridArray.length - panel.getWidth() / spaceSize && counter >= 0)
					|| isIntersection(panel)) {
			
				return false;
			}

		}
		
		return true;
	}

	
	/**
	 * Function: getGridArray
	 * @return
	 * returns the grid array
	 */
	public Object[][] returnGridArray() {
		return gridArray;
	}

	
	/**
	 * Function: completeSetup
	 * @return
	 * 
	 * returns when setup is over 
	 * 
	 */
	public boolean completeSetup() {
		return completed;
	}

	
	/**
	 * Function: getButton
	 * @return
	 * 
	 * return when done placing the ships 
	 */
	public JButton getButton() {
		return donePlacingShips;
	}

}
