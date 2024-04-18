package project3;

import java.util.Scanner;

public class Text2048 {
    private GameController game = new GameController();
    private Scanner scanner = new Scanner(System.in);
 
    public void run() {
    	int boardSize = game.getBoard().getSize();
    	
    	game.getBoard().printBoard();
    	while (game.getStatus() == GameStatus.IN_PROGRESS) {
            System.out.println("Move in which direction? (WASD): ");
            String direction = scanner.nextLine();
            switch (direction){
                case "W":
                    game.recurseUp(1, 0);
                    break;
                case "S":
                    game.recurseDown(boardSize - 2, 0);
                    break;
                case "A":
                    game.recurseLeft(0, 1);
                    break;
                case "D":
                    game.recurseRight(0, boardSize - 2);
                    break;
                case "Q":
                    System.out.println("Thank you for playing!\n\n");
                    System.exit(0);
                    break;
                default:
                    System.out.println("That is not a valid command.");
                    continue;
            }
            if (game.getBoard().hasEmpty()) {
                game.newTile();
            }
            game.getBoard().printBoard();
        }
 
        if (game.getStatus() == GameStatus.WON) {
            System.out.println("CONGRATULATIONS!\n\n");
        }
        else {
            System.out.println("Better luck next time.\n\n");
        }
    }
 
    public static void main(String[] args) {
        Text2048 game = new Text2048();
        game.run();
    }
}