package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flori on 30.05.2017.
 */
public class SpielerDAOimpl implements SpielerDAO {

    @Override
    public boolean create(Spieler spieler) {

        String sql = "INSERT INTO spieler("
                + "VName,"
                + "NName, "
                + "SpielerID) "
                + "VALUES(?,?,?)";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setString(1, spieler.getvName());
            smt.setString(2, spieler.getnName());
            smt.setInt(3, spieler.getSpielerID());
            smt.executeUpdate();
            smt.close();
            System.out.println("Einfügen klappt");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Einfügen Klappt nicht");
        }


        return false;
    }

    @Override
    public boolean delete(Spieler spieler) {
        String sql = "Delete From spieler Where spielerID= ?";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, spieler.getSpielerID());
            smt.executeUpdate();
            smt.close();
            System.out.println("Verein Loeschen klappt");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Verein Loeschen Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean update(Spieler spieler) {

        String sql = "UPDATE spieler SET VName = ?, NName = ? WHERE spielerID = ?";
        ;
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setString(1, spieler.getvName());
            smt.setString(2, spieler.getnName());
            smt.setInt(3, spieler.getSpielerID());
            smt.executeUpdate();
            smt.close();
            System.out.println("Update klappt");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Update Klappt nicht");
        }


        return false;
    }

    @Override
    public Spieler read(int spielerID) {
        String sql = "Select * from spieler Where spielerid=" + spielerID;
        Spieler temp = null;
        try {
            SQLConnection con = new SQLConnection();
            Connection connection = con.SQLConnection();
            Statement st = connection.createStatement();
            ResultSet spielerResult = st.executeQuery(sql);
            spielerResult.next();
            temp = new Spieler(spielerResult.getString(2), spielerResult.getString(3), spielerID);
            System.out.println(spielerResult.getString(2));
            System.out.println("Lesen klappt");


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lesen Klappt nicht");
        }
        return temp;

    }

    @Override
    public List<Spieler> getAllSpieler() {
        List<Spieler> alleSpieler = new ArrayList<Spieler>();
        SQLConnection con = new SQLConnection();
        Connection connection = con.SQLConnection();
        int size = 0;
        try {
            Statement st = connection.createStatement();
            ResultSet count = st.executeQuery("SELECT COUNT(SpielerID) from spieler");
            count.next();
            size = count.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Spieler Zählen Klappt nicht");
        }
        try {
            Statement st = connection.createStatement();
            ResultSet spielerResult = st.executeQuery("Select VName, NName, SpielerID from spieler");
            for (int i = 1; i <= size; i++) {
                spielerResult.next();
                alleSpieler.add(new Spieler(spielerResult.getString(1), spielerResult.getString(2), spielerResult.getInt(3)));
                System.out.println(spielerResult.getString(2));
                System.out.println("Spielerliste Lesen klappt");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Spielerliste Lesen Klappt nicht");
        }

        return alleSpieler;
    }

    @Override
    public List<Spieler> getSetzliste(Spielklasse spielklasse) {

        List<Spieler> setzliste = new ArrayList<Spieler>();
        SQLConnection con = new SQLConnection();
        Connection connection = con.SQLConnection();
        int size = 0;
        try {
            Statement st = connection.createStatement();
            ResultSet count = st.executeQuery("SELECT * from spieler");
            ResultSetMetaData countMetaData = count.getMetaData();
            size = countMetaData.getColumnCount();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Spieler Zählen Klappt nicht");
        }
        for (int i = 1; i <= size; i++) {
            setzliste.add(read(i));
            return null;
        }
        return setzliste;
    }
}