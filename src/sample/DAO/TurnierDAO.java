package sample.DAO;

import sample.Turnier;
import sample.Verein;

import java.util.Dictionary;
import java.util.List;

public interface TurnierDAO {
    public boolean create(Turnier turnier);
    public boolean delete(Turnier turnier);
    public boolean update(Turnier turnier);
    public Turnier read (int turnierID);
   // public List<Turnier> getAllTurniere();

    public Dictionary<Integer,Turnier> getAllTurniere();
}
