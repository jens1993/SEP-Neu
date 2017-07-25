package sample.DAO;

import sample.*;
import sample.Spielsysteme.*;
import sample.Enums.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florian-PC on 23.07.2017.
 */
public class SetzlisteDAOimpl implements SetzlisteDAO {
    @Override
    public boolean create(List<Spieler> setzliste, Spielklasse spielklasse) {
        try {
            SQLConnection con = new SQLConnection();
            for (int i = 0; i < setzliste.size(); i++) {


                String sql = "INSERT INTO spielklasse_setzliste("
                        + "setzplatz,"
                        + "spielklasseID, "
                        + "spielerID "
                        + "VALUES(?,?,?)";

                PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
                smt.setInt(1, i);
                smt.setInt(2, spielklasse.getSpielklasseID());
                smt.setInt(3, setzliste.get(i).getSpielerID());
                smt.executeUpdate();
                smt.close();
                System.out.println("Setzliste Einfügen klappt");
                return true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Setzliste Einfügen Klappt nicht");
        }
        return false;
    }

    @Override
    public boolean delete(int spielklasseid) {
        String sql = "Delete From spielklasse_setzliste Where SpielklasseID= ?";
        try{
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setInt(1, spielklasseid);
            smt.executeUpdate();
            smt.close();
            System.out.println("Setzliste Loeschen klappt");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Setzliste Loeschen Klappt nicht");
        }

        return false;
    }

    @Override
    public boolean update(List<Spieler> setzliste, Spielklasse spielklasse) {
        boolean temp;
        temp = delete(spielklasse.getSpielklasseID());
        if (temp) {
            temp = create(setzliste, spielklasse);
        }
        return temp;
    }

    @Override
    public List<Spieler> read(int spielklasseID) {
        List<Spieler> setzliste = new ArrayList<Spieler>();
        SQLConnection con = new SQLConnection();
        Connection connection = con.SQLConnection();
        int size = 0;
        try {
            Statement st = connection.createStatement();
            ResultSet count = st.executeQuery("SELECT COUNT(setzplatz) from spielklasse_setzliste where spielklasseID = "+spielklasseID);
            count.next();
            size = count.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Spielklasse Zählen Klappt nicht");
        }
        try {
            Statement st = connection.createStatement();
            ResultSet setzlisteResult = st.executeQuery("SELECT setzplatz,VName,NName,spielklasse_setzliste.spielerID FROM spielklasse_setzliste " +
                    "inner JOIN spieler ON spielklasse_setzliste.spielerID = spieler.SpielerID " +
                    "WHERE spielklasseID = " + spielklasseID +
                    " ORDER BY setzplatz ;");
            for (int i = 1; i <= size; i++) {
                setzlisteResult.next();
                setzliste.add(new Spieler(setzlisteResult.getString(2), setzlisteResult.getString(3),setzlisteResult.getInt(4)));
                System.out.println(setzlisteResult.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Setzliste Lesen Klappt nicht");
        }
        return setzliste;
    }

    @Override
    public List<List<Spieler>> getAllSetzlisten() {
        return null;
    }
}
