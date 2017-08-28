package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Spielsysteme.Spielsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;

/**
 * Created by Florian-PC on 11.08.2017.
 */
public class Zeitplan {
    private static ObservableList<Spiel> zeitplan = FXCollections.observableArrayList();
    private static ArrayList<ArrayList<ArrayList<Spiel>>> spielsystemRunden = new ArrayList<>();
    private static ArrayList<ArrayList<Spiel>> alleRundenSortiert = new ArrayList<>();
    //private Turnier turnier;

    /*public Zeitplan(Turnier turnier) {
        this.turnier = turnier;
        zeitplanErstellen();
    }*/


    public static void zeitplanErstellen(Turnier turnier){
        alleSpielsystemeEinlesen(turnier);
        if (spielsystemRunden.size()>1){
            spielSystemeSortieren();
            alleRundenSortieren();
        }
        listenVereinen();
    }
    public static ArrayList<ArrayList<Spiel>> getAlleRunden(Turnier turnier){
        alleSpielsystemeEinlesen(turnier);
        if (spielsystemRunden.size()>1){
            spielSystemeSortieren();
            alleRundenSortieren();
            return alleRundenSortiert;
        }
        return null;
    }

    private static void alleSpielsystemeEinlesen(Turnier turnier){
        if (turnier.getSpielklassen()!=null){

            Enumeration e = turnier.getSpielklassen().keys();
            while (e.hasMoreElements()){
                int key =(int) e.nextElement();
                if (turnier.getSpielklassen().get(key).getSpielsystem()!=null){
                    spielsystemRunden.add(turnier.getSpielklassen().get(key).getSpielsystem().getRunden());
                }
            }
        }
    }


    private static void spielSystemeSortieren(){
        Collections.sort(spielsystemRunden, new Comparator<ArrayList<ArrayList<Spiel>>>() {
            @Override
            public int compare(ArrayList<ArrayList<Spiel>> list1,ArrayList<ArrayList<Spiel>> list2) {
                return list2.size()-list1.size();
            }
        });
    }


    private static void alleRundenSortieren(){
        while (spielsystemRunden.size()>0){
            for (int i=0;i<spielsystemRunden.size();i++){
                int verbleibendeRunden = spielsystemRunden.get(i).size();
                if (verbleibendeRunden!=0){
                    ArrayList<Spiel> runde = spielsystemRunden.get(i).get(verbleibendeRunden-1);
                    alleRundenSortiert.add(runde);
                    spielsystemRunden.get(i).remove(runde);
                }
                else {
                    spielsystemRunden.remove(i);
                }
            }
        }
    }
    private static void listenVereinen(){
        int spielnummer = 1;
        for(int i=alleRundenSortiert.size()-1;i>=0;i--){
            for(int j=alleRundenSortiert.get(i).size()-1;j>=0;j--){
                Spiel spiel = alleRundenSortiert.get(i).get(j);
                zeitplan.add(spiel);
                spiel.setZeitplanNummer(spielnummer);
                spiel.setRundenZeitplanNummer(i+1);
                spielnummer++;
            }
        }
    }
}
