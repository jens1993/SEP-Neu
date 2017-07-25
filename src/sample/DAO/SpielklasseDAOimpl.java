package sample.DAO;

import sample.*;
import sample.Spielsysteme.*;
import sample.Enums.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florian-PC on 22.07.2017.
 */
public class SpielklasseDAOimpl implements SpielklasseDAO {
    @Override
    public boolean create(Spielklasse spielklasse) {
        String sql = "INSERT INTO spielklasse("
                + "Disziplin,"
                + "Niveau, "
                + "turnierid, "
                + "SpielklasseID) "
                + "VALUES(?,?,?,?)";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setString(1, spielklasse.getDisziplin());
            smt.setString(2, spielklasse.getNiveau());
            smt.setInt(3, spielklasse.getTurnierid());
            smt.setInt(4, spielklasse.getSpielklasseID());
            smt.executeUpdate();
            smt.close();
            System.out.println("Spielklasse Einfügen klappt");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spielklasse Einfügen Klappt nicht");
        }

        return false;
    }

    @Override
    public boolean delete(Spielklasse spielklasse) {
        String sql = "Delete From spielklasse Where SpielklasseID= ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, spielklasse.getSpielklasseID());
            smt.executeUpdate();
            smt.close();
            System.out.println("Spielklasse Loeschen klappt");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spielklasse Loeschen Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean update(Spielklasse spielklasse) {
        String sql = "UPDATE spielklasse "
                + "SET Disziplin = ?, "
                + "Niveau = ? "
                + "WHERE SpielklasseID = ? ";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setString(1, spielklasse.getDisziplin());
            smt.setString(2, spielklasse.getNiveau());
            smt.setInt(3, spielklasse.getSpielklasseID());
            smt.executeUpdate();
            smt.close();
            System.out.println("Spielklasse Update klappt");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spielklasse Update Klappt nicht");
        }
        return false;
    }

    @Override
    public Spielklasse read(int spielklasseID) {
        String sql = "Select * from spielklasse Where spielklasseID=" + spielklasseID;
        Spielklasse temp = null;
        try {
            SQLConnection con = new SQLConnection();
            Connection connection = con.SQLConnection();
            Statement st = connection.createStatement();
            ResultSet spielklasseResult = st.executeQuery(sql);
            spielklasseResult.next();
            temp = new Spielklasse(spielklasseID, spielklasseResult.getString(2), spielklasseResult.getString(3), spielklasseResult.getInt("turnierid"));
            System.out.println(spielklasseResult.getString(2));


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lesen Klappt nicht");
        }
        return temp;
    }

    @Override
    public List<Spielklasse> getAllSpielklassen() {
        List<Spielklasse> alleSpielklassen = new ArrayList<Spielklasse>();
        SQLConnection con = new SQLConnection();
        Connection connection = con.SQLConnection();
        int size = 0;
        try {
            Statement st = connection.createStatement();
            ResultSet count = st.executeQuery("SELECT COUNT(SpielklasseID) from spielklasse");
            count.next();
            size = count.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Spielklasse Zählen Klappt nicht");
        }
        try {
            Statement st = connection.createStatement();
            ResultSet spielklasseResult = st.executeQuery("Select * from spielklasse");
            for (int i = 1; i <= size; i++) {
                spielklasseResult.next();
                alleSpielklassen.add(new Spielklasse(spielklasseResult.getInt(1), spielklasseResult.getString(2),spielklasseResult.getString(3), spielklasseResult.getInt(5)));
                System.out.println(spielklasseResult.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Spielklassenliste Lesen Klappt nicht");
        }
        return alleSpielklassen;
    }
}
