import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Has title page, menubar items, ship and count of ship's information
 * @author Jahnvi, Pawel
 *
 */
public class MainMenu {
	
	private JFrame frame;
	private ImageIcon backgroundTitleImage;
	private JLabel bkgImageContainer;
	private JButton gridSizeBtn;
	private JButton joinGame;
	private JButton aircraftSize, destroyerSize, patrolSize, submarineSize, battleshipSize; ;
	private JButton aircraftCount, destroyerCount, patrolCount, submarineCount, battleshipCount;
	private JLabel printError;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenu help;
	public JMenuItem exitGame;
	public JMenuItem gameAbout;
	public JMenuItem authorAbout;
	public JMenuItem aboutNetworkBattleship;
	public JButton sizeInfo, countInfo; 

	private volatile boolean isImageVisible;
	
	private Color brown = new Color (199, 176, 151);
	private Color blue  = new Color (151, 174, 199);
	
	public MainMenu(JFrame displayFrame){
		frame = displayFrame;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setTitle("Networked Battleship");
		backgroundTitleImage = new ImageIcon("GameTitle.png");
		bkgImageContainer = new JLabel(backgroundTitleImage);
		isImageVisible = true;
		
		/* File Menu */
		menuBar = new JMenuBar();
		file = new JMenu();
		file.setText("File");
		menuBar.add(file);
		
		exitGame = new JMenuItem("Quit");
		exitGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		exitGame.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ie) {
				System.exit(0);
			}
		});
		file.add(exitGame);
		/* About Menu */
		help = new JMenu();
		help.setText("Help");
		menuBar.add(help);

		/* Display Information about the game - with title */
		gameAbout = new JMenuItem("About Our Game");
		gameAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));

		gameAbout.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent ie)
			{
					JTextArea gameText = new JTextArea();
					gameText.setLineWrap(true);
					gameText.setWrapStyleWord(true);
					gameText.append(" • The game is played on four 10x10 square grids. There are 2 players. \n");
					gameText.append(" • Blue is player 1, and Green is player 2. \n");
					gameText.append(" • The individual squares on the grid are identified by letters (A-J) & numbers (1-10).\n");
					gameText.append(" • Small right hand grid - displays your hit ships. \n");
					gameText.append(" • Big Main grid is for shooting shots at the opponent player's board.\n");
					gameText.append(" • Before the game begins, each player must arrange a number of ships secretly on the grid.\n");
					gameText.append(" • If you wish to make the horizontal ships vertical, simply left click on your mouse.\n");
					gameText.append(" • You may also use the 'Generate Random Ship Placement' to randomly place the ships.\n");
					gameText.append(" • After you have arranged the ships as you wish, press the green 'Done Placing BattleShip' button to begin.\n");
					gameText.append(" • Good Luck!\n");

					JScrollPane gameAboutPane = new JScrollPane(gameText); 
					gameAboutPane.setPreferredSize( new Dimension(600, 300)); 
					JOptionPane.showMessageDialog(null, gameAboutPane, "About This Game", JOptionPane.INFORMATION_MESSAGE); 
			}
		});
	
		help.add(gameAbout);
		
		/* Display information about Networked Battleship - with title */
		aboutNetworkBattleship = new JMenuItem("About Networked Battleship");
		aboutNetworkBattleship.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));

		aboutNetworkBattleship.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ie)
			{
					JTextArea aboutBattleshipText = new JTextArea();
					aboutBattleshipText.setLineWrap(true);
					aboutBattleshipText.setWrapStyleWord(true);
					aboutBattleshipText.append("How to play Networked Battleship:\n");
					aboutBattleshipText.append(" • Networked Battleship is a guessing game for two players.\n");
					aboutBattleshipText.append(" • It contains ruled grids on which the players' fleets of ships are marked.\n");
					aboutBattleshipText.append(" • The location of the fleet are concealed from the other player\n");
					aboutBattleshipText.append(" • Players alternate turns shooting 'shots' at the other opponent player's ships.\n");
					aboutBattleshipText.append(" • The objective of the game is to destroy all of the opposing player's fleet.\n");
					JScrollPane gameAboutPane = new JScrollPane(aboutBattleshipText); 
					gameAboutPane.setPreferredSize( new Dimension(580, 300)); 
					JOptionPane.showMessageDialog(null, gameAboutPane, "About This Game", JOptionPane.INFORMATION_MESSAGE); 
			}
		});
		help.add(aboutNetworkBattleship);
		
		/* Display information about the authors - with title */
		authorAbout = new JMenuItem("About Authors");
		authorAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

		authorAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(null,
								"\t\t\t\t\t\t\t\t\t\t\t\tAuthors:\n" + "\t\t\tJahnvi Patel (jpate201)\n"
										+ "\t\t\tPawel Pietron (ppietr4)\n",
								"Authors of Networked Battleship", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		help.add(authorAbout);
		
		frame.setJMenuBar(menuBar);

	}
	
	
	public boolean canShipsFitOnBoard(){
		int totalShipSize = (logicBoard.battleshipCount * logicBoard.battleshipSize) + 
				(logicBoard.destroyerCount * logicBoard.destroyerSize) +
				(logicBoard.patrolCount * logicBoard.patrolSize) + 
				(logicBoard.submarineCount * logicBoard.submarineSize);
		if (totalShipSize > logicBoard.boardSize*logicBoard.boardSize){
			return false;
		}
		if (logicBoard.battleshipSize > logicBoard.boardSize){
			return false;
		}
		if (logicBoard.destroyerSize > logicBoard.boardSize){
			return false;
		}
		if (logicBoard.patrolSize > logicBoard.boardSize){
			return false;
		}
		if (logicBoard.submarineSize > logicBoard.boardSize){
			return false;
		}
		return true;
	}
	
	public void loadTitleScreen() {
		bkgImageContainer.setSize(frame.getContentPane().getWidth(),frame.getContentPane().getHeight()/2);
		bkgImageContainer.setLocation(0, 0); 
		bkgImageContainer.setVisible(true);
		
		printError = new JLabel("Error: grid is too small to fit the selected ships");
		printError.setForeground(Color.RED);
		printError.setFont(new Font("Impact", Font.PLAIN, 24));
		printError.setSize(440, 40);
		printError.setLocation(frame.getWidth()/2 - printError.getWidth()/2,
				frame.getHeight()-printError.getHeight() - 30);
		printError.setVisible(false);
		
		joinGame = new JButton("Join Game");
		joinGame.setSize(200, 100);
		joinGame.setLocation(150, bkgImageContainer.getHeight() + 50);
		joinGame.addActionListener(new ActionListener(){
			/* Remove  Title screen when Join Game is clicked */
			public void actionPerformed(ActionEvent arg) {
				frame.getContentPane().remove(joinGame);
				frame.getContentPane().remove(bkgImageContainer);
				frame.getContentPane().remove(gridSizeBtn);
				frame.getContentPane().remove(aircraftSize);
				frame.getContentPane().remove(destroyerSize);
				frame.getContentPane().remove(patrolSize);
				frame.getContentPane().remove(submarineSize);
				frame.getContentPane().remove(aircraftCount);
				frame.getContentPane().remove(destroyerCount);
				frame.getContentPane().remove(patrolCount);
				frame.getContentPane().remove(submarineCount);
				frame.getContentPane().remove(battleshipCount);
				frame.getContentPane().remove(battleshipSize);
				frame.getContentPane().setBackground(new Color(199, 176, 151));  //Complementary Brownish color 
				isImageVisible = false;
			}	
		});
		
		gridSizeBtn = new JButton();
		gridSizeBtn.setEnabled(false);
		gridSizeBtn.setSize(200, 100); 
		gridSizeBtn.setLocation(150, bkgImageContainer.getHeight() + joinGame.getHeight()  + 50);
		gridSizeBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg) {
				if (logicBoard.boardSize < 18){
					logicBoard.boardSize += 2;
				}else{
					logicBoard.boardSize = 6;
				}
				gridSizeBtn.setText("Grid Width: " + logicBoard.boardSize);
				boolean shipsFit = canShipsFitOnBoard();
				joinGame.setEnabled(shipsFit);
				printError.setVisible(!shipsFit);
			}	
		});
		
		
		aircraftSize = new JButton("Length of Aircraft: 5");
		aircraftSize.setEnabled(false);
		aircraftSize.setSize(200, 50); 
		aircraftSize.setLocation(frame.getContentPane().getWidth() - aircraftSize.getWidth() - 300, 
				bkgImageContainer.getHeight() + 50);
		
		
		aircraftCount = new JButton("Number of Aircrafts: 5");
		aircraftCount.setEnabled(false);
		aircraftCount.setSize(200, 50); 
		aircraftCount.setLocation(frame.getContentPane().getWidth() - aircraftCount.getWidth() - 100, 
				bkgImageContainer.getHeight() + 50);
		
		
		
		destroyerSize = new JButton("Length of Destroyer: 3");
		destroyerSize.setEnabled(false);
		destroyerSize.setSize(200, 50); 
		destroyerSize.setLocation(frame.getContentPane().getWidth() - destroyerSize.getWidth() - 300, 
				bkgImageContainer.getHeight() + aircraftSize.getHeight() + 50);
		
		
		
		destroyerCount = new JButton("Number of Destroyer: 2");
		destroyerCount.setEnabled(false);
		destroyerCount.setSize(200, 50); 
		destroyerCount.setLocation(frame.getContentPane().getWidth() - destroyerCount.getWidth() - 100, 
				bkgImageContainer.getHeight() + aircraftCount.getHeight() + 50);
		
		
		
		patrolSize = new JButton("Length of Patrol: 2");
		patrolSize.setEnabled(false);
		patrolSize.setSize(200, 50); 
		patrolSize.setLocation(frame.getContentPane().getWidth() - aircraftSize.getWidth() - 300, 
				bkgImageContainer.getHeight() + aircraftSize.getHeight() + destroyerSize.getHeight() + 50);
		
		

		patrolCount = new JButton("Number of Patrols: 3");
		patrolCount.setEnabled(false);
		patrolCount.setSize(200, 50); 
		patrolCount.setLocation(frame.getContentPane().getWidth() - patrolCount.getWidth() - 100, 
				bkgImageContainer.getHeight() +
				aircraftCount.getHeight() + 
				destroyerCount.getHeight() + 50);
		
		
		submarineSize = new JButton("Length of Submarine: 3");
		submarineSize.setEnabled(false);
		submarineSize.setSize(200, 50); 
		submarineSize.setLocation(frame.getContentPane().getWidth() - submarineSize.getWidth() - 300, 
				bkgImageContainer.getHeight() + 
				aircraftSize.getHeight() + 
				destroyerSize.getHeight() + 
				patrolSize.getHeight() + 50);
		
		submarineCount = new JButton("Number of Submarines: 2" );
		submarineCount.setEnabled(false);
		submarineCount.setSize(200, 50); 
		submarineCount.setLocation(frame.getContentPane().getWidth() - submarineCount.getWidth() - 100, 
				bkgImageContainer.getHeight() +
				aircraftCount.getHeight() + 
				destroyerCount.getHeight() + 
				patrolCount.getHeight() + 50);
		
		battleshipSize = new JButton("Length of Battleship: 3");
		battleshipSize.setEnabled(false);
		battleshipSize.setSize(200, 50); 
		battleshipSize.setLocation(frame.getContentPane().getWidth() - battleshipSize.getWidth() - 300, 
				bkgImageContainer.getHeight() + 
				aircraftSize.getHeight() + 
				destroyerSize.getHeight() + 
				patrolSize.getHeight() +
				submarineSize.getHeight( ) + 50);
		
		battleshipCount = new JButton("Number of Battleship: 2" );
		battleshipCount.setEnabled(false);
		battleshipCount.setSize(200, 50); 
		battleshipCount.setLocation(frame.getContentPane().getWidth() - battleshipCount.getWidth() - 100, 
				bkgImageContainer.getHeight() +
				aircraftCount.getHeight() + 
				destroyerCount.getHeight() + 
				patrolCount.getHeight()  +
				submarineSize.getHeight( ) + 50);
		
		/* Give Size Button Colors  - Brown */
		aircraftSize.setBackground(brown); 
		aircraftSize.setBorderPainted(false);
		aircraftSize.setOpaque(true);
		
		destroyerSize.setBackground(brown); 
		destroyerSize.setBorderPainted(false);
		destroyerSize.setOpaque(true);
		
		submarineSize.setBackground(brown); 
		submarineSize.setBorderPainted(false);
		submarineSize.setOpaque(true);
		
		patrolSize.setBackground(brown); 
		patrolSize.setBorderPainted(false);
		patrolSize.setOpaque(true);
		
		battleshipSize.setBackground(brown); 
		battleshipSize.setBorderPainted(false);
		battleshipSize.setOpaque(true);
		
		
		/* Give Count Button Colors - Blue */
		aircraftCount.setBackground(blue); 
		aircraftCount.setBorderPainted(false);
		aircraftCount.setOpaque(true);
		
		destroyerCount.setBackground(blue); 
		destroyerCount.setBorderPainted(false);
		destroyerCount.setOpaque(true);
		
		submarineCount.setBackground(blue); 
		submarineCount.setBorderPainted(false);
		submarineCount.setOpaque(true);
		
		patrolCount.setBackground(blue); 
		patrolCount.setBorderPainted(false);
		patrolCount.setOpaque(true);
		
		battleshipCount.setBackground(blue); 
		battleshipCount.setBorderPainted(false);
		battleshipCount.setOpaque(true);
	
		/* Set everything visible */
		joinGame.setVisible(true);
		gridSizeBtn.setVisible(false);
		aircraftSize.setVisible(true);
		destroyerSize.setVisible(true);
		submarineSize.setVisible(true);
		patrolSize.setVisible(true);
		battleshipSize.setVisible(true);
		battleshipCount.setVisible(true);
		aircraftCount.setVisible(true);
		destroyerCount.setVisible(true);
		submarineCount.setVisible(true);
		patrolCount.setVisible(true);
		
		
		
		frame.getContentPane().add(printError);
		frame.getContentPane().add(joinGame);
		frame.getContentPane().add(bkgImageContainer);
		frame.getContentPane().add(gridSizeBtn);
		frame.getContentPane().add(aircraftSize);
		frame.getContentPane().add(battleshipSize);

		frame.getContentPane().add(destroyerSize);
		frame.getContentPane().add(submarineSize);
		frame.getContentPane().add(patrolSize);
		frame.getContentPane().add(aircraftCount);
		frame.getContentPane().add(destroyerCount);
		frame.getContentPane().add(submarineCount);
		frame.getContentPane().add(battleshipCount);
		frame.getContentPane().add(patrolCount);
		
		
		
		/* Title Page color light Gray*/
		frame.getContentPane().setBackground(new Color(229, 232, 232));
		frame.pack();
		frame.setVisible(true);
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
	}

	public boolean isImageVisible(){
		return isImageVisible;
	}
	
}
