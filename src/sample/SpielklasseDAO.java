package sample;

import java.util.List;

/**
 * Created by Florian-PC on 21.07.2017.
 */
public interface SpielklasseDAO {
    public boolean create(Spielklasse spielklasse);
    public boolean delete(Spielklasse spielklasse);
    public boolean update(Spielklasse spielklasse);
    public Spielklasse read (int spielklasseID);
    public List<Spielklasse> getAllSpielklassen();
}
