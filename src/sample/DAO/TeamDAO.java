package sample.DAO;

import sample.Team;

import java.util.List;

/**
 * Created by Florian-PC on 25.07.2017.
 */
public interface TeamDAO {
    public boolean createTeam(Team team);
    public boolean updateTeam(Team team);
    public boolean deleteTeam(Team team);
    public List<Team> getAllTeams();
}
