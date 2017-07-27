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
            smt.setInt(1, spiel.getHeim().getTeamid());
            smt.setInt(2, spiel.getGast().getTeamid());
            smt.setInt(3, spiel.getSpielID());
            smt.setInt(4, spiel.getSchiedsrichter().getSpielerID());
            smt.setInt(5, spiel.getSpielklasse().getSpielklasseID());
            smt.executeUpdate();
            smt.close();
            PreparedStatement smtzwei = con.SQLConnection().prepareStatement(sqlzwei);
            smtzwei.setInt(1, spiel.getSpielID());
            smtzwei.setInt(2, spiel.getSpielklasse().getSpielklasseID());
            smtzwei.setInt(3, spiel.getSystemSpielID());
            smtzwei.executeUpdate();
            smtzwei.close();
            System.out.println("Spiel Einfügen klappt");
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
            System.out.println("Spiel Loeschen klappt");
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
            smt.setInt(2, spiel.getSchiedsrichter().getSpielerID());
            smt.setInt(3, spiel.getFeld().getFeldID());
            smt.setInt(4, spiel.getStatus());
            smt.setInt(5, spiel.getHeim().getTeamid());
            smt.setInt(6, spiel.getGast().getTeamid());
            smt.setInt(7, spiel.getSieger().getTeamid());
            smt.setInt(8, spiel.getSpielklasse().getSpielklasseID());
            smt.setInt(9, spiel.getSpielID());
            smt.executeUpdate();
            smt.close();
            System.out.println("Spiel Update klappt");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spiel Update Klappt nicht");
        }


        return false;
    }

    //@Override
    //public Spiel read(int spielID) {
    //    return null;
    //}

    /*@Override
    public Spiel read(int spielID) {
        String sql = "Select * from spiel Where SpielID="+spielID;
        Spiel temp = null;
        try {
            SQLConnection con = new SQLConnection();
            Connection connection = con.SQLConnection();
            Statement st = connection.createStatement();
            ResultSet vereinResult = st.executeQuery(sql);
            vereinResult.next();
            temp = new Spiel( spielID, vereinResult.getString(2), vereinResult.getString(3), vereinResult.getString(4));
            System.out.println(vereinResult.getString(2));
            System.out.println("Verein Lesen klappt");


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Verein Lesen Klappt nicht");
        }
        return temp;
    }*/
}
