package sample.DAO;

import sample.Turnier;

import java.util.Dictionary;

public interface TurnierDAO {
    public boolean create(Turnier turnier);
    public boolean delete(Turnier turnier);
    public boolean update(Turnier turnier);
    public Turnier read (Turnier turnierEingabe);
   // public List<Turnier> getAllTurniere();

    public Dictionary<Integer,Turnier> getAllTurniere();
}
