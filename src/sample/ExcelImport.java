package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.*;

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
            readWorkSheetDouble(worksheet);

            worksheet = workbook.getSheet("Mixed");
            readWorkSheetMixed(worksheet);

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

    private static void readWorkSheetDouble(HSSFSheet worksheet) {
        int actualRow = 1;

        while ((worksheet.getRow(actualRow) != null)) {
            //get first row
            HSSFRow row = worksheet.getRow(actualRow);
            readRow(row);
            actualRow++;

            //get second row
            row = worksheet.getRow(actualRow);
            if (row != null) {
                readRow(row);
            }
            actualRow++;
        }
    }

    private static void readWorkSheetMixed(HSSFSheet worksheet) {

    }

    private static void readRow(HSSFRow row) {
        int cellNumber = 0;
        HSSFCell cell = row.getCell(cellNumber);

        /*while (cell != null) {
            System.out.println(cell.toString());
            cellNumber++;
            cell = row.getCell(cellNumber);
        }*/

        HSSFCell cellB1 = row.getCell(1);
        if (cellB1 != null ) {
            if(cellB1.getStringCellValue() != "") {
                System.out.println("B1 " + cellB1.getStringCellValue());

                HSSFCell cellA1 = row.getCell(0);
                if (cellA1 != null) {
                    System.out.println("A1 " + cellA1.getStringCellValue());
                }
                HSSFCell cellC1 = row.getCell(2);
                if (cellC1 != null) {
                    System.out.println("C1 " + cellC1.getStringCellValue());
                }
                HSSFCell cellD1 = row.getCell(3);
                if (cellD1 != null) {
                    System.out.println("D1 " + cellD1.getStringCellValue());
                }
                HSSFCell cellE1 = row.getCell(4);
                if (cellE1 != null) {
                    System.out.println("E1 " + cellE1.getStringCellValue());
                }
                HSSFCell cellF1 = row.getCell(5);
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
                }
                HSSFCell cellI1 = row.getCell(8);
                if (cellI1 != null) {
                    System.out.println("I1 " + cellI1.getStringCellValue());
                }
                HSSFCell cellJ1 = row.getCell(9);
                if (cellJ1 != null) {
                    System.out.println("J1 " + cellJ1.getStringCellValue());
                }
                HSSFCell cellK1 = row.getCell(10);
                if (cellK1 != null) {
                    System.out.println("K1 " + cellK1.getStringCellValue());
                }
                HSSFCell cellL1 = row.getCell(11);
                if (cellL1 != null) {
                    System.out.println("L1 " + cellL1.getStringCellValue());
                }
                HSSFCell cellM1 = row.getCell(12);
                if (cellM1 != null) {
                    System.out.println("M1 " + cellM1.getNumericCellValue());
                }
                HSSFCell cellN1 = row.getCell(13);
                if (cellN1 != null) {
                    System.out.println("N1 " + cellN1.getNumericCellValue());
                }
                HSSFCell cellO1 = row.getCell(14);
                if (cellO1 != null) {
                    System.out.println("O1 " + cellO1.getNumericCellValue());
                }
                HSSFCell cellP1 = row.getCell(15);
                if (cellP1 != null) {
                    System.out.println("P1 " + cellP1.getStringCellValue());
                }
                HSSFCell cellQ1 = row.getCell(16);
                if (cellQ1 != null) {
                    System.out.println("Q1 " + cellQ1.getStringCellValue());
                }
            }
        }
    }

}
