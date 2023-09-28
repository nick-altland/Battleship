/**Nicholas Altland. CS110C. Final project.
 * 
 * Fleet Class. Initalize a series of ships and adds them to the Fleet
 */

public class Fleet 
{
    //Variables for all the ships in the fleet. 
    private Ship battleship;    //Size 4
    private Ship aircraftCarrier;   //Size 5
    private Ship cruiser;   //Size 3
    private Ship sub;   //Size 3
    private Ship destroyer; //Size 2

    /**
     * Fleet. Initalize all ship variables, creating the approprite ship object with the specified size
     */
    public Fleet()
    {
        this.battleship = new Battleship();
        this.aircraftCarrier = new AircraftCarrier();
        this.cruiser = new Cruiser();
        this.sub = new Sub();
        this.destroyer = new Destroyer();
    }

    /** updateFleet. Takes a shipType, looks for the matching ship, and, and adds a hit to the hit counter. Returns true if the ship was sunk, false if the ship was not
     * 
     * @param ship the ship in question
     * @return whether or not the ship was sunk
     */
    public boolean updateFleet(ShipType ship)
    {
        if(ship.equals(ShipType.ST_AIRCRAFT_CARRIER))
        {
            return aircraftCarrier.hit();
        }

        else if(ship.equals(ShipType.ST_BATTLESHIP))
        {
            return battleship.hit();
        }

        else if(ship.equals(ShipType.ST_CRUISER))
        {
            return cruiser.hit();
        }

        else if(ship.equals(ShipType.ST_SUB))
        {
            return sub.hit();
        }

        else if(ship.equals(ShipType.ST_DESTROYER))
        {
            return destroyer.hit();
        }
        
        else
        {
            System.out.println(ship + " shiptype could not be found");
            return false;
        }
    }

    /**gameOver. If all the ships are sunk (true), return true that the games is over. Else, return false
     * 
     * @return whether or not the game is over
     */
    public boolean gameOver()
    {
        if(this.battleship.getSunk() == true && 
           this.aircraftCarrier.getSunk() == true &&
           this.cruiser.getSunk() == true &&
           this.sub.getSunk() == true &&
           this.destroyer.getSunk() == true)
           return true;        
        return false;
    }
}