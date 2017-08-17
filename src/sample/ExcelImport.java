package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Enumeration;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import sample.DAO.auswahlklasse;

/**
 * Created by Manuel Hüttermann on 15.08.2017.
 */
public class ExcelImport {
    public static void importExcelData(String excelDatei) {
        try {
            FileInputStream iStream = new FileInputStream(excelDatei);
            HSSFWorkbook workbook = new HSSFWorkbook(iStream);

            HSSFSheet worksheet = workbook.getSheet("Einzel");
            readWorkSheetSingle(worksheet);

            worksheet = workbook.getSheet("Doppel");
            readWorkSheetDoubleMixed(worksheet);

            worksheet = workbook.getSheet("Mixed");
            readWorkSheetDoubleMixed(worksheet);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void readWorkSheetSingle(HSSFSheet worksheet) {
        int actualRow = 1;

        while ((worksheet.getRow(actualRow) != null)) {
            //get row
            HSSFRow row = worksheet.getRow(actualRow);
            readRow(row);
            actualRow++;
        }
    }

    private static void readWorkSheetDoubleMixed(HSSFSheet worksheet) {
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
    }
    private static boolean gibtEsSchon(Spieler spieler){
        return true; //wenn es den schon gibt;
    }
    private static Spieler readRow(HSSFRow row) {
        int cellNumber = 0;
        HSSFCell cell = row.getCell(cellNumber);


        int spielerid = auswahlklasse.getSpieler().size()+1;
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
                    System.out.println("C1 " + geschlecht);
                }
                HSSFCell gdatumZelle = row.getCell(12);
                if (gdatumZelle != null) {
                    gDatum = gdatumZelle.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    System.out.println("M1 " + gdatumZelle.getNumericCellValue());
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
                        if (tempVerein.getName() == vereinsname) {
                            verein = tempVerein;
                        } else if (tempVerein.getExtVereinsID() == extVereinsID) {
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
                Spieler neuerSpieler = new Spieler(vName,nName,gDatum,spielerid,geschlecht,rPunkte,verein,extSpielerID);
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
