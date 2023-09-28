import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**Nicholas Altland. CS110C. Final project.
 * 
 * Board. The playing board for the game of Battleship. Includes an arraylist of the layout, creates fleets for the computer and user
 */

public class Board 
{
    private static final int SIZE = 10; //Size of playing board
    private static final int ASCII_ALPHA_VALUE = 'A';   //Value of A
    private static final int ASCII_NUM_VALUE = '0';     //Value of 0
    private int shipsInGame = 5;       //How many ships are in the game

    //Series of ints, for use in locating where a ship is stored in arraylist shipsHIt
    private int aircraftLocation = 0;
    private int battleshipLocation = 1;
    private int cruiserLocation = 2;
    private int destroyerLocaiton = 3;
    private int subLocaiton = 4;

    //Arraylist of an Arraylist that keeps track of what ships have been hit and their
    //Location on the board
    private ArrayList<ArrayList<Integer>> shipsHit;
    private ArrayList<Integer> shipLocations;

    //The fleet, for both the user and the computer
    private Fleet fleet;

    //Arraylist, keeping track of where the ships are for that fleet
    private ArrayList<ArrayList<CellStatus>> layout;
    private ArrayList<CellStatus> row;

    /**
     * Board constructer. Take in a string, represting a file name. Create a board from the fleet
     * initalize the arraylists
     */
    public Board(String fleetStatus)
    {
        //Create a new arraylist, layout, for the ten rows
        layout = new ArrayList<ArrayList<CellStatus>>(SIZE);
        //Loop ten times
        for(int i=0; i < SIZE; i++)
        {
            //Each loop, create a new array list of length ten
            row = new ArrayList<CellStatus>(SIZE);
            //Loop ten times
            for(int j = 0; j < SIZE; j++)
            {
                //Add the cellstatus of nothing to the arraylist row
                row.add(CellStatus.NOTHING);
            }
            //Add the row arraylist to the layout
            layout.add(row);
        }

        //Arraylist to keep track of all the locations of the ships that have been hit
        shipsHit = new ArrayList<ArrayList<Integer>>(shipsInGame);
        //Loop 5 times
        for(int i=0; i < shipsInGame; i++)
        {
            //Each loop, create a new array list of integers
            shipLocations = new ArrayList<Integer>();
            //Add the shipLocations arraylist to the shipsHit
            shipsHit.add(shipLocations);
        }

        //Try, while the file exists
        try{
            //Create new scanner, reading in a fleet position
            Scanner infile = new Scanner(new File(fleetStatus));
            while(infile.hasNext())
            {
                //Variable to track the ship alignment
                boolean horizontal = true;
                //Variable to track the length of the ship
                int shipLength = 0;
                //String form of CELLSTATUS names
                CellStatus cellShipName;

                //Takes the line from the file
                String line = infile.nextLine();
                //The ship name is signified by the first letter of each row
                char shipName = line.charAt(0);
                //The starting point and ending point are signfied by the next two sets of character
                //The character is what column they are at, start/end
                int shipColStart = line.charAt(2) - ASCII_ALPHA_VALUE;
                int shipColEnd = line.charAt(5) - ASCII_ALPHA_VALUE;
            
                //The int value is what row they start at
                int shipRowStart = line.charAt(3) - ASCII_NUM_VALUE;

                //If the ship start do not make the ship end, then they are not horizontal
                if (shipColStart != shipColEnd)
                {
                    horizontal = false;
                }

                //Series of if statements to see what the the cellstatus should be
                if(shipName == 'A'){cellShipName = CellStatus.AIRCRAFT_CARRIER;
                                    shipLength = 5;}
                else if(shipName == 'B'){cellShipName = CellStatus.BATTLESHIP;
                                         shipLength = 4;}
                else if(shipName == 'C'){cellShipName = CellStatus.CRUISER;
                                         shipLength = 3;}
                else if(shipName == 'D'){cellShipName = CellStatus.DESTROYER;
                                         shipLength = 2;}
                else if(shipName == 'S'){cellShipName = CellStatus.SUB;
                                         shipLength = 3;}
                else{cellShipName = CellStatus.NOTHING;
                     shipLength = 0;}
                
                //If the ship is horizontal, set each value to the ship cell name, starting at the numerical ship actual location
                //Goes through and sets each of the next values of layout to the name of the ship, for the length of the ship
                if(horizontal)
                {
                    for(int i=0; i < shipLength; i++)
                    {
                        layout.get(shipColStart).set(shipRowStart+i-1, cellShipName);
                    }

                }
                //If the ship is vertical, set each value to the ship cell name, starting at the numerical ship actual location
                //Set that to the ship name
                else if(!horizontal)
                {
                    for(int i=0; i < shipLength; i++)
                    {
                        layout.get(shipColStart+i).set(shipRowStart-1, cellShipName);
                    }
                }
            }
            this.fleet = new Fleet();  //Initalize the fleet
        }
        //If the file is not found, throw an error and exit the program
        catch(FileNotFoundException e)
        {
            System.out.println("Error, file not found for Board constructor");
            System.exit(0);
        }
        //If the arraylist is out of bounds, let user know
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Array outta bound exception in Board constructor");
        }
        //If the character can not be converted to an int, let user know
        catch(NumberFormatException e)
        {
            System.out.println("Could not convert the character to a number in Board constructor");
        }
    }

    /**applyMoveToLayout. Receive a move object. Determine if the cooridinates recevied, which equate to a location in the array.
     * See if there is a ship in that location, and if so, what ship. 
     * If there is, the ship is hit. If not, the cell is set as nothing hit
     * 
     * @param movement a move, used to get the column and row 
     * @return an update to the layout array.
     */
    public CellStatus applyMoveToLayout(Move movement)
    {
        try{
            //Get the location of the ship by getting the col and row of the move
            int colLocation = movement.col();
            int rowLocation = movement.row();

            //Ship name the second character in the string
            char shipName = layout.get(colLocation).get(rowLocation).toString().charAt(1);

            //Initialze the ship hit variable
            CellStatus cellState;

            //Series of if statements to find the ship name
            //Set the location to that name HIT if it is true
            if(shipName == 'A')
            {
                cellState = CellStatus.AIRCRAFT_CARRIER_HIT;
                //Add the location of the hit to array ships hit
                shipsHit.get(aircraftLocation).add(colLocation);
                shipsHit.get(aircraftLocation).add(rowLocation);

                //If that ship has been sunk, set all the previous locations to sunk
                if(getFleet().updateFleet(ShipType.ST_AIRCRAFT_CARRIER) == true)
                {
                    cellState = CellStatus.AIRCRAFT_CARRIER_SUNK;

                    for(int i=0; i< shipsHit.get(aircraftLocation).size(); i+=2)
                    {
                        int thiscol = shipsHit.get(aircraftLocation).get(i);
                        int thisrow = shipsHit.get(aircraftLocation).get(i+1);

                        layout.get(thiscol).set(thisrow, cellState);
                    }
                }
            }
            else if(shipName == 'B')
            {
                cellState = CellStatus.BATTLESHIP_HIT;
                 //Add the location of the hit to array ships hit
                shipsHit.get(battleshipLocation).add(colLocation);
                shipsHit.get(battleshipLocation).add(rowLocation);
                
                //If that ship has been sunk, set all the previous locations to sunk                
                if(getFleet().updateFleet(ShipType.ST_BATTLESHIP) == true)
                {
                    cellState = CellStatus.BATTLESHIP_SUNK;

                    for(int i=0; i< shipsHit.get(battleshipLocation).size(); i+=2)
                    {
                        int thiscol = shipsHit.get(battleshipLocation).get(i);
                        int thisrow = shipsHit.get(battleshipLocation).get(i+1);

                        layout.get(thiscol).set(thisrow, cellState);
                    }
                }
            }
            else if(shipName == 'C')
            {
                cellState = CellStatus.CRUISER_HIT;

                //Add the location of the hit to array ships hit
                shipsHit.get(cruiserLocation).add(colLocation);
                shipsHit.get(cruiserLocation).add(rowLocation);
                
                //If that ship has been sunk, set all the previous locations to sunk
                if(getFleet().updateFleet(ShipType.ST_CRUISER) == true)
                {
                    cellState = CellStatus.CRUISER_SUNK;

                    for(int i=0; i< shipsHit.get(cruiserLocation).size(); i+=2)
                    {
                        int thiscol = shipsHit.get(cruiserLocation).get(i);
                        int thisrow = shipsHit.get(cruiserLocation).get(i+1);

                        layout.get(thiscol).set(thisrow, cellState);
                    }
                }
            }
            else if(shipName == 'D')
            {
                cellState = CellStatus.DESTROYER_HIT;

                //Add the location of the hit to array ships hit
                shipsHit.get(destroyerLocaiton).add(colLocation);
                shipsHit.get(destroyerLocaiton).add(rowLocation);

                //If that ship has been sunk, set all the previous locations to sunk
                if(getFleet().updateFleet(ShipType.ST_DESTROYER) == true)
                {
                    cellState = CellStatus.DESTROYER_SUNK;
                    for(int i=0; i < shipsHit.get(destroyerLocaiton).size(); i+=2)
                    {
                        int thiscol = shipsHit.get(destroyerLocaiton).get(i);
                        int thisrow = shipsHit.get(destroyerLocaiton).get(i+1);

                        layout.get(thiscol).set(thisrow, cellState);    
                    }
                }
            }
            else if(shipName == 'S')
            {
                cellState = CellStatus.SUB_HIT;

                //Add the location of the hit to array ships hit
                shipsHit.get(subLocaiton).add(colLocation);
                shipsHit.get(subLocaiton).add(rowLocation);
                
                //If that ship has been sunk, set all the previous locations to sunk
                if(getFleet().updateFleet(ShipType.ST_SUB) == true)
                {
                    cellState = CellStatus.SUB_SUNK;
                    int j = 0;

                    for(int i=0; i < shipsHit.get(subLocaiton).size(); i++)
                    {
                        int thiscol = shipsHit.get(subLocaiton).get(j);
                        int thisrow = shipsHit.get(subLocaiton).get(j+1);

                        layout.get(thiscol).set(thisrow, cellState);
                        j+=2;
                    }
                }                
            }
            else cellState = CellStatus.NOTHING_HIT;

            //Return the status of the cell of the original move
            return cellState;

        }
        catch(StringIndexOutOfBoundsException e)
            {System.out.println("Error, string index out of bounds in applyMoveToLayout");
            return CellStatus.NOTHING_HIT;}
        catch(IndexOutOfBoundsException e){
            return CellStatus.NOTHING_HIT;}
    }

    /** moveAvalible. Takes a movement, gets the col and row, and sees if it is in the layout. 
     * 
     * @param movement  a movement, consisting of a col and a row
     * @return  whether that move is possible, given the parameters of the layout
     */
    public boolean moveAvailable(Move movement)
    {
        if(movement.col() < 0 || movement.col() > SIZE)
        {
            return false;
        }
        else if(movement.row() < 0 || movement.row() > SIZE)
        {
            return false;
        }

        return true;
    }

    /**gameOver. Calls the fleet's game over to check if all the ships are sunk. Returns true if they are, false if they are not
     * 
     * @return
     */
    public boolean gameOver()
    {
        return fleet.gameOver();
    }

    /**getLayout. Returns a reference to the layout.
     * 
     * @return the layout
     */
    public ArrayList<ArrayList<CellStatus>> getLayout()
    {
        return this.layout;
    }

    /**getFleet. Returns the fleet
     * 
     * @return the fleet
     */
    public Fleet getFleet()
    {
        return this.fleet;
    }

}