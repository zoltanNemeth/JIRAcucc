package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.params.provider.Arguments;


public class ExcelReader {
    String  basePath;

    public ExcelReader(String basePath) {
        this.basePath = basePath;
    }


    /**
     * A method that returns test data intended to be uses in parameterized test.
     * @return a 2d-array containing test data.
     */


    public ArrayList getSheet(String filename, String sheetName) {
        DataFormatter myDataFormatter = new DataFormatter();
        Sheet excelSheet = null;
        String fullPath = "./src/main/resources/" + filename + ".xlsx";
        ArrayList<Map> sheetData = new ArrayList<>();
        try(Workbook excelFile = WorkbookFactory.create(new File(fullPath),null,true)) {
            excelSheet = excelFile.getSheet(sheetName);
            ArrayList<String> header = new ArrayList<String>();
            for(Row row: excelSheet){
                Map<String, String> fields = new HashMap<String, String>();
                int columnNumber = 0;
                for (Cell cell : row) {
                    if(row.getRowNum()==0) {
                        header.add(myDataFormatter.formatCellValue(cell));
                    } else {
                        fields.put(header.get(columnNumber), myDataFormatter.formatCellValue(cell));
                    }
                    columnNumber += 1;
                }
                sheetData.add(fields);
            }
            return sheetData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheetData;
    }

    /**
     * Method that searches through a given sheet in an Excel-file and returns the data
     * @param excelFullPath full path to excel-file including file name.
     * @param sheetName the name of the sheet where to extract data.
     * @return test data as Stream&lt;Arguments&gt;
     */
    public Stream<Arguments> getTestDataStreamFromExcelFile(String fileName,
                                                                   String sheetName){
        String excelFullPath = basePath + fileName;
        Stream<Arguments> returnStream = Stream.empty();
        DataFormatter myDataFormatter = new DataFormatter();

        try(Workbook excelFile = WorkbookFactory.create(new File(excelFullPath),null,true)) {
            Sheet excelSheet1 = excelFile.getSheet(sheetName);

            for(Row row: excelSheet1){
                if(row.getRowNum()==0) {continue;}
                ArrayList<Object> rowArrayList = new ArrayList<>();
                for (Cell cell : row) {
                    rowArrayList.add(myDataFormatter.formatCellValue(cell));
                }
                Arguments arguments = Arguments.of(rowArrayList.toArray());
                Map<String, String> map = new HashMap<String, String>();

                // Mapping int values to string keys
                map.put("Geeks", "cica");
                arguments = Arguments.of(map);

                returnStream = Stream.concat(returnStream,Stream.of(arguments));
            }
            return returnStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnStream;
    }

    public ArrayList<Map> getArrayListOfMap(String fileName,
                                                            String sheetName){
        String excelFullPath = basePath + fileName;
        ArrayList<Map> returnArray = new ArrayList<Map>();
        DataFormatter myDataFormatter = new DataFormatter();

        try(Workbook excelFile = WorkbookFactory.create(new File(excelFullPath),null,true)) {
            Sheet excelSheet1 = excelFile.getSheet(sheetName);
            ArrayList<String> header = new ArrayList<String>();
            // Get header
            for (Cell cell : excelSheet1.getRow(0)) {
                header.add(myDataFormatter.formatCellValue(cell));
            }
            // Get fields
            for(Row row: excelSheet1){

                Map<String, String> fields = new HashMap<String, String>();

                for (int columnNumber = 0; columnNumber < header.size(); columnNumber++) {
                    if (myDataFormatter.formatCellValue(excelSheet1.getRow(0).getCell(columnNumber)).isEmpty()) {continue;}
                    if(row.getRowNum()!=0) {
                        if (!myDataFormatter.formatCellValue(row.getCell(columnNumber)).isEmpty()) {
                            fields.put(header.get(columnNumber), myDataFormatter.formatCellValue(row.getCell(columnNumber)));
                        } else {
                            fields.put(header.get(columnNumber), null);
                        }

                    }

                }

                if(row.getRowNum()!=0) {
                    returnArray.add(fields);
                }
            }
            return returnArray;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnArray;
    }

    public Stream<Arguments> getStreamOfMapArray(String fileName,
                                                 String sheetName){
        String excelFullPath = basePath + fileName;
        Stream<Arguments> returnStream = Stream.empty();
        DataFormatter myDataFormatter = new DataFormatter();

        try(Workbook excelFile = WorkbookFactory.create(new File(excelFullPath),null,true)) {
            Sheet excelSheet1 = excelFile.getSheet(sheetName);
            ArrayList<String> header = new ArrayList<String>();
            for(Row row: excelSheet1){

                Map<String, String> fields = new HashMap<String, String>();
                int columnNumber = 0;
                for (Cell cell : row) {
                    if (myDataFormatter.formatCellValue(excelSheet1.getRow(0).getCell(columnNumber)).isEmpty()) {continue;}

                    if(row.getRowNum()==0) {
                        header.add(myDataFormatter.formatCellValue(cell));
                    } else  {
                        fields.put(header.get(columnNumber), myDataFormatter.formatCellValue(cell));
                    }
                    columnNumber += 1;
                }
                if(row.getRowNum()!=0) {
                    Arguments arguments = Arguments.of(fields);
                    returnStream = Stream.concat(returnStream,Stream.of(arguments));
                }

            }
            return returnStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnStream;
    }


    /**
     * A method that returns test data intended to be uses in parameterized test.
     * @return a 2d-array containing test data.
     */
    static String[][] testValues2() {
        return getTestData2dArrayFromExcelFile("./src/test/resources/excel-filer/testdata2.xlsx",
                "Sheet1");
    }


    /**
     * Method that searches through a given sheet in an Excel-file and returns the data
     * @param excelFullPath full path to excel-file including file name.
     * @param sheetName the name of the sheet where to extract data.
     * @return String[][] containing the data
     */
    public static String[][] getTestData2dArrayFromExcelFile(String excelFullPath, String sheetName){
        DataFormatter myDataFormatter = new DataFormatter();

        try(Workbook excelFile = WorkbookFactory.create(
                new File(excelFullPath),null,true)) {

            Sheet excelSheet1 = excelFile.getSheet(sheetName);
            int firstRow = excelSheet1.getFirstRowNum();
            int lastRow = excelSheet1.getLastRowNum();
            String[][] testData2dArray = new String[lastRow][];

            //skipping the title row in the sheet
            for (int i = firstRow+1; i <= lastRow; i++) {
                Row row = excelSheet1.getRow(i);
                int firstCellNum = row.getFirstCellNum();
                int lastCellNum = row.getLastCellNum();
                String[] testDataArray = new String[lastCellNum];

                for (int j = firstCellNum; j < lastCellNum; j++) {
                    testDataArray[j]=myDataFormatter.formatCellValue(row.getCell(j));
                }
                testData2dArray[i-1]=testDataArray;
            }

            return testData2dArray;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





}
