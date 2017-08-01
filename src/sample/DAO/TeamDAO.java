package sample.DAO;

import sample.Team;

import java.util.List;

/**
 * Created by Florian-PC on 25.07.2017.
 */
public interface TeamDAO {
    public boolean create(Team team);
    public boolean createFreilos(Team team);
    public boolean update(Team team);
    public boolean delete(Team team);
    //public List<Team> getAllTeams();
}
