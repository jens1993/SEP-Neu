package sample.DAO;

import sample.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

import java.sql.*;

/**
 * Created by Florian-PC on 21.07.2017.
 */
public class SpielDAOimpl implements SpielDAO {
    @Override
    public boolean create(Spiel spiel) {
        String sql = "INSERT INTO spiel("
                + "Heim,"
                + "Gast, "
                + "SpielID, "
                + "Schiedsrichter, "
                + "SpielklasseID) "
                + "VALUES(?,?,?,?,?)";

        String sqlzwei = "INSERT INTO spielklasse_spielid("
                + "SpielID,"
                + "SpielklasseID, "
                + "SpielklasseSpielID) "
                + "VALUES(?,?,?)";


        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            if(spiel.getHeim()!=null&&spiel.getGast()!=null) {
                smt.setInt(1, spiel.getHeim().getTeamid());
                smt.setInt(2, spiel.getGast().getTeamid());
            }
            else{
                smt.setNull(1, Types.INTEGER);
                smt.setNull(2, Types.INTEGER);
            }
            smt.setInt(3, spiel.getSpielID());
            if (spiel.getSchiedsrichter()!=null){
                smt.setInt(4, spiel.getSchiedsrichter().getSpielerID());
            }
            else{
                smt.setNull(4,Types.INTEGER);
            }
            smt.setInt(5, spiel.getSpielklasse().getSpielklasseID());
            smt.executeUpdate();
            smt.close();
            PreparedStatement smtzwei = con.SQLConnection().prepareStatement(sqlzwei);
            smtzwei.setInt(1, spiel.getSpielID());
            smtzwei.setInt(2, spiel.getSpielklasse().getSpielklasseID());
            smtzwei.setInt(3, spiel.getSystemSpielID());
            smtzwei.executeUpdate();
            smtzwei.close();
            con.closeCon();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spiel Einfügen Klappt nicht");
        }


        return false;
    }

    @Override
    public boolean delete(Spiel spiel) {
        String sql = "Delete From spiel Where SpielID= ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, spiel.getSpielID());
            smt.executeUpdate();
            smt.close();
            con.closeCon();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spiel Loeschen Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean update(Spiel spiel) {
        String sql = "UPDATE spiel "
                + "SET AufrufZeit = ?, "
                + "Schiedsrichter = ?, "
                + "Feld = ?, "
                + "Status = ?, "
                + "Heim = ?, "
                + "Gast = ?, "
                + "SiegerID = ?, "
                + "SpielklasseID = ? "
                + "WHERE SpielID = ? "
        ;
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setObject(1, spiel.getAufrufZeit());
            if (spiel.getSchiedsrichter()!=null){
                smt.setInt(2, spiel.getSchiedsrichter().getSpielerID());
            }
            else{
                smt.setNull(2,Types.INTEGER);
            }
            if (spiel.getFeld()!=null){
                smt.setInt(3, spiel.getFeld().getFeldID());
            }
            else{
                smt.setNull(3,Types.INTEGER);
            }
            smt.setInt(4, spiel.getStatus());
            if (spiel.getHeim()!=null){
                smt.setInt(5, spiel.getHeim().getTeamid());
            }
            else{
                smt.setNull(5,Types.INTEGER);
            }
            if (spiel.getGast()!=null){
                smt.setInt(6, spiel.getGast().getTeamid());
            }
            else{
                smt.setNull(6,Types.INTEGER);
            }
            if (spiel.getSieger()!=null){
                smt.setInt(7, spiel.getSieger().getTeamid());
            }
            else{
                smt.setNull(7,Types.INTEGER);
            }
            smt.setInt(8, spiel.getSpielklasse().getSpielklasseID());
            smt.setInt(9, spiel.getSpielID());
            smt.executeUpdate();
            smt.close();
            con.closeCon();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spiel Update Klappt nicht");
        }


        return false;
    }
}
