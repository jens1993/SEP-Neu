package sample.DAO;

import sample.Spielklasse;

import java.util.Dictionary;
import java.util.List;

/**
 * Created by jens on 05.08.2017.
 */
public interface SpielklasseDAO {
    boolean create(Spielklasse spielklasse);

    boolean delete(Spielklasse spielklasse);

    boolean update(Spielklasse spielklasse);

    List<Spielklasse> getAllSpielklassen();
    public Dictionary<Integer, Spielklasse> getAllSpielklassenDict();
}
