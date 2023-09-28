/** Nicholas Altland. CS110C. Final project.
 * 
 * Used to represent the ship type that is being interacted with
 * 
 */
public enum ShipType 
{
    ST_AIRCRAFT_CARRIER{
        @Override
        public String toString(){ return "ST_AIRCRAFT_CARRIER"; }
    },

     ST_BATTLESHIP{
        @Override
        public String toString(){ return "ST_BATTLESHIP"; }
    },

     ST_CRUISER{
        @Override
        public String toString(){ return "ST_CRUISER"; }
    },

    ST_SUB{
        @Override
        public String toString(){ return "ST_SUB"; }
    },

    ST_DESTROYER{
        @Override
        public String toString(){ return "ST_DESTROYER"; }
    }
}