package com.example.passrateprediction.helper;

import com.spire.xls.*;

public class ImportDataset {

    public Workbook importExcelFile(String fileName){
        Workbook workbook=new Workbook();
        //Load from file
        workbook.loadFromFile(fileName);
        return workbook;
    }
    public void convertToCSV(Workbook workbook){
        Worksheet worksheet=workbook.getWorksheets().get("PivotTable");
        for(int i=0;i<worksheet.getRows().length;i++){
            String cellValues=worksheet.getCellRange(i+1,3).getValue();
            if(cellValues==null||cellValues.trim().isEmpty()){
                worksheet.deleteRow(i+1);
            }
        }
        workbook.save();
    }
    public Workbook createPivotTable(String fileName){
        Workbook workbook = importExcelFile(fileName);
        Worksheet sheet = workbook.getWorksheets().get(0);
        sheet.setName("DataSource");

        Worksheet resultSheet = workbook.createEmptySheet();
        resultSheet.setName("PivotTable");

        //Select the data source range
        CellRange dataRange = sheet.getCellRange("C1:AC11617");
        PivotCache pivotCache = workbook.getPivotCaches().add(dataRange);

        //Add pivot table to the worksheet and set the location and cache of it
        PivotTable pt=resultSheet.getPivotTables().add("PivotTable",sheet.getRange().get("A1"),pivotCache);

        //Define the row labels
        PivotField pf=null;
        if(pt.getPivotFields().get("MaSV") instanceof PivotField){
            pf=(PivotField)pt.getPivotFields().get("MaSV");
        }
        pf.setAxis(AxisTypes.Row);

        PivotField pf2=null;
        if(pt.getPivotFields().get("TenMH") instanceof PivotField){
            pf2=(PivotField)pt.getPivotFields().get("TenMH");
        }
        pf2.setAxis(AxisTypes.Column);

        //Add data to the value field
        pt.getDataFields().add(pt.getPivotFields().get("DiemHP"),"Scores",SubtotalTypes.Product);
        //Set pivot table style
        pt.setBuiltInStyle(PivotBuiltInStyles.PivotStyleMedium12);
        pt.calculateData();
        resultSheet.getAllocatedRange().autoFitColumns();
        resultSheet.getAllocatedRange().autoFitRows();
        //Save the document
        String resultFileName="src\\main\\resources\\Dataset\\CreatePivotTable.xlsx";
        workbook.saveToFile(resultFileName,ExcelVersion.Version2013);
        return workbook;
    }
}
