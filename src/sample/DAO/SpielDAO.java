package sample.DAO;

import sample.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

/**
 * Created by Florian-PC on 21.07.2017.
 */
public interface SpielDAO {
    public boolean create(Spiel spiel);
    public boolean delete(Spiel spiel);
    public boolean update(Spiel spiel);
    //public Spiel read (int spielID);
}
