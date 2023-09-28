/**Nicholas Altland. CS110C. Final project.
 * 
 * ComputerBoard Class. Is a Board. Passes the file name to the board constructer, reads and makes moves. ToString of the board state
 */

public class ComputerBoard extends Board
{
    private static final int ASCII_VALUE_A = 'A';    //The int value of A
    private static final int BOARD_LENGTH = 10;     //The length of the board

    /**Computer Board constructor. Takes a String representing a file name and turns it
     * into a board
     * 
     * @param filename string represting a file
     */
    public ComputerBoard(String filename)
    {
        super(filename);
    }

    /** makePlayerMove. Takes a move command. Gets the location and calls applyMoveToLayout
     * 
     * @param movement A move command from the user
     * @return A string if the player's move sunk a ship
     */
    public String makePlayerMove(Move movement)
    {
        try{
            //Integers of col and row. Just to help with clarity well reading
            int numCol = movement.col();
            int numRow = movement.row();

            //Test to see if the cellstatus has beeen changed before.
            //If it has, do not change the layout and return a string informing the user
            //That they wasted a turn
            char locationStatus = getLayout().get(numCol).get(numRow).toString().charAt(0);

            if((locationStatus == 'o')==false){return "You have already guessed that location";}

            //Call the applyMoveToLayout, see if the ship has been
            CellStatus shipHit = applyMoveToLayout(movement);

            //Ship name the second character in the string
            char shipName = shipHit.toString().charAt(0);

            //Set the locaiton to the cellstatus cellState
            getLayout().get(numCol).set((numRow), shipHit);

            //String representing if a ship was sunk
            String shipSunkString = "Nothing sunk"; 

            //Series of if statements to see what ship has been sunk
            if(shipName == 'A'){shipSunkString = "Computer: You sunk my Aircraft Carrier!!";}
            else if(shipName == 'B'){shipSunkString = "Computer: You sunk my Battleship!!";}
            else if(shipName == 'C'){shipSunkString = "Computer: You sunk my Cruiser!!";}
            else if(shipName == 'D'){shipSunkString = "Computer: You sunk my Destroyer!!";}
            else if(shipName == 'S'){shipSunkString = "Computer: You sunk my Sub!!";}

            //Return the string, saying if a ship was sunk, null if it was not
            return shipSunkString;
        }
        catch(StringIndexOutOfBoundsException e)
            {return "Error, string index out of bounds in makeComputerMove";}
        catch(IndexOutOfBoundsException e)
            {return "That location is not avalible";}
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
                                        getLayout().get(i).get(j).toString().charAt(0));
                }
                //Add a newlinecharacter to the end of the string
                s += "\n";
            }
            return s;   //Return the string
        }
        catch(IndexOutOfBoundsException e)
        {
            return "Index out of bounds exception in computer board toString";
        }
    }
}
