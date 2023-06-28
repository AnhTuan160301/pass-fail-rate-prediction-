package com.example.passrateprediction.Helper;

import com.example.passrateprediction.Data_Preprocessing.Data;
import com.example.passrateprediction.Data_Preprocessing.DataDiscretization;
import com.example.passrateprediction.Data_Preprocessing.DataImbalancedHandling;
import weka.core.Instances;
import weka.core.converters.CSVSaver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class Preproscessing {


    public HashMap<String, Instances> dataPreprocessing(String filePath) throws Exception {

//        Run_Python_File run_python_file = new Run_Python_File();
//        run_python_file.runPythonScript("C:\\Users\\pass-fail-rate-prediction-\\src\\main\\java\\com\\example\\passrateprediction\\Python\\dataset_score.xlsx");
//        System.out.println("Done");
        Thread.sleep(5000);

        Data data = new Data();
        HashMap<String, Instances> instancesHashMap = new HashMap<>();

        Instances instances = data.dataInstancesCSV(filePath);
        instances.setClassIndex(instances.numAttributes() - 1);
        DataDiscretization dataDiscretization = new DataDiscretization();
        DataImbalancedHandling dataImbalancedHandling = new DataImbalancedHandling();
        instances = dataDiscretization.convertNumericalToNomial(instances);

        Instances rosInstances  = dataImbalancedHandling.handleByROS(instances);
        Instances smoteInstance = dataImbalancedHandling.handleBySmote(instances);

        instances.randomize(new Random(123456));
        rosInstances.randomize(new Random(123456));
        smoteInstance.randomize(new Random(123456));

        instancesHashMap.put("unbalanced", instances);
        instancesHashMap.put("ROS", rosInstances);
        instancesHashMap.put("SMOTE", smoteInstance);

        Thread.sleep(5000);

        return instancesHashMap;

    }

}
