package sample.DAO;

import sample.*;

import java.util.List;

/**
 * Created by Florian-PC on 21.07.2017.
 */
public interface SetzlisteDAO {
    public boolean create(List<Spieler> setzliste, Spielklasse spielklasse);
    public boolean delete(int spielklasseid);
    public boolean update(List<Spieler> setzliste,  Spielklasse spielklasse);
    public List<Spieler> read (int spielklasseID);
    public List<List<Spieler>> getAllSetzlisten();
}
