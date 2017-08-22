package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import sample.DAO.TurnierDAO;
import sample.DAO.TurnierDAOimpl;
import sample.DAO.auswahlklasse;

/**
 * Created by Manuel Hüttermann on 15.08.2017.
 */
public class ExcelImport {

    public static ObservableList<Spieler> getObs_spieler_erfolreich() {
        return obs_spieler_erfolreich;
    }

    public static void setObs_spieler_erfolreich(ObservableList<Spieler> obs_spieler_erfolreich) {
        ExcelImport.obs_spieler_erfolreich = obs_spieler_erfolreich;
    }

    public static ObservableList<Spieler> getObs_spieler_fehlgeschlagen() {
        return obs_spieler_fehlgeschlagen;
    }

    public static void setObs_spieler_fehlgeschlagen(ObservableList<Spieler> obs_spieler_fehlgeschlagen) {
        ExcelImport.obs_spieler_fehlgeschlagen = obs_spieler_fehlgeschlagen;
    }

    private static ObservableList<Spieler> obs_spieler_erfolreich = FXCollections.observableArrayList();
    private static ObservableList<Spieler> obs_spieler_fehlgeschlagen = FXCollections.observableArrayList();



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
                readRow(row);
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
                actualRow++;

                //get second row
                row = worksheet.getRow(actualRow);
                if (row != null) {
                    Spieler tempSpieler = readRow(row);
                    if (tempSpieler !=null && !gibtEsSchon(tempSpieler)){
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
    private static boolean gibtEsSchon(Spieler spieler){
        return true; //wenn es den schon gibt;
    }
    private static Spieler readRow(HSSFRow row) throws Exception {
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
                Spieler neuerSpieler = new Spieler(vName,nName,gDatum,geschlecht,rPunkte,verein,extSpielerID);
                obs_spieler_erfolreich.add(neuerSpieler);




                ArrayList<Spieler> vorhandeneSpieler = new ArrayList<>();

                //felderLeeren();
                for(Enumeration ee = auswahlklasse.getSpieler().elements();ee.hasMoreElements();)
                {
                    Spieler sp = (Spieler) ee.nextElement();
                    if(sp.getNName().equalsIgnoreCase(neuerSpieler.getNName()) && sp.getVName().equalsIgnoreCase(neuerSpieler.getVName()))
                    {

                        System.out.println("Übereinstimmung gefunden:");
                        System.out.println(sp.getVName()+" "+sp.getNName()+" --- "+neuerSpieler.getVName()+" "+neuerSpieler.getNName());


                        vorhandeneSpieler.add(sp);

                    }

                }


                return neuerSpieler;





                //für Klassenzuweisung (Einzel/Doppel/Mixed-Spalte)
                /*HSSFCell cellF1 = row.getCell(5);
                if (cellF1 != null) {
                    System.out.println("F1 " + cellF1.getStringCellValue());
                }
                HSSFCell cellG1 = row.getCell(6);
                if (cellG1 != null) {
                    System.out.println("G1 " + cellG1.getStringCellValue());
                }
                HSSFCell cellH1 = row.getCell(7);
                if (cellH1 != null) {
                    System.out.println("H1 " + cellH1.getStringCellValue());
                }*/
                //gemeldet von
                /*HSSFCell cellQ1 = row.getCell(16);
                if (cellQ1 != null) {
                    System.out.println("Q1 " + cellQ1.getStringCellValue());
                }*/

            }
        }
        return null;
    }

}
