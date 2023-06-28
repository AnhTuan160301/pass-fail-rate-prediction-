package com.example.passrateprediction.Models;

import com.example.passrateprediction.Helper.WekaHelper;
import weka.core.Instances;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.Evaluation;
public class Random_Forest {

    public void buildRandomForest(Instances instances, String fileToStored) throws Exception {

        RandomForest rf = new RandomForest();

        String[] parameters = new String[14];
        parameters[0] = "-P";
        parameters[1] = "10";
        parameters[2] = "-I";
        parameters[3] = "10";
        parameters[4] = "-num-slots";
        parameters[5] = "1";
        parameters[6] = "-K";
        parameters[7] = "0";
        parameters[8] = "-M";
        parameters[9] = "1.0";
        parameters[10] = "-V";
        parameters[11] = "0.001";
        parameters[12] = "-S";
        parameters[13] = "1";

        rf.setOptions(parameters);
        WekaHelper wekaHelper = new WekaHelper();
        wekaHelper.train(instances, rf);
        wekaHelper.runCrossValidation(instances, rf, 5);
        wekaHelper.serialize(rf, fileToStored);
    }

}
