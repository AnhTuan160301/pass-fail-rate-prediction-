package com.example.passrateprediction.Models.DNN;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;

import java.io.File;
import java.io.IOException;

public class DNN_functions {

    public DataSet readCSVDataset(String filename) throws IOException, InterruptedException {
        RecordReader rr = new CSVRecordReader(1, ',');
        rr.initialize(new FileSplit(new File(filename)));

        DataSetIterator iterator = new RecordReaderDataSetIterator(
                rr, 120, 11, 2);
        DataSet dataSet =  iterator.next();
        dataSet.shuffle();

        return dataSet;
    }

    public void dataNormalize(DataSet dataSet){
        DataNormalization normalizer = new NormalizerStandardize();
        normalizer.fit(dataSet);
        normalizer.transform(dataSet);
    }

    public DataSet readCSVFile(String filePath, int batchSize) throws IOException, InterruptedException {
        RecordReader rr = new CSVRecordReader(1, ',');
        rr.initialize(new FileSplit(new File(filePath)));

        DataSetIterator iterator = new RecordReaderDataSetIterator(
                rr, batchSize, 11, 2);

        DataSet dataSet =  iterator.next();
//        dataSet.shuffle();

        return dataSet;
    }

    public void saveDNNModel(MultiLayerNetwork model, String filePath){
        File locationToSave = new File(filePath);
        boolean saveUpdater = false;
        try{
            ModelSerializer.writeModel(model, locationToSave, saveUpdater);
        }catch (IOException e){
            System.out.println("Can not save the model: " + e.toString());
        }

    }

    public MultiLayerNetwork loadDNNModel(String filePath){
        File locationToSave = new File(filePath);
        MultiLayerNetwork model = null;
        try{
            model = ModelSerializer.restoreMultiLayerNetwork(locationToSave);
        }catch (IOException e){
            System.out.println("Cannot Load the Model: " + e.toString());
        }
        return model;

    }

    public void predict(MultiLayerNetwork model, String filePath) throws IOException, InterruptedException {
        DataSet ds = readCSVFile(filePath, 10);
        dataNormalize(ds);
        Evaluation evaluation = new Evaluation(2);
        for(int i = 0; i <=0; i++){
            INDArray output = model.output(ds.getFeatures());
            INDArray label = ds.getLabels();
            evaluation.eval(label, output);
        }
        System.out.println(evaluation.stats());
        System.out.println(evaluation.getConfusion());
        System.out.println(evaluation.f1(0));
        System.out.println(evaluation.f1(1));

    }





}
