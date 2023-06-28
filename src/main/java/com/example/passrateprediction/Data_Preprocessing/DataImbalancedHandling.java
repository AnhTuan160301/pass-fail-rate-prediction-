package com.example.passrateprediction.Data_Preprocessing;

import com.example.passrateprediction.Helper.WekaHelper;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;
import weka.filters.supervised.instance.SMOTE;

public class DataImbalancedHandling {

    public Instances handleBySmote(Instances instances) throws Exception {

        SMOTE smote = new SMOTE();
        smote.setInputFormat(instances);
        smote.setPercentage(200.0);
        Instances toReturnSmoteDataset = Filter.useFilter(instances, smote);
        return toReturnSmoteDataset;
    }

    public Instances handleByROS(Instances instances) throws Exception {

        Resample resample = new Resample();
        resample.setNoReplacement(false);
        resample.setBiasToUniformClass(1.0);
        resample.setInvertSelection(false);
        resample.setSampleSizePercent(157.5);
        resample.setInputFormat(instances);
        Instances toReturnROSDataset = Filter.useFilter(instances, resample);

        return toReturnROSDataset;
    }
}
