package com.example.passrateprediction;

import com.example.passrateprediction.helper.ImportDataset;
import com.example.passrateprediction.helper.Run_Python_File;
import com.spire.xls.Workbook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import weka.core.Instances;

import java.io.IOException;

@SpringBootApplication
public class PassFailRatePredictionApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PassFailRatePredictionApplication.class, args);
		Data data = new Data();
		Instances instances = data.dataInstancesARFF("C:\\Users\\pass-fail-rate-prediction-\\src\\main\\resources\\Dataset\\pivot.arff");
		DataDiscretization dataDiscretization = new DataDiscretization();
		System.out.println(dataDiscretization.convertNumericalToNomial(instances).firstInstance());
	}
}
