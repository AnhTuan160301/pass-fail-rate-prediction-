package com.example.passrateprediction.Models;

import com.example.passrateprediction.Helper.WekaHelper;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.RBFKernel;
import weka.core.Instances;

public class SVM {

    public void buildSVM(Instances instances, String fileToStored) throws Exception {

        SMO svm = new SMO();
        WekaHelper wekaHelper = new WekaHelper();

        String[] options = new String[2];
        options[0] = "-C";
        options[1] = "1.8";
        svm.setOptions(options);


        wekaHelper.train(instances, svm);
        wekaHelper.runCrossValidation(instances, svm, 5);
        wekaHelper.serialize(svm, fileToStored);
    }
}
