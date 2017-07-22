package sample;

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
                + "SpielklasseID) "
                + "VALUES(?,?,?,?)";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, spiel.getHeim().getSpielerID());
            smt.setInt(2, spiel.getAuswaerts().getSpielerID());
            smt.setInt(3, spiel.getSpielID());
            smt.setInt(4, spiel.getSpielklasse().getSpielklasseID());
            smt.executeUpdate();
            smt.close();
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
                + "SET Heim = ?, "
                + "Gast = ?, "
                + "SpielklasseID = ? "
                + "WHERE SpielID = ? "
        ;
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, spiel.getHeim().getSpielerID());
            smt.setInt(2, spiel.getAuswaerts().getSpielerID());
            smt.setInt(3, spiel.getSpielklasse().getSpielklasseID());
            smt.setInt(4, spiel.getSpielID());
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

    @Override
    public Spiel read(int spielID) {
        return null;
    }

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
