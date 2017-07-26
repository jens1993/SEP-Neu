package sample.DAO;

import sample.Turnier;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class TurnierDAOimpl implements TurnierDAO {

    @Override
    public boolean create(Turnier turnier) {

        String sql = "INSERT INTO Turnier("
                + "Datum, "
                + "Name, "
                + "Turnierid)"
                + "VALUES(?,?,?)";

        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setObject(1, turnier.getDatum());
            smt.setString(2, turnier.getName());
            smt.setInt(3, turnier.getTurnierid());
            smt.executeUpdate();
            smt.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Turnier Einfügen Klappt nicht");
        }

        return false;
    }

    @Override
    public boolean delete(Turnier turnier) {
        String sql = "Delete From turnier Where turnierID= ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, turnier.getTurnierid());
            smt.executeUpdate();
            smt.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Turnier Loeschen Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean update(Turnier turnier) {

        String sql = "UPDATE Turnier " +
                "SET MatchDauer = ?, " +
                "Datum = ?, " +
                "Name= ?, " +
                "SpielerPausenZeit = ?, " +
                "Zaehlweise = ? " +
                "WHERE Turnierid = ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, turnier.getMatchDauer());
            smt.setObject(2, turnier.getDatum());
            smt.setString(3, turnier.getName());
            smt.setInt(4, turnier.getSpielerPausenZeit());
            smt.setInt(5, turnier.getZaehlweise());
            smt.setInt(6, turnier.getTurnierid());
            smt.executeUpdate();
            smt.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Turnier Update Klappt nicht");
        }
        return false;
    }

    @Override
    public Turnier read(int turnierID) {
        return null;
    }
}
