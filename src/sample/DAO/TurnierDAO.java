package sample.DAO;

import sample.Turnier;
import sample.Verein;

public interface TurnierDAO {
    public boolean create(Turnier turnier);
    public boolean delete(Turnier turnier);
    public boolean update(Turnier turnier);
    public Turnier read (int turnierID);
}
