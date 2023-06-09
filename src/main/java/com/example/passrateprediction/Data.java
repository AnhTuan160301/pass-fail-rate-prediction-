package com.example.passrateprediction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class Data {

    private static Data myData;

    public static Data getData(){
        if ( myData == null ) {
            myData = new Data();
        }
        return myData;
    }

    /**
     * Get the data instances from the arff file*
     * @param filePath
     * @return
     */
    public Instances dataInstancesARFF(String filePath){

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found "+filePath);
        }

        Instances data = null;
        try {
            data = new Instances(fileReader);
        } catch (IOException e) {
            System.out.println("ERROR: Check data inside the file: "+ filePath);
        } catch (NullPointerException e) {
            System.out.println("ERROR: File is missing.");
        }

        try {
            fileReader.close();
        } catch (IOException e) {
            System.out.println("ERROR: Closing the file.");
        }
        data.randomize(new Random(42));

        if (data.attribute("class") != null){
            data.setClassIndex(data.attribute("class").index());
        } else {
            data.setClassIndex(data.numAttributes()-1);
        }

        return data;
    }

    /**
     * Get Data instances from CSV file*
     * @param filePath
     * @return
     */
    public Instances dataInstancesCSV(String filePath){

        CSVLoader loader = new CSVLoader();
        try{
            loader.setSource(new File(filePath));
        }catch (IOException e){
            System.out.println("ERROR: File not found "+filePath);
        }
        Instances data = null;
        try {
            data = loader.getDataSet();
        } catch (IOException e) {
            System.out.println("ERROR: Check data inside the file: "+ filePath);
        } catch (NullPointerException e) {
            System.out.println("ERROR: File is missing.");
        }

        data.randomize(new Random(42));
        if (data.attribute("class") != null){
            data.setClassIndex(data.attribute("class").index());
        } else {
            data.setClassIndex(data.numAttributes()-1);
        }
        return data;
    }
}
