package com.example.passrateprediction.Helper;

import org.springframework.stereotype.Component;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToNominal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

@Component
public class WekaHelper {

    public void writeArffFile(Instances instances, String outPath) {
        try {
            ArffSaver saver = new ArffSaver();
            saver.setInstances(instances);
            saver.setFile(new File(outPath));
            saver.writeBatch();
        } catch (IOException e) {
            System.out.println("Error while writing data to file.");
        } catch (Exception e) {
            System.out.println("Error while transforming data from non-sparse to sparse.");
        }
    }

    public Instances convertStringToNominal(Instances instances, String range) {
        try {
            StringToNominal filter = new StringToNominal();
            filter.setAttributeRange(range);
            filter.setInputFormat(instances);
            instances = Filter.useFilter(instances, filter);
        } catch (Exception e) {
            System.out.println("Error while converting attribute from string to nominal.");
        }
        return instances;
    }

    public void train(Instances instances, Classifier classifier) {

        try {
            classifier.buildClassifier(instances);
        } catch (Exception e) {
            System.out.println("Error while training classifier");
        }
    }

    public void predict(Instances instances, Instances dataset,
                        Classifier classifier) {

        try {
            for (int i = 0; i < dataset.numInstances(); i++) {
                int prediction = (int) classifier.classifyInstance(dataset
                        .instance(i));
                String label = instances.classAttribute().value((prediction));
                System.out.print(label + " ");
            }
        } catch (Exception e) {
            System.out.println("Error while running classifier.");
        }
    }

    public void runCrossValidation(Instances instances, Classifier classifier,
                                   int folds) {

        try {
            Evaluation eval = new Evaluation(instances);
            eval.crossValidateModel(classifier, instances, folds, new Random(1));

            System.out.println(eval.toSummaryString());
            System.out.println(eval.toClassDetailsString());
            System.out.println(eval.toMatrixString());

        } catch (Exception e) {
            System.out.println("Erorr while running cross-validation.");
        }
    }

    /**
     * Serializes a model.
     *
     * @param classifier the trained classifier to be serialized
     * @param path the location of the output file
     */
    public void serialize(Classifier classifier, String path) {

        try {
            SerializationHelper.write(path, classifier);
        } catch (Exception e) {
            System.out.println("Error while serializing classifier.");
        }
    }

    /**
     * Deserializes a model.
     *
     * @param path the location of the output file
     * @return the trained classifier
     */
    public Classifier deserialize(String path) {

        Classifier classifier = null;
        try {
            classifier = (Classifier)SerializationHelper.read(path);
        } catch (Exception e) {
            System.out.println("Error while deserializing classifier");
        }

        return classifier;
    }

    public void predictPredictFromModel(String modelPath, Instances testInstances){
        try{
            Classifier classifier = deserialize(modelPath);
            System.out.println("ActualClass \t ActualValue \t PredictedValue \t PredictedClass");
            for (int i = 0; i < testInstances.numInstances(); i++) {
                HashMap<Double, String> classValues = new HashMap<>();
                classValues.put(1.0, "pass");
                classValues.put(0.0, "fail");
                String act = testInstances.instance(i).stringValue(testInstances.instance(i).numAttributes() - 1);
                double actual = testInstances.instance(i).classValue();
                Instance inst = testInstances.instance(i);
                double predict = classifier.classifyInstance(inst);
                String pred = classValues.get(predict);
                System.out.println(act + " \t\t " + actual + " \t\t " + predict + " \t\t " + pred);
            }
        }
        catch(Exception e){
            System.out.println("Error!!!!\n" + e.getMessage());
        }
    }

    public void csvStored(Instances instances, String filePath) throws IOException {
        CSVSaver csvSaver = new CSVSaver();
        csvSaver.setInstances(instances);
        csvSaver.setFile(new File(filePath));
        csvSaver.writeBatch();
    }
}
