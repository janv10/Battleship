import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * GameLogic class 	- sets the ships length and count
 * 					- puts the ship on the grid
 * 					- Checks if all the ships are hit 
 * 					
 * @author Jahnvi
 *
 */
public class logicBoard {

	public static int boardSize = 10;

	/* Sizes of ships - static not changing */
	public static int aircraftSize = 5;
	public static int battleshipSize = 4;
	public static int destroyerSize = 3;
	public static int patrolSize = 2;
	public static int submarineSize = 3;

	/* Count of ships - static not changing */
	public static int airCraftCount = 1;
	public static int battleshipCount = 1;
	public static int destroyerCount = 2;
	public static int patrolCount = 3;
	public static int submarineCount = 2;

	private JFrame frame;
	private boolean ongoingGame;
	
	public int i = 0; 
	public int j = 0;

	public void setUpWindow() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.BLUE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1000, 700));
		frame.setMinimumSize(new Dimension(1000, 700));
		// frame.setResizable(true);
		frame.pack();
		startGame();
	}

	public void startGame() {
		ongoingGame = true;

		MainMenu startMenu = new MainMenu(frame);
		startMenu.loadTitleScreen();
		while (startMenu.isImageVisible()) {
		}

		/* Initialize player 1 and 2 ships */
		Ship[] p1Ships = initShip(true);
		Ship[] p2Ships = initShip(false);

		Grid grid = new Grid(displayShipsAvailable(p1Ships));
		HitShipGrid small = new HitShipGrid(displayShipsAvailable(p2Ships));
		small.setLocation(grid.getWidth() + 10, grid.getY());

		int windowWidth = small.getX() + small.getWidth() + 10;
		frame.setPreferredSize(new Dimension(windowWidth, frame.getHeight()));
		frame.setSize(frame.getPreferredSize());
		frame.pack();

		frame.getContentPane().add(grid); // adds the grids to the window
		frame.getContentPane().add(small);
		frame.addMouseListener(grid);
		frame.setVisible(true);

		gameLoop(p1Ships, p2Ships, grid, small);
	}

	/**
	 * initShip
	 * @param isPlayerOne
	 * @return
	 * 
	 * create ship according to the size and count 
	 * and group them together in ascending order to display them on the screen 
	 * 
	 */
	private Ship[] initShip(boolean player1) {
		Ship[] aircrafts = makeShips(aircraftSize, aircraftSize, player1);
		Ship[] battleships = makeShips(battleshipSize, battleshipCount, player1);
		Ship[] destroyers = makeShips(destroyerSize, destroyerCount, player1);
		Ship[] patrols = makeShips(patrolSize, patrolCount, player1);
		Ship[] submarines = makeShips(submarineSize, submarineCount, player1);

		/* Place in order ascending size order */
		Ship[] ships; 
		ships = mergeShips(aircrafts, battleships);
		ships = mergeShips(ships, destroyers);
		ships = mergeShips(ships, submarines);
		ships = mergeShips(ships, patrols);

		return ships;
	}

	/**
	 * makeShips
	 * @param shipSize - size of ship
	 * @param shipNum - number of ship 
	 * @param player1 - player 1 or 2
	 * @return
	 * 
	 * each player has own sets of ship 
	 */
	private Ship[] makeShips(int shipSize, int shipNum, boolean player1) {
		Ship[] listOfShips = new Ship[shipNum];
		for (int i = 0; i < shipNum; i++) {
			ShipPiece[] shipArray = new ShipPiece[shipSize];
			for (int j = 0; j < shipSize; j++) {
				ShipPiece p = new ShipPiece(player1);
				shipArray[j] = p;
			}
			listOfShips[i] = new Ship(shipArray);
		}

		return listOfShips;
	}

	/**
	 * Function: mergeShips
	 * 
	 * @param shipType1
	 * @param shipType2
	 * @return
	 * 
	 * 		merge 2 ships arrays and return the concatenated array
	 */
	private Ship[] mergeShips(Ship[] shipType1, Ship[] shipType2) {
		int ship1Len = shipType1.length;
		int ship2Len = shipType2.length;
		
		Ship[] c = new Ship[ship1Len + ship2Len];
		System.arraycopy(shipType1, 0, c, 0, ship1Len);
		System.arraycopy(shipType2, 0, c, ship1Len, ship2Len);
		return c;
	}

	/**
	 * Function: displayShipsAvailable
	 * 
	 * @param ships
	 * @return
	 * 
	 * 		Display the available ships on the game board
	 */
	private Object[][] displayShipsAvailable(Ship[] ships) {
		GridCreator c = new GridCreator(ships, boardSize, frame);
		c.setup();

		frame.getContentPane().add(c);
		frame.getContentPane().repaint();
		frame.setVisible(true);
		while (!c.completeSetup()) {}
		frame.getContentPane().removeAll();
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
		return c.returnGridArray();
	}
	
	/* Add Mouse Listener to in between screens */
	private void betweenTurns(Grid mainGameGrid, HitShipGrid hitShipGrid) {
		frame.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				WaitingForPlayer betweenTurns = new WaitingForPlayer((JPanel) frame.getContentPane(), mainGameGrid, hitShipGrid);
				
				final Object[][] temp1 = mainGameGrid.getArray();
				final Object[][] temp2 = hitShipGrid.getArray();
				
				if (!mainGameGrid.isTurn() && ongoingGame) {
					
					/* Temporary make the hit ship grid and main grid invisible */
					mainGameGrid.setVisible(false);
					hitShipGrid.setVisible(false);
					
					mainGameGrid.setArray(temp2);
					hitShipGrid.setArray(temp1);
					
					/* After each turn wait for the other player */
					betweenTurns.loadWaitingScreen();
				}
			}
		});
	}

	/**
	 * gameLoop
	 * @param player1Ships
	 * @param player2Ships
	 * @param mainGameGrid
	 * @param hitShipGrid
	 */
	private void gameLoop(Ship[] player1Ships, Ship[] player2Ships, Grid mainGameGrid, HitShipGrid hitShipGrid) {
		betweenTurns(mainGameGrid, hitShipGrid);

		while (ongoingGame) {

			boolean Player1_shipsDead = true;
			boolean Player2_shipsDead = true;

			for (i = 0; i < player1Ships.length; i++) {
				if (player1Ships[i].checkIfDead()) {
					for (j = 0; j < player1Ships[i].getShipPieces().length; j++)
						player1Ships[i].getShipPieces()[j].setImage("Dead_Ship.png");
				} else {
					Player1_shipsDead = false;
				}
			}


			mainGameGrid.repaint();
			hitShipGrid.repaint();

			for (int i = 0; i < player2Ships.length; i++) {
				if (player2Ships[i].checkIfDead()) {
					for (j = 0; j < player2Ships[i].getShipPieces().length; j++)
						player2Ships[i].getShipPieces()[j].setImage("Dead_Ship.png");
				} else {
					Player2_shipsDead = false;
				}
			}

			/* update the look of the grid */
			mainGameGrid.repaint();
			hitShipGrid.repaint();

			/* If one of the player has all dead ships then stop running the game */
			if (Player1_shipsDead || Player2_shipsDead) {
				
				ongoingGame = false;
				
				int getLength = mainGameGrid.getArray().length; 
				
				for (i = 0; i < getLength ; i++) {
					for (j = 0; j < mainGameGrid.getArray()[i].length; j++) {
						if ((mainGameGrid.getArray()[i][j].equals((Object) 1))) {
							mainGameGrid.getArray()[i][j] = (Object) 0;
						}
					}
				}

				/* Game has ended - GameOver Class will display the image of the player who has won the game */
				EndCredits gameEnd = new EndCredits(frame, !Player1_shipsDead);
				gameEnd.loadEndScreen();
			}
		}

	}
}