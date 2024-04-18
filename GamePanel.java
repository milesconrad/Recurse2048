package project3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	private GameController game;
	private JButton[][] buttonsBoard;
	private int boardSize;
	
	JMenuBar menu;
	JMenu fileMenu;
	JMenuItem resetItem;
	JMenuItem quitItem;
	private JButton upButton;
	private JButton downButton;
	private JButton leftButton;
	private JButton rightButton;
	
	public GamePanel(int boardSize, int winValue) {
		super();
		
		game = new GameController(boardSize, winValue);
		this.boardSize = boardSize;
		
		setLayout(new GridLayout(3, 1));
		
		menu = new JMenuBar();
		fileMenu = new JMenu("File");
		resetItem = new JMenuItem ("Reset");
		quitItem = new JMenuItem ("Quit");
		resetItem.addActionListener(this);
		quitItem.addActionListener(this);
		fileMenu.add(resetItem);
		fileMenu.add(quitItem);
		menu.add(fileMenu);
		
		JPanel boardPanel = new JPanel();
		buttonsBoard = new JButton[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                buttonsBoard[i][j] = new JButton();
                buttonsBoard[i][j].setBackground(Color.WHITE);
                buttonsBoard[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                boardPanel.add(buttonsBoard[i][j]);
            }
        }
        boardPanel.setLayout(new GridLayout(boardSize, boardSize));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));
        upButton = new JButton("UP");
        downButton = new JButton("DOWN");
        leftButton = new JButton("LEFT");
        rightButton = new JButton("RIGHT");
        upButton.addActionListener(this);
        downButton.addActionListener(this);
        leftButton.addActionListener(this);
        rightButton.addActionListener(this);
        upButton.setBackground(Color.LIGHT_GRAY);
        downButton.setBackground(Color.LIGHT_GRAY);
        leftButton.setBackground(Color.LIGHT_GRAY);
        rightButton.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(upButton);
        buttonPanel.add(downButton);
        buttonPanel.add(leftButton);
        buttonPanel.add(rightButton);
        
        this.add(menu);
        this.add(boardPanel);
        this.add(buttonPanel);
        
        addKeyListener(this);
        setFocusable(true);
        displayBoard();
	}
	
	private void displayBoard() {
        Board gameBoard = game.getBoard();
        for (int i = 0; i < buttonsBoard.length; i++) {
        	for (int j = 0; j < buttonsBoard.length; j++) {
        		int tmp = gameBoard.getValue(i, j);
        		if (tmp != -1) {
        			buttonsBoard[i][j].setText("" + tmp);
        		}
        		else {
        			buttonsBoard[i][j].setText("");
        		}
        	}
        }
        this.requestFocus();
    }
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == upButton) {
			game.recurseUp(1, 0);
		}
		else if (e.getSource() == downButton) {
			game.recurseDown(boardSize - 2, 0);
		}
		else if (e.getSource() == leftButton) {
			game.recurseLeft(0, 1);
		}
		else if (e.getSource() == rightButton) {
			game.recurseRight(0, boardSize - 2);
		}
		else if (e.getSource() == resetItem) {
			game.reset();
		}
		else if (e.getSource() == quitItem) {
			System.exit(0);
		}
		
		if (game.getBoard().hasEmpty()) {
			game.newTile();
		}
		displayBoard();
		
		GameStatus status = game.getStatus();
        //If the game is done, then say so and end the game
        if (status != GameStatus.IN_PROGRESS) {
            if (status == GameStatus.WON) {
                JOptionPane.showMessageDialog(null, "You won!\n The game will reset");
            }
            else if (status == GameStatus.LOST) {
                JOptionPane.showMessageDialog(null, "You lost!\n The game will reset");
            }

            game.reset();
            displayBoard();
            return;
        }
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			game.recurseUp(1, 0);
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			game.recurseDown(boardSize - 2, 0);
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			game.recurseLeft(0, 1);
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			game.recurseRight(0, boardSize - 2);
		}
		else {
			return;
		}
		
		if (game.getBoard().hasEmpty()) {
			game.newTile();
		}
		displayBoard();
		
		GameStatus status = game.getStatus();
        //If the game is done, then say so and end the game
        if (status != GameStatus.IN_PROGRESS) {
            if (status == GameStatus.WON) {
                JOptionPane.showMessageDialog(null, "You won!\n The game will reset");
            }
            else if (status == GameStatus.LOST) {
                JOptionPane.showMessageDialog(null, "You lost!\n The game will reset");
            }

            game.reset();
            displayBoard();
            return;
        }
	}
	
	public static void main(String[] args) {
		JFrame gameFrame = new JFrame("Super 2048");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int boardSize = 0;
		while (boardSize < 4 || boardSize > 10) {
            String input = JOptionPane.showInputDialog(null, "Enter in the size of the board (4 - 10): ");
            //If board size is set to null, exit program
            if (input == null) {
                System.exit(1);
            }

            try {
                //Set the board size... If it doesn't work then throw IAE
                boardSize = Integer.parseInt(input);
                if (boardSize < 4 || boardSize > 10) {
                    throw new IllegalArgumentException();
                }
            }
            //Let the user know that they need to retry
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Sorry, the value you entered was either not a number or did not meet the requirements. Please try again.");
            }
        }
		
		int winValue = 0;
		while (!Tile.power2(winValue)) {
            String input = JOptionPane.showInputDialog(null, "Enter in the value needed to win (power of 2): ");
            //If board size is set to null, exit program
            if (input == null) {
                System.exit(1);
            }

            try {
                //Set the board size... If it doesn't work then throw IAE
            	winValue = Integer.parseInt(input);
                if (!Tile.power2(winValue)) {
                    throw new IllegalArgumentException();
                }
            }
            //Let the user know that they need to retry
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Sorry, the value you entered was either not a number or did not meet the requirements. Please try again.");
            }
        }
		
		gameFrame.getContentPane().add(new GamePanel(boardSize, winValue));
		gameFrame.setSize(800, 800);
        gameFrame.setVisible(true);
	}
}