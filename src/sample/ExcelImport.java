package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import sample.DAO.auswahlklasse;

/**
 * Created by Manuel Hüttermann on 15.08.2017.
 */
public class ExcelImport implements Initializable{


    public static ObservableList getObs_vorh() {
        return obs_vorh;
    }

    public static void setObs_vorh(ObservableList obs_vorh) {
        ExcelImport.obs_vorh = obs_vorh;
    }

    private static Spieler aktuellerSpieler = new Spieler();
    private static Dictionary<Spieler, ObservableList> dict_doppelte_spieler = new Hashtable<Spieler, ObservableList>();
    private static ObservableList obs_vorh=FXCollections.observableArrayList();

    public static ObservableList getObs_erf_spieler() {
        return obs_erf_spieler;
    }

    public static void setObs_erf_spieler(ObservableList obs_erf_spieler) {
        ExcelImport.obs_erf_spieler = obs_erf_spieler;
    }

    private static ObservableList obs_erf_spieler=FXCollections.observableArrayList();



    //private static ArrayList<Spieler> vorhandeneSpieler;
    //private static Dictionary<Spieler,ArrayList> dictvorhandenespieler = new Hashtable();
    //private static Spieler SpielerzumHinzufeuegen=null;

/*
    public static void setSpielerzumHinzufeuegen(Spieler spielerzumHinzufeuegen) {
        SpielerzumHinzufeuegen = spielerzumHinzufeuegen;
    }
    public static Spieler getSpielerzumHinzufeuegen() {
        return SpielerzumHinzufeuegen;
    }

    public static Dictionary<Spieler, ArrayList> getDictvorhandenespieler() {
        return dictvorhandenespieler;
    }

    public void setDictvorhandenespieler(Dictionary<Spieler, ArrayList> dictvorhandenespieler) {
        this.dictvorhandenespieler = dictvorhandenespieler;
    }

    public static ArrayList<Spieler> getVorhandeneSpieler() {
        return vorhandeneSpieler;
    }

    public static void setVorhandeneSpieler(ArrayList<Spieler> vorhandeneSpieler) {
        ExcelImport.vorhandeneSpieler = vorhandeneSpieler;
    }
    public static void addDictvorhandenespieler(Spieler neuerSpieler)
    {
        dictvorhandenespieler.put(neuerSpieler,vorhandeneSpieler);

    }*/
    public static boolean importExcelData(String excelDatei) {
        try {
            FileInputStream iStream = new FileInputStream(excelDatei);
            HSSFWorkbook workbook = new HSSFWorkbook(iStream);

            HSSFSheet worksheet = workbook.getSheet("Einzel");
            readWorkSheetSingle(worksheet);

            worksheet = workbook.getSheet("Doppel");
            readWorkSheetDoubleMixed(worksheet);

            worksheet = workbook.getSheet("Mixed");
            readWorkSheetDoubleMixed(worksheet);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    private static void readWorkSheetSingle(HSSFSheet worksheet) {
        int actualRow = 1;

        while ((worksheet.getRow(actualRow) != null)) {
            //get row
            try {
                HSSFRow row = worksheet.getRow(actualRow);
                Spieler sp = readRow(row);
                if(sp!=null) {
                    ExcelImport exc= new ExcelImport();
                    exc.pressBtn_Popup();
                    //ExcelImport.getObs_vorh().add(sp);
                    obs_vorh.clear();
                    dict_doppelte_spieler.remove(sp.getExtSpielerID());
                    System.out.println("Spieler erfolgreich (ohne Duplikat) erstellt");


                }
                actualRow++;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static void readWorkSheetDoubleMixed(HSSFSheet worksheet) {
        try {
            int actualRow = 1;

            while ((worksheet.getRow(actualRow) != null)) {
                //get first row
                HSSFRow row = worksheet.getRow(actualRow);
                readRow(row);
                Spieler sp = readRow(row);
                if(sp!=null) {
                   // ExcelImport.getVorhandeneSpieler().add(sp);
                }
                actualRow++;

                //get second row
                row = worksheet.getRow(actualRow);
                if (row != null) {
                    Spieler tempSpieler = readRow(row);
                    if (tempSpieler !=null){
                        auswahlklasse.getObs_spieler().add(tempSpieler);
                        auswahlklasse.addSpieler(tempSpieler);
                        System.out.println(tempSpieler.getVName()+" "+tempSpieler.getNName()+" gespeichert"+ " geschlecht:"+tempSpieler.getGeschlecht()+" extID:"+tempSpieler.getExtSpielerID()+"verein: "+tempSpieler.getVerein()+" gdatum:"+tempSpieler.getGDatum());
                    }
                }
                actualRow++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Spieler getAktuellerSpieler() {
        return aktuellerSpieler;
    }

    public static void setAktuellerSpieler(Spieler aktuellerSpieler) {
        ExcelImport.aktuellerSpieler = aktuellerSpieler;
    }

    private static Spieler readRow(HSSFRow row) throws Exception {
        dict_doppelte_spieler=new Hashtable<>();
        int cellNumber = 0;
        HSSFCell cell = row.getCell(cellNumber);


        String vName = "";
        String nName;
        LocalDate gDatum = LocalDate.now();
        boolean geschlecht = false;
        String extSpielerID = "";
        int rPunkte[] = new int[3];
        Verein verein = null;



        HSSFCell nachnameZelle = row.getCell(1);
        if (nachnameZelle != null ) {
            if(nachnameZelle.getStringCellValue() != "") {
                nName = nachnameZelle.getStringCellValue();

                HSSFCell vornameZelle = row.getCell(0);
                if (vornameZelle != null) {
                    vName = vornameZelle.getStringCellValue();
                }
                HSSFCell geschlechtZelle = row.getCell(2);
                if (geschlechtZelle != null) {
                    geschlecht = (geschlechtZelle.getStringCellValue().toUpperCase().contains("M"));
                }
                HSSFCell gdatumZelle = row.getCell(12);
                if (gdatumZelle != null) {
                    if(gdatumZelle.getCellTypeEnum()==CellType.NUMERIC){
                        gDatum = gdatumZelle.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    }
                    else{
                        auswahlklasse.WarnungBenachrichtigung("Formatierungsfehler",vName+" "+nName+": Datumszelle nicht richtig formatiert");
                        //throw new Exception("Datumszelle nicht richtig formatiert");

                    }
                }
                HSSFCell extSpieleridZelle = row.getCell(11);
                if (extSpieleridZelle != null) {
                    extSpielerID = extSpieleridZelle.getStringCellValue();
                }

                //RanglistenpunkteZellen
                HSSFCell einzelRPunkteZelle = row.getCell(8);
                if (einzelRPunkteZelle != null) {
                    rPunkte[0] = (int) einzelRPunkteZelle.getNumericCellValue();
                }
                HSSFCell doppelRPunkteZelle = row.getCell(9);
                if (doppelRPunkteZelle != null) {
                    rPunkte[1] = (int) doppelRPunkteZelle.getNumericCellValue();
                }
                HSSFCell mixedRPunkteZelle = row.getCell(10);
                if (mixedRPunkteZelle != null) {
                    rPunkte[2] = (int) mixedRPunkteZelle.getNumericCellValue();
                }


                String vereinsname ="";
                String verband="";
                String extVereinsID="";
                //Vereinszellen
                HSSFCell vereinsnameZelle = row.getCell(3);
                if (vereinsnameZelle != null) {
                    vereinsname = vereinsnameZelle.getStringCellValue();
                }
                HSSFCell vereinverbandZelle = row.getCell(4);
                if (vereinverbandZelle != null) {
                    verband = vereinverbandZelle.getStringCellValue();
                    System.out.println("E1 " + vereinverbandZelle.getStringCellValue());
                }
                HSSFCell extVereinsidZelle = row.getCell(13);
                if (extVereinsidZelle != null) {
                    if (extVereinsidZelle.getCellTypeEnum() == CellType.NUMERIC) {
                        Integer extID = (int)extVereinsidZelle.getNumericCellValue();
                        extVereinsID = extID.toString();
                    } else if (extVereinsidZelle.getCellTypeEnum() == CellType.STRING){
                        extVereinsID = extVereinsidZelle.getStringCellValue();
                    }
                    System.out.println("N1 " + extVereinsidZelle.getNumericCellValue());
                }
                Enumeration e = auswahlklasse.getVereine().keys();
                while (e.hasMoreElements()){
                    int key = (int) e.nextElement();
                    if(verein == null) {
                        Verein tempVerein = auswahlklasse.getVereine().get(key);
                        if (tempVerein.getName().contentEquals(vereinsname)) {
                            verein = tempVerein;
                        } else if (tempVerein.getExtVereinsID().contentEquals(extVereinsID)) {
                            verein = tempVerein;
                        }
                    }
                }
                if(verein ==null){
                    {
                        verein = new Verein(extVereinsID,vereinsname,verband);
                        auswahlklasse.addVerein(verein);
                    }
                }
                Spieler neuerSpieler = new Spieler(vName,nName,gDatum,geschlecht,rPunkte,verein,extSpielerID,"");
                for(Enumeration ee = auswahlklasse.getSpieler().elements();ee.hasMoreElements();)
                {
                    Spieler sp = (Spieler) ee.nextElement();
                    if(sp!=null && neuerSpieler!=null) {
                        if (sp.getNName().equalsIgnoreCase(neuerSpieler.getNName()) && sp.getVName().equalsIgnoreCase(neuerSpieler.getVName()) ) {

                            System.out.println("Übereinstimmung gefunden:--");


                            System.out.println(sp.getVName() + " " + sp.getNName() + " --- " + neuerSpieler.getVName() + " " + neuerSpieler.getNName() + " --- " + neuerSpieler.getExtSpielerID() + " " + neuerSpieler.getExtSpielerID());


                            //vorhandeneSpieler.add(sp);
                            obs_vorh.add(sp);

                            dict_doppelte_spieler.put(neuerSpieler,obs_vorh);
                            aktuellerSpieler=neuerSpieler;

                            System.out.println(neuerSpieler.getExtSpielerID()+"........"+dict_doppelte_spieler.get(neuerSpieler.getExtSpielerID()));
                            //exc.pressBtn_Popup();




                        }
                    }

                }

                //obs_spieler_erfolreich.add(neuerSpieler);





/*

                //felderLeeren();


                if(obs_vorh.size()>0)
                {
                    array2.add(obs_vorh);
                    array2.get(0).add(neuerSpieler);

*/
/*                    obs_neu_vorh.add(obs_vorh);
                    ExcelImport.setSpielerzumHinzufeuegen(neuerSpieler);
                    ExcelImport.setVorhandeneSpieler(vorhandeneSpieler);
                    ExcelImport.addDictvorhandenespieler(neuerSpieler);*//*



                }
                else
                {
                    neuerSpieler.getSpielerDAO().create(neuerSpieler);
                    auswahlklasse.addSpieler(neuerSpieler);
                    auswahlklasse.getSpieler().put(neuerSpieler.getSpielerID(),neuerSpieler);
                    auswahlklasse.InfoBenachrichtigung("erf","erf");
                }
*/

                return neuerSpieler;


            }
        }
        return null;
    }
    public  void pressBtn_Popup() throws Exception {
        //System.out.println("test");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GUI/spielerVorhanden.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        auswahlklasse.getStagesdict().put("SpielerVorhanden",stage);
        stage.setScene(new Scene(root1));
        stage.showAndWait();
        stage.setTitle("Spieler vorhanden");
    }

    public static Dictionary<Spieler, ObservableList> getDict_doppelte_spieler() {
        return dict_doppelte_spieler;
    }

    public static void setDict_doppelte_spieler(Dictionary<Spieler, ObservableList> dict_doppelte_spieler) {
        ExcelImport.dict_doppelte_spieler = dict_doppelte_spieler;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // ExcelImport.getVorhandeneSpieler().clear();
    }
}
