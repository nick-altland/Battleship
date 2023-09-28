/**Nicholas Altland. CS110C. Final project.
 * 
 * Move class. Creates a move object, representing the user/computer movements
 */
public class Move
{
    private int row;    //Int, represets the row 
    private int col;    //Int, represents the collumn
    private static final int ASCII_ALPHA_VALUE = 'A';//Ascii value of A
    private static final int ASCII_NUM_VALUE = '1';//Ascii value of 1

    /**Move constructer. Takes two integers and assigns them to the row and col values
     * 
     */
    public Move(int col, int row)
    {
        this.col = col;
        this.row = row;
    }
    
    /**Secondary move constructor. Takes a string represting a move, and turns the
     * characters into integer values. If the String is size 3, then the 2nd character is 10
     * 
     * @param input String representing a letter and a number. ex: A1 or J10
     */
    public Move(String input)
    {
        try{

            char firstCharacter = input.charAt(0);
            char secondCharacter = input.charAt(1);

            this.col = ((int)firstCharacter - ASCII_ALPHA_VALUE);
            this.row = ((int)secondCharacter - ASCII_NUM_VALUE);
            
            if (input.length() == 3)
            {
                this.row = 9;
            }
        }
        catch(NumberFormatException e)
        {
            System.out.println("error: the second character is not an integer");
        }
    }

    /**getter for the row value
     * 
     * @return the int row value
     */
    public int row()
    {
        return row;
    }

    /**getter for the col value
     * 
     * @return the int col value
     */
    public int col()
    {
        return col;
    }

    /**toString. Returns a string represting the movement made
     * 
     */
    public String toString()
    {
        char collumn = (char)(col + ASCII_ALPHA_VALUE);
        return String.format("%s%d\n", collumn, row);
    }
}
