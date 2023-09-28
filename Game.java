/**Nicholas Altland. CS110C. Final project.
 * 
 *  Game class. Constructs the two boards, makes moves for computer and player, 
 *  Tells when game is over
 */

public class Game
{
    private ComputerBoard computer; //Board representing the computers fleet
    private UserBoard user; //Board representing the user's fleet

    /**
     *  Game Constructor. Creates two boards, using input files. Assign the to computer fleet and user fleet
     */
    public Game()
    {
        this.computer = new ComputerBoard("compFleet.txt");
        this.user = new UserBoard("userFleet.txt");
    }

    /** makeComputerMove
     * 
     * @return a random String, representing the move the computer chose and the result
     */
    public String[] makeComputerMove()
    {
        return user.makeComputerMove();
    }

    /**MakePlayerMovement. Accept a String, call the move class to turn it into a movement, then call the computer class to process it
     * 
     * @param movement a string, representing the players movement. 
     * @return A string, representing a phrase if something was sunk
     */
    public String makePlayerMove(String movement)
    {
        try {
            /**Debugging code to see if computer can win */
            //if(movement.equals("END ME")){return "Okay then";}

            //Create a move
            Move thisMove = new Move(movement);

            //If the move is valide, call makePlayerMove. Else, return null
            if(!computer.moveAvailable(thisMove))
            {
                return null;
            }
            return computer.makePlayerMove(thisMove);
        }
        catch(StringIndexOutOfBoundsException e){System.out.println("Entry too short");
                                                return null;}
    }

    /**computerDefeated. Checks the computers fleet to see if there are any remaining ships
     * 
     * @return a boolean value, true or false, if the computer fleet is sunk
     */
    public boolean computerDefeated()
    {
        return computer.gameOver();
    }

    /**userDefeated. Checks the users fleet to see if there are any remaining ships not sunk
     * 
     * @return true if all ships are sunk, false if there are ships not sunk
     */
    public boolean userDefeated()
    {
        return user.gameOver();
    }

    /**toString. Returns a string representation of the user and computer's boards
     * 
     */
    @Override 
    public String toString()
    {
        return String.format("User Board:\n%s\nComputer Board:\n%s",
                             user.toString(), computer.toString()); 
    }
   
}
