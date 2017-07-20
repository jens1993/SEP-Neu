package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Badminton Turnierverwaltung");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
    	
    	 //testverbindung.PrintResult(r);
        //launch(args);
        //SQLConnection testverbindung = new SQLConnection();
        //KO testsystem = new KO(65);
        //testsystem.rundenBerechnen();
        SQLConnection con = new SQLConnection();
        Connection connection = con.SQLConnection();
        Statement st = null;


        int size = 0;
        try {
            st = connection.createStatement();
            ResultSet count = st.executeQuery("SELECT * from spieler");
            ResultSetMetaData countMetaData = count.getMetaData();
            size = countMetaData.getColumnCount();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Zählen Klappt nicht");
        }
        for (int i=1; i<=size; i++){
            SpielerCRUDimpl crud = new SpielerCRUDimpl();
            crud.read(i);
        }


        /*SQLConnection testverbindung = new SQLConnection();
        
        System.out.println("------------------------------------------");System.out.println("------------------------------------------");
        boolean hallo = testverbindung.insertSpieler("jens", "isttoll");
        System.out.println("Einfügen = "+hallo);
        
        
        System.out.println("------------------------------------------");System.out.println("------------------------------------------");
        String name = testverbindung.getSpielerName(1);
        System.out.println("Spielername: "+name);

        
        System.out.println("------------------------------------------");System.out.println("------------------------------------------");
        int i = testverbindung.getSpielerID("jens", "isttoll");
        if(i>0)
        {
        	System.out.println("Der Index lautet "+i);
        }
        else
        {
        	System.out.println("Spieler nicht gefunden");
        }
        
        
        System.out.println("------------------------------------------");System.out.println("------------------------------------------");
        ResultSet j = testverbindung.getSpielerr(i);
        testverbindung.PrintResult(j);
        System.out.println("------------------------------------------");System.out.println("------------------------------------------");
        ResultSet r = testverbindung.executeSQL("SELECT * FROM spieler");
        testverbindung.PrintResult(r);
*/
    }
}
