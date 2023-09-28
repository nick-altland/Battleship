import java.util.ArrayList;
import java.util.Random;

/**Nicholas Altland. CS110C. Final project.
 * 
 * UserBoard. Creates an instance of a board for the user. Applies moves by the computer to the board
 * tostring to print out the instance, formatted properly. Is a Board. 
 */

public class UserBoard extends Board
{
    private static ArrayList<Move> move; // list of all possible moves
    private static final int BOARD_LENGTH = 10; //length of board
    private Random rand;     //Random number generater
    private static final int ASCII_VALUE_A = 'A'; //Value of A

    /**UserBoard constructer. Takes a file name and creates a user board from it. Initialize the move arraylist
     * Loops through and fills it with all possible moves
     * 
     * @param fileName the file name containing the user board
     */
    public UserBoard(String fileName)
    {
        //creates a board using the super constructor
        super(fileName);

        //initialize the array
        move = new ArrayList<Move>(BOARD_LENGTH*BOARD_LENGTH);
        //initialize the random object
        rand = new Random();
       
        //for each value, up to ten
        for(int i = 0; i < BOARD_LENGTH; i++)
        {
            //for each value, up to ten
            for(int j = 0; j < BOARD_LENGTH; j++)
            {
                //create a new move and add it to the array
                Move movement = new Move(i, j);
                move.add(movement);
            }
        }
    }

    /**makeComputerMove. Picks a random move from the move array initilazed in the class
     * constructor. Calls applyMoveToLayout, then looks to see if there is a ship there
     * 
     * @return  Two strings, one representing the computer's move, the second if there was any 
     * result from that move
     */
    public String[] makeComputerMove()
    {
        try{
            //Select a random from move array, remove it and create a move object
            int moveSelected = rand.nextInt(0, move.size());
            Move computerMove = move.remove(moveSelected);

            //Change the col into a letter character
            char compMove = (char)(computerMove.col() + ASCII_VALUE_A);

            //Create the first string, which tells the user what location the computer chose
            String compMoveString = "Computer chose: " + compMove  + (computerMove.row()+1);
            
            //Call the applyMoveToLayout, see if a ship has been hit/sunk
            CellStatus shipHit = applyMoveToLayout(computerMove);

            //Ship name the first character in the string
            char shipName = shipHit.toString().charAt(0);

            //Set the locaiton to the cellstatus cellState
            getLayout().get(computerMove.col()).set(computerMove.row(), shipHit);

            //String represting if a ship was sunk. 
            String shipSunk = "Nothing sunk";

            //Series of if statements to see what ship has been sunk
            if(shipName == 'A'){shipSunk = "The computer has sunk your Aircraft Carrier!!";}
            else if(shipName == 'B'){shipSunk = "The computer has sunk your Battleship!!";}
            else if(shipName == 'C'){shipSunk = "The computer has sunk your Cruiser!!";}
            else if(shipName == 'D'){shipSunk = "The computer has sunk your Destroyer!!";}
            else if(shipName == 'S'){shipSunk = "The computer has sunk your Sub!!";}

            //Create the string array, of the computer move and if the ship was sunk
            String[] movement = {compMoveString, shipSunk};

            //Return the string array
            return movement;
        }
        catch(StringIndexOutOfBoundsException e)
        {
            String[] temp1 = {"String out of bounds in makeComputerMove", null}; 
            return temp1;
        }
    }

    /** toString method. Sets up the computer board layout that is printed to the screen
     * 
     * return a string of the board
     */
    @Override
    public String toString()
    {
        //Set up the string, which is the layout for the top row
        String s = String.format("%6s%5s%5s%5s%5s%5s%5s%5s%5s%5s\n",
                                 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        //Asscii value for A, which is added to each time 
        int colLetter = ASCII_VALUE_A;

        try{
            //Loops through every ten times, up to the size of layout
            for(int i = 0; i < BOARD_LENGTH; i++)
            {
                //The string, add the character representation of the ascii value, with a tab. Add one for next loop
                s += (char)(colLetter);

                colLetter+= 1;
                //Loop ten times, getting each layout
                for(int j = 0; j < BOARD_LENGTH; j++)
                {
                    //adds to the string the first character of the string from CellStatus. Add one to count
                    s += String.format("%5s", 
                                        getLayout().get(i).get(j).toString().charAt(1));
                }
                //Add a newlinecharacter to the end of the string
                s += "\n";
            }
            return s;   //Return the string
        }
        catch(IndexOutOfBoundsException e)
        {
            return "Index out of bounds exception in user board toString";
        }
    }
}
