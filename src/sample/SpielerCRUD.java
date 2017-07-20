package sample;

/**
 * Created by flori on 30.05.2017.
 */
public interface SpielerCRUD {
    public boolean create(Spieler spieler);
    public boolean delete(Spieler spieler);
    public boolean update(Spieler spieler);
    public Spieler read (int spielerID);
}
