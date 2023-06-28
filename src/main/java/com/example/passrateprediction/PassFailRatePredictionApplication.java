package com.example.passrateprediction;

import com.example.passrateprediction.Helper.Preproscessing;
import com.example.passrateprediction.Helper.WekaHelper;
import com.example.passrateprediction.Models.DNN.DNN;
import com.example.passrateprediction.Models.DNN.DNN_functions;
import com.example.passrateprediction.Models.DecisionTree;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.api.DataSet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import weka.core.Instances;

import java.util.HashMap;

@SpringBootApplication
public class PassFailRatePredictionApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PassFailRatePredictionApplication.class, args);

		Preproscessing preproscessing = new Preproscessing();
		HashMap<String, Instances> instancesHashMap = new HashMap<>();
		instancesHashMap = preproscessing.dataPreprocessing("C:\\Users\\pass-fail-rate-prediction-\\src\\main\\resources\\Dataset\\test.csv");
		WekaHelper wekaHelper = new WekaHelper();
		wekaHelper.csvStored(instancesHashMap.get("unbalanced"),"C:\\Users\\pass-fail-rate-prediction-\\src\\main\\resources\\Dataset\\test_DNN_1.csv");
		Thread.sleep(5000);
//		Random_Forest random_forest = new Random_Forest();
//		random_forest.buildRandomForest(instances);
//
//		DecisionTree decisionTree = new DecisionTree();
//		decisionTree.buildDecisionTree(instancesHashMap.get("ROS"),"C:\\Users\\pass-fail-rate-prediction-\\src\\main\\resources\\models\\decision_trees\\DT_model.model" );
//
//		SVM svm = new SVM();
//		svm.buildSVM(instances);
//
//		DNN_functions dnn_functions = new DNN_functions();
//		DataSet dataSet = dnn_functions.readCSVDataset("C:\\Users\\pass-fail-rate-prediction-\\src\\main\\resources\\Dataset\\fileForDNN_1.csv");
//		DNN dnn = new DNN();
//		dnn.buildNeuralNetwork((org.nd4j.linalg.dataset.DataSet) dataSet);
//
		DNN_functions dnn_functions = new DNN_functions();
		MultiLayerNetwork model = dnn_functions.loadDNNModel("C:\\Users\\pass-fail-rate-prediction-\\src\\main\\resources\\models\\DNN\\DNN_model.zip");
		dnn_functions.predict(model,"C:\\Users\\pass-fail-rate-prediction-\\src\\main\\resources\\Dataset\\test_DNN_1.csv");

//		wekaHelper.predictPredictFromModel("C:\\Users\\pass-fail-rate-prediction-\\src\\main\\resources\\models\\decision_trees\\DT_model.model",instancesHashMap.get("unbalanced"));

	}
}
