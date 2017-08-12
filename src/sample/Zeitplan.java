package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Spielsysteme.Spielsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Florian-PC on 11.08.2017.
 */
public class Zeitplan {
    private ObservableList<Spiel> zeitplan = FXCollections.observableArrayList();
    private ArrayList<ArrayList<ArrayList<Spiel>>> spielsystemRunden = new ArrayList<>();
    private ArrayList<ArrayList<Spiel>> alleRundenSortiert = new ArrayList<>();
    private Turnier turnier;

    public Zeitplan(Turnier turnier) {
        this.turnier = turnier;
        zeitplanErstellen();
    }

    private void zeitplanErstellen(){
        alleSpielsystemeEinlesen();
        if (spielsystemRunden.size()>1){
            spielSystemeSortieren();
            alleRundenSortieren();
        }
        listenVereinen();
    }



    private void alleSpielsystemeEinlesen(){
        if (turnier.getSpielklassen()!=null){
            for (int i=0;i<turnier.getSpielklassen().size();i++){
                if (turnier.getSpielklassen().get(i).getSpielsystem()!=null){
                    spielsystemRunden.add(turnier.getSpielklassen().get(i).getSpielsystem().getRunden());
                }
            }
        }
    }


    private void spielSystemeSortieren(){
        Collections.sort(spielsystemRunden, new Comparator<ArrayList<ArrayList<Spiel>>>() {
            @Override
            public int compare(ArrayList<ArrayList<Spiel>> list1,ArrayList<ArrayList<Spiel>> list2) {
                return list2.size()-list1.size();
            }
        });
    }


    private void alleRundenSortieren(){
        while (spielsystemRunden.size()>0){
            for (int i=0;i<spielsystemRunden.size();i++){
                int verbleibendeRunden = spielsystemRunden.get(i).size();
                if (verbleibendeRunden!=0){
                    ArrayList<Spiel> runde = spielsystemRunden.get(i).get(verbleibendeRunden-1);
                    alleRundenSortiert.add(runde);
                }
                else {
                    spielsystemRunden.remove(i);
                }
            }
        }
    }
    private void listenVereinen(){
        int spielnummer = 1;
        for(int i=0;i<alleRundenSortiert.size();i++){
            for(int j=0;j<alleRundenSortiert.get(i).size();j++){
                Spiel spiel = alleRundenSortiert.get(i).get(j);
                zeitplan.add(spiel);
                spiel.setZeitplanNummer(spielnummer);
                spielnummer++;
            }
        }
    }
}
