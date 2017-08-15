package sample.DAO;

import sample.*;
import sample.Enums.Disziplin;
import sample.Enums.Niveau;
import sun.security.provider.ConfigFile;

import java.sql.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
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
            smt.setInt(3, spielklasse.getTurnier().getTurnierid());
            smt.setInt(4, spielklasse.getSpielklasseID());
            smt.executeUpdate();
            smt.close();
            con.closeCon();
            auswahlklasse a = new auswahlklasse();
            //a.getAktuelleTurnierAuswahl().
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
            con.closeCon();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spielklasse Loeschen Klappt nicht");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Allgemeiner Fehler");
        }
        return false;
    }

    @Override
    public boolean deleteSpielsystem(Spielklasse spielklasse) {
        if(spielklasse.getSpielsystem()!=null) {
            String sqlErgebnis = "DELETE FROM spiel_satzergebnis WHERE SpielID= 'start'";
            String sqlSpielklasseSpielID = "DELETE FROM spielklasse_spielid WHERE SpielID= 'start'";
            String sqlSpiel = "DELETE FROM spiel WHERE SpielID= 'start'";
            for (int i=0;i<spielklasse.getSpielsystem().getRunden().size();i++){
                for (int j=0;j<spielklasse.getSpielsystem().getRunden().get(i).size();j++){
                    sqlErgebnis += " OR SpielID= ?";
                    sqlSpielklasseSpielID += " OR SpielID= ?";
                    sqlSpiel += " OR SpielID= ?";
                }
            }
            try {
                int zaehler = 1;
                SQLConnection con = new SQLConnection();
                PreparedStatement smtErgebnis = con.SQLConnection().prepareStatement(sqlErgebnis);
                PreparedStatement smtSpielklasseSpielId = con.SQLConnection().prepareStatement(sqlSpielklasseSpielID);
                PreparedStatement smtSpiel = con.SQLConnection().prepareStatement(sqlSpiel);
                for (int i=0;i<spielklasse.getSpielsystem().getRunden().size();i++){
                    for (int j=0;j<spielklasse.getSpielsystem().getRunden().get(i).size();j++){
                        Spiel spiel = spielklasse.getSpielsystem().getRunden().get(i).get(j);
                        smtErgebnis.setInt(zaehler,spiel.getSpielID());
                        smtSpielklasseSpielId.setInt(zaehler,spiel.getSpielID());
                        smtSpiel.setInt(zaehler,spiel.getSpielID());
                        zaehler++;
                    }
                }
                smtErgebnis.executeUpdate();
                smtErgebnis.close();
                smtSpielklasseSpielId.executeUpdate();
                smtSpielklasseSpielId.close();
                smtSpiel.executeUpdate();
                smtSpiel.close();
                con.closeCon();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Spielklasse Loeschen Klappt nicht");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Allgemeiner Fehler");
            }
        }
        System.out.println("kein Spielsystem vorhanden");
        return false;
    }

    @Override
    public boolean update(Spielklasse spielklasse) {
        String sql = "UPDATE spielklasse "
                + "SET Disziplin = ?, "
                + "Niveau = ?, "
                + "SpielsystemCode = ?, "
                + "Meldekosten = ?, "
                + "Aktiv = ? "
                + "WHERE SpielklasseID = ? ";
        try {
            SQLConnection con = new SQLConnection();
            PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
            smt.setString(1, spielklasse.getDisziplin());
            smt.setString(2, spielklasse.getNiveau());
            smt.setInt(3, spielklasse.getSpielsystem().getSpielsystemCode());
            smt.setFloat(4, spielklasse.getMeldeKosten());
            smt.setBoolean(5, spielklasse.isAktiv());
            smt.setInt(6, spielklasse.getSpielklasseID());
            smt.executeUpdate();
            smt.close();
            con.closeCon();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Spielklasse Update Klappt nicht");
        }
        return false;
    }

/*    @Override
    public List<Spielklasse> getAllSpielklasse() {
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
            ResultSet SpielklasseResult = st.executeQuery("Select Disziplin, Niveau, turnierid, SpielklasseID from spielklasse");
            for (int i = 1; i <= size; i++) {
                SpielklasseResult.next();
                alleSpielklassen.add(new Spielklasse(SpielklasseResult.getInt("SpielklasseID"),SpielklasseResult.getString("Disziplin"))
                       // new Spieler(spielerResult.getString(1), spielerResult.getString(2), spielerResult.getInt(3)));
                System.out.println(SpielklasseResult.getString(2));
                System.out.println("Spielklasseliste Lesen klappt");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Spielklasseliste Lesen Klappt nicht");
        }

        return alleSpielklassen;
    }*/

/*    @Override
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
    }*/
/*@Override
public Dictionary<Integer, Spielklasse> getSpielklassenDict(Turnier turniereingabe) {
    Dictionary<Integer, Spielklasse> turnierListe = new Hashtable<Integer,Spielklasse>();
    String sql ="SELECT * FROM spielklasse WHERE turnierid = ?";

    try {
        SQLConnection con = new SQLConnection();
        PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
        smt.setInt(1, turniereingabe.getTurnierid());
        ResultSet TurnierResult = smt.executeQuery();
        while (TurnierResult.next()){
            int spielklasseid  = TurnierResult.getInt("SpielklasseID");
            Disziplin disziplin = Disziplin.valueOf(TurnierResult.getString("Disziplin"));
            Niveau niveau = Niveau.valueOf(TurnierResult.getString("Niveau"));
            int turnierid  = TurnierResult.getInt("turnierid");
            turnierListe.put(spielklasseid,new Spielklasse(spielklasseid,disziplin,niveau,turnierid));
        }
        smt.close();
        con.closeCon();
        System.out.println("Spielklasse lesen klappt");
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Spielklasse lesen klappt nicht");
    }


    return turnierListe;
}*/
/*@Override
public Dictionary<Integer, Spielklasse> getAllSpielklassenDict() {
    Dictionary<Integer, Spielklasse> turnierListe = new Hashtable<Integer,Spielklasse>();
    String sql ="SELECT * FROM spielklasse";

    try {
        SQLConnection con = new SQLConnection();
        PreparedStatement smt = con.SQLConnection().prepareStatement(sql);
        //smt.setInt(1, turnier.getTurnierid());
        ResultSet TurnierResult = smt.executeQuery();
        while (TurnierResult.next()){
            int spielklasseid  = TurnierResult.getInt("SpielklasseID");
            Disziplin disziplin = Disziplin.valueOf(TurnierResult.getString("Disziplin"));
            Niveau niveau = Niveau.valueOf(TurnierResult.getString("Niveau"));
            int turnierid  = TurnierResult.getInt("turnierid");
            turnierListe.put(spielklasseid,new Spielklasse(spielklasseid,disziplin,niveau,turnierid));
        }
        smt.close();
        con.closeCon();
        System.out.println("Spielklasse lesen klappt");
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Spielklasse lesen klappt nicht");
    }


    return turnierListe;
}*/
    /*@Override
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
                alleSpielklassen.add(new Spielklasse(spielklasseResult.getInt(1), spielklasseResult.getString(2),spielklasseResult.getString(3),spielklasseResult.getInt(4)));
                System.out.println(spielklasseResult.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Spielklassenliste Lesen Klappt nicht");
        }
        return alleSpielklassen;
    }*/
}
