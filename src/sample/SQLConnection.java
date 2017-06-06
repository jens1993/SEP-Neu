package sample;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SQLConnection 
{

        private static Connection con = null;
        private Statement stmt = null;
        private String dbHost = "localhost"; // Hostname
        private String dbPort = "3306";      // Port -- Standard: 3306
        private String dbName = "turnierverwaltung";   // Datenbankname
        private String dbUser = "root";     // Datenbankuser
        private String dbPass = "";      // Datenbankpasswort
        private String db_erstellung = "create table if not exists turnierverwaltung";
        private String db_nutzung = "USE turnierverwaltung";


        public SQLConnection() //eventuell nich pub
        {
            try 
            {
                Class.forName("com.mysql.jdbc.Driver"); // Datenbanktreiber fÃ¼r JDBC Schnittstellen laden.

                // Verbindung zur JDBC-Datenbank herstellen.
                con = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?" + "user=" + dbUser + "&" + "password=" + dbPass); //con muss unbedingt irgendwo geschlossen werden
                System.out.println("Erfolg!");
                stmt = con.createStatement();
                //stmt.executeUpdate(db_erstellung);

            } catch (ClassNotFoundException e) 
            {
                System.out.println("Treiber nicht gefunden");
            } catch (SQLException e) 
            {
                System.out.println("Verbindung nicht moglich");
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        //Funktioniert. Einfach select abfragen
        public ResultSet executeSQL(String sql)
        {
        	try
        	{
        		Statement smt = con.createStatement();
        		
        		ResultSet res = stmt.executeQuery(sql);

        		return res;
        		
        	}
        	catch (SQLException e)
        	{
        		e.printStackTrace();
        		System.out.println("Klappt nicht");
        	}
        	return null;
        }

        //Ergebnis ausgeben (komplette Tabelle z.b.)
        public void PrintResult(ResultSet r) //als boolean machen, um zu prüfen ob erfolgreich (gilt für alle void sql klassen!) Booleans immer weiterleiten und ganz am ende ausgeben ob erfolgreich 
        {
        	try {
				while(r.next())
				{
					int maxColoums = r.getMetaData().getColumnCount();
					String print = "";
					for(int i =1; i<=maxColoums; i++) //eventuell bei 1 starten
					{
						print += " ";
						print += r.getMetaData().getColumnName(i);
						print += " = ";
						print += r.getString(i);
					}
					System.out.println(print);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Fehler");
			}
        }

        //funktioniert
        public int getSpielerID(String firstname, String lastname)
        {
        	String sql = "SELECT ID,VNAME, NNAME FROM spieler";
        	ResultSet r = executeSQL(sql);
        	try {
        		while(r.next())
        		{
        			if(firstname.equals(r.getString(2))&&lastname.equals(r.getString(3))) //Spalte 1 = firstname
        			{
        				return r.getInt(1);
        			}
        		}
        		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return -1; //Überprüfung in main, ob nicht -1 return
        }
        public String getSpielerName(int id)
        {
        	String sql = "SELECT ID,VNAME, NNAME FROM spieler";
        	ResultSet r = executeSQL(sql);
        	try {
        		while(r.next())
        		{ 
        			if(id==Integer.parseInt(r.getString(1))) //Spalte 1 = firstname
        			{
        				return r.getString(2)+" "+r.getString(3);
        			}
        		}
        		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return " Nicht gefunden"; //Überprüfung in main, ob nicht -1 return
        }
        public ResultSet getSpielerr(int id)
        {
        	String sql = "SELECT ID,VNAME, NNAME, GDatum FROM spieler WHERE ID = "+id;
        	try
        	{
        		Statement smt = con.createStatement();
        		
        		ResultSet res = stmt.executeQuery(sql);

        		return res;
        		
        	}
        	catch (SQLException e)
        	{
        		e.printStackTrace();
        		System.out.println("Klappt nicht");
        	}
        	return null;
        }
        public Boolean insertSpieler(String vorname, String nachname)
        {
        	String sql = "INSERT INTO spieler("
			        + "VName,"
			        + "NName) "
			        +  "VALUES(?,?)";
        	try
        	{
        		PreparedStatement smt = con.prepareStatement(sql);
        		smt.setString(1, vorname);
        		smt.setString(2, nachname);
        		smt.executeUpdate();
        		smt.close();
        		return true;
        		
        	}
        	catch (SQLException e)
        	{
        		e.printStackTrace();
        		System.out.println("Klappt nicht");
        	}
        	return false;
        	
        	
        }

        
}
