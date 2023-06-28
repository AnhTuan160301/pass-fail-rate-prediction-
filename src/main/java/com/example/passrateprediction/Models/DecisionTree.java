package com.example.passrateprediction.Models;

import com.example.passrateprediction.Helper.WekaHelper;
import weka.classifiers.trees.J48;
import weka.core.Instances;
public class DecisionTree {

    public void buildDecisionTree(Instances instances, String fileToStored) throws Exception {

        WekaHelper wekaHelper = new WekaHelper();
        J48 decisionTree = new J48();
        String[] options = new String[4];
        options[0] = "-U";
        options[1] = "-M";
        options[2] = "8";
        options[3] = "-B";

        decisionTree.setOptions(options);
        wekaHelper.train(instances, decisionTree);
        wekaHelper.runCrossValidation(instances, decisionTree, 5);
        wekaHelper.serialize(decisionTree, fileToStored);
    }

}
