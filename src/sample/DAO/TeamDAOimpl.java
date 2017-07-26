package sample.DAO;

import sample.Team;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Florian-PC on 25.07.2017.
 */
public class TeamDAOimpl implements TeamDAO {
    @Override
    public boolean createTeam(Team team) {
        String sql = "INSERT INTO team("
                + "TeamID, "
                + "SpielklasseID) "
                + "VALUES(?,?)";
        String sqlSpielerEins = "INSERT INTO Team_Spieler("
                + "TeamID, "
                + "SpielerID) "
                + "VALUES(?,?)"
                ;
        String sqlSpielerZwei = "INSERT INTO Team_Spieler("
                + "TeamID, "
                + "SpielerID) "
                + "VALUES(?,?)"
                ;
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, team.getTeamid() );
            smt.setInt(2, team.getSpielklasse().getSpielklasseID());
            smt.executeUpdate();
            smt.close();
            PreparedStatement smtSpielerEins = con.SQLConnection().prepareStatement(sqlSpielerEins);
            smtSpielerEins.setInt(1, team.getTeamid() );
            smtSpielerEins.setInt(2, team.getSpielerEins().getSpielerID());
            smtSpielerEins.executeUpdate();
            smtSpielerEins.close();
            if(team.getSpielerZwei()!=null) {
                PreparedStatement smtSpielerZwei = con.SQLConnection().prepareStatement(sqlSpielerZwei);
                smtSpielerZwei.setInt(1, team.getTeamid());
                smtSpielerZwei.setInt(2, team.getSpielerZwei().getSpielerID());
                smtSpielerZwei.executeUpdate();
                smtSpielerZwei.close();
            }
            System.out.println("Team Einfügen klappt");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Team Einfügen Klappt nicht");
        }

        return false;
    }

    @Override
    public boolean updateTeam(Team team) {
        String sql = "UPDATE team "
                + "SET SpielklasseID = ?, "
                + "GewonneneSpiele = ?, "
                + "GewonneneSaetze = ?, "
                + "VerloreneSaetze = ?, "
                + "GewonnnenePunkte = ?, "
                + "VerlorenePunkte = ? "
                + "WHERE TeamID = ? "
                ;
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, team.getSpielklasse().getSpielklasseID());
            smt.setInt(2, team.getGewonneneSpiele());
            smt.setInt(3, team.getGewonneneSaetze());
            smt.setInt(4, team.getVerloreneSaetze());
            smt.setInt(5, team.getGewonnnenePunkte());
            smt.setInt(6, team.getVerlorenePunkte());
            smt.setInt(7, team.getTeamid());
            smt.executeUpdate();
            smt.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Team Update Klappt nicht");
        }

        return false;
    }

    @Override
    public boolean deleteTeam(Team team) {
        String sql = "Delete From team Where teamid= ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, team.getTeamid());
            smt.executeUpdate();
            smt.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Team Loeschen Klappt nicht");
        }
        return false;
    }

   /* @Override
    public List<Team> getAllTeams() {
        return null;
    }*/
}
