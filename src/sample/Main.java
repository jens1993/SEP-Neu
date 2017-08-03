package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Main extends Application {

    private static Main instance;
    public static Main getInstance() {
        return instance;
    }
    private Stage primaryStage;



    @Override
    public void start(Stage primaryStage) throws Exception{
        instance = this;
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("GUI/Main.fxml"));
        this.primaryStage.setTitle("Badminton Turnierverwaltung - Kein Turnier ausgewählt");
        Scene scene = new Scene(root,500,200);
        this.primaryStage.setScene(scene);
        this.primaryStage.setMaximized(true);
        this.primaryStage.show();
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("mouse click detected! " + mouseEvent.getSource());
            }
        });


    }
    public void updateTitle(String title) {
        primaryStage.setTitle(title);
    }

    public static void main(String[] args) {




        launch(args);
        //$java -Duser.language=en com.tutego.insel.bundle.InternationalHelloWorld

        //Locale.setDefault( new Locale("en", "UK") );
//        Locale.setDefault( new Locale("de", "ch") );
//        String baseName = "resources.HelloWorld";
//
//        try
//        {
//            ResourceBundle bundle = ResourceBundle.getBundle( baseName );
//            System.out.println( bundle.getString("Bye") );
//        }
//        catch ( MissingResourceException e ) {
//            System.err.println( e );
//        }


//       ArrayList <Turnier> turnierliste = new ArrayList<>();
//    	TurnierDAO test = new TurnierDAOimpl();
//
//        //System.out.println((i+1)+" "+setzliste.get(i).getName());
//
//        Turnier turnier = new Turnier("testturnier",1,LocalDate.now());
//    	test.read(turnier);
//        Spielklasse spielklasse =turnier.getSpielklassen().get(1);
//   	    ArrayList<Team> setzliste = spielklasse.getSetzliste();
//        //spielklasse.setSpielsystem(new Gruppe(setzliste,spielklasse));
//        List<Ergebnis> ergebnisse = new ArrayList<>();
//        ergebnisse.add(new Ergebnis(21,15,21,12));
//        ergebnisse.add(new Ergebnis(15,21,21,19,30,29));
//        ergebnisse.add(new Ergebnis(21,12,21,11));
//        ergebnisse.add(new Ergebnis(21,18,21,19));
//        ergebnisse.add(new Ergebnis(22,20,15,21,23,25));
//        ergebnisse.add(new Ergebnis(16,21,21,15,21,14));
//        ergebnisse.add(new Ergebnis(26,24,28,30,12,21));
//        ergebnisse.add(new Ergebnis(21,14,21,17));
//        ergebnisse.add(new Ergebnis(21,6,21,14));
//        ergebnisse.add(new Ergebnis(16,21,14,21));
//        ergebnisse.add(new Ergebnis(26,24,21,14,15,21));
//        ergebnisse.add(new Ergebnis(21,15,21,18));
//        ergebnisse.add(new Ergebnis(12,21,16,21));
//        ergebnisse.add(new Ergebnis(15,21,13,21));
//        ergebnisse.add(new Ergebnis(19,21,21,14,21,18));





        /*for(int i=4; i<=7;i++){
            System.out.println(turnier.getSpiele().get(i).getHeim()+" gegen "+turnier.getSpiele().get(i).getGast());
            turnier.getSpiele().get(i).setErgebnis(ergebnisse.get((int)(Math.random()*ergebnisse.size())));
            //turnier.getSpiele().get(i).setErgebnis(ergebnisse.get(0));
        }
        for(int i=2; i<=3;i++){
            System.out.println(turnier.getSpiele().get(i).getHeim()+" gegen "+turnier.getSpiele().get(i).getGast());
            turnier.getSpiele().get(i).setErgebnis(ergebnisse.get((int)(Math.random()*ergebnisse.size())));
            //turnier.getSpiele().get(i).setErgebnis(ergebnisse.get(0));
        }
        for(int i=1; i<=1;i++){
            System.out.println(turnier.getSpiele().get(i).getHeim()+" gegen "+turnier.getSpiele().get(i).getGast());
            turnier.getSpiele().get(i).setErgebnis(ergebnisse.get((int)(Math.random()*ergebnisse.size())));
            //turnier.getSpiele().get(i).setErgebnis(ergebnisse.get(0));
        }*//*


        *//*Ergebnis ergebnis = new Ergebnis(21,15,21,15);
        Ergebnis ergebnis1 = new Ergebnis(15,21,21,19,30,29);
        Ergebnis ergebnis2 = new Ergebnis(21,12,21,11);
        Ergebnis ergebnis3 = new Ergebnis(21,18,21,19);
        Ergebnis ergebnis4 = new Ergebnis(22,20,15,21,23,25);
        Ergebnis ergebnis5 = new Ergebnis(16,21,21,15,21,14);
        Ergebnis ergebnis6 = new Ergebnis(26,24,28,30,12,21);
        Ergebnis ergebnis7 = new Ergebnis(21,14,21,17);
        turnier.getSpiele().get(1).setErgebnis(ergebnis);
        turnier.getSpiele().get(2).setErgebnis(ergebnis1);
        turnier.getSpiele().get(3).setErgebnis(ergebnis2);
        turnier.getSpiele().get(4).setErgebnis(ergebnis3);
        turnier.getSpiele().get(5).setErgebnis(ergebnis4);
        turnier.getSpiele().get(6).setErgebnis(ergebnis5);
        turnier.getSpiele().get(7).setErgebnis(ergebnis6);
        turnier.getSpiele().get(8).setErgebnis(ergebnis7);
*//*








        //Turnier turnier = new Turnier("Kreismeisterschaften", 2, LocalDate.now());
    	//test.create(turnier);
    	 //testverbindung.PrintResult(r);
        //launch(args);
        //SQLConnection testverbindung = new SQLConnection();
        //KO testsystem = new KO(65);
        //testsystem.rundenBerechnen();


       *//* TurnierDAO test = new TurnierDAOimpl();
        Turnier turnier = new Turnier("Tolles Turnier",1);
        test.delete(turnier);*//*

        //VereinDAOimpl test = new VereinDAOimpl();
        //Verein Osterbrock = new Verein(5, "123456789","Osterbrock",  "BWE");
        //Verein Testest = new Verein(6, "123456789","Osterbrock",  "BWE");
        //test.create(Osterbrock);
        //test.update(Testest);
        //test.delete(Osterbrock);

       *//* SpielerDAO test = new SpielerDAOimpl();
        List<Spieler> alleSpieler;
        alleSpieler = test.getAllSpieler();
        for (int i=0; i < alleSpieler.size();i++){
            System.out.println(alleSpieler.get(i).getName());
        }*//*

        *//*SpielklasseDAO test = new SpielklasseDAOimpl();
        Spielklasse deb = new Spielklasse(5, "Dameneinzel", "B", 1);
        test.create(deb);
        test.update(deb);
        test.delete(deb);
        List<Spielklasse> alleKlassen = test.getAllSpielklassen();
        for (int i=0; i < alleKlassen.size();i++){
            System.out.println(alleKlassen.get(i).getDisziplin()+alleKlassen.get(i).getNiveau());
        }*//*
        *//*Spielklasse spielklasse = new Spielklasse(1,"Herreneinzel","A",1);
        Spieler spieler = new Spieler("Herbert", "Müller",1);
        Spieler spieler2 = new Spieler("Herbert", "Müller",2);
        Spiel spiel = new Spiel(spieler, spieler2,spielklasse);
        Ergebnis ergebnis = new Ergebnis(21,15,21,19);
        spiel.setErgebnis(ergebnis);
        ErgebnisDAO test = new ErgebnisDAOimpl();
        test.update(spiel);*//*

        //for (int i=0; i<setzliste.size(); i++){
          //  System.out.println((i+1)+" "+setzliste.get(i).getName());
        //}
        //Spielsystem spielsystem = new KO(setzliste);

        //SpielDAO test = new SpielDAOimpl();
        //Spielklasse spielklasse = new Spielklasse(1, "Herren", "A", 1);

//        Spiel testspiel = new Spiel(1,1,2,spielklasse);



       *//* SpielDAOimpl test = new SpielDAOimpl();
        Spielklasse heA = new Spielklasse(5);
        Spieler hans = new Spieler("Hans", "Müller", 41);
        Spieler harald = new Spieler("Harald", "Test", 42);
        Spiel spiel1 = new Spiel(30, hans, harald, heA);
        test.delete(spiel1);*//*
        //KO test = new KO(16);


        //Alle Spieler bei Programmstart einlesen: (in einzelnen Thread packen)
        //vereineEinlesen();
        //spielerEinlesen();


        *//*SQLConnection testverbindung = new SQLConnection();
        
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
        testverbindung.PrintResult(r);*/

    }
}
