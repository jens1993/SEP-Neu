package sample;

import java.util.List;

/**
 * Created by flori on 30.05.2017.
 */
public interface SpielerDAO {
    public boolean create(Spieler spieler);
    public boolean delete(Spieler spieler);
    public boolean update(Spieler spieler);
    public Spieler read (int spielerID);
    public List<Spieler> getAllSpieler();
    public List<Spieler> getSetzliste(Spielklasse spielklasse);
}
