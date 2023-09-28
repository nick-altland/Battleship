import java.io.*;
import java.util.Scanner;
import java.util.Random;

/**Nicholas Altland. CS110C. Final project.
 * 
 * Battleship driver. Creates a game, and while either side has not lost, continously
 * Prompts the user and computer to make guesses. Ends when one side has lost, informing
 * the user who won
 */
public class BattleShipDriver
{
    public static void main(String [] args) throws IOException
    {
        Game game = new Game();     //Create a game
        Random rand = new Random();     //Create a random instance
        int coinFlip = rand.nextInt(2);     //Create a random number from 0-1
              
        String playerMove;      //String representing a players movement
        String[] computerMove;      //String list of the computers move

        Scanner keyboard = new Scanner(System.in);  //Create new scanner for keyboard

        System.out.println("Welcome to Battleship!"); //Inform user that game has started

        //If the random number is 0, the computer goes first
        if(coinFlip == 0)
        {
            //Ask user to aknowledge this and then get a new move from the computer
            System.out.println("The computer won the coin toss and gets to go first");
            System.out.println("Computer's turn. Press any key to continue.");
            keyboard.nextLine();
            computerMove = game.makeComputerMove();
            System.out.println(computerMove[0]);

        }
        //User goes first if not
        else
        {
            //Ask user to aknowledge this
            System.out.println("You won the coin toss and get to go first!");
            System.out.println("Your turn. Press any key to continue.");
            keyboard.nextLine();
        }

        //Print the first instance of the game board
        System.out.print(game.toString());

        //While the game has not ended
        do{
            //tell the user it is their turn, and create a new move from their entry
            System.out.print("Your turn: ");
            playerMove = game.makePlayerMove(keyboard.nextLine().toUpperCase());
            
            /**Debugging code, to see if computer can win */
            // if(playerMove.equals("Okay then"))
            // {
            //     while(game.userDefeated() == false)
            //     {
            //         computerMove = game.makeComputerMove();
            //         System.out.println(computerMove[0]);
            //     }
            // }

            //While the playermove is not valid
            while(playerMove == null)
            {
                //Inform them and ask them to try again
                System.out.print("Location not available, try again: ");
                playerMove = game.makePlayerMove(keyboard.nextLine().toUpperCase());
            } 
            //If something has been sunk, inform the player        
            if(!(playerMove.equals("Nothing sunk"))){ System.out.println(playerMove);}

            /** there is supposed to be another game board printed, but it looks super cluttered so I removed it.
             *  Simpley uncomment the next line to add it back */
            //System.out.print(game.toString());

            //Create a new computer move, inform the user what that move it
            computerMove = game.makeComputerMove();
            System.out.println(computerMove[0]);
            
            //If the computer has sunk one of their ships, inform the user
            if(!computerMove[1].equals("Nothing sunk")){System.out.println(computerMove[1]);}
            
            //Print out an instance of the game board after both user and computer go
            System.out.print(game.toString());

        } while(game.userDefeated() == false && game.computerDefeated() == false);

        //If the user has been defeated, taunt them with their mediocraty
        if(game.userDefeated()) System.out.println("You have lost, the computer has won!!!");
        //Else, they won. Nice job user!
        else System.out.println("Congrats!!! You have won!!!");

        //Close the keyboard
        keyboard.close();
    }    
}
