package com.example.passrateprediction.Data_Preprocessing;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
import weka.filters.unsupervised.attribute.MathExpression;
import weka.filters.unsupervised.attribute.NumericToNominal;

public class DataDiscretization {

    public Instances convertNumericalToNomial(Instances instances) throws Exception {

        Instances toReturn = instances;

        MathExpression mathExpression = new MathExpression();
        String[] mathExpressOptions = new String[2];
        mathExpressOptions[0] = "-E";
        mathExpressOptions[1] = "ifelse(A<50,0,ifelse(A<64,1,ifelse(A<74,2,ifelse(A<85,3,4))))";
        mathExpression.setOptions(mathExpressOptions);
        mathExpression.setInputFormat(instances);
        toReturn = Filter.useFilter(toReturn, mathExpression);

        NumericToNominal numericToNominal = new NumericToNominal();
        numericToNominal.setInputFormat(toReturn);
        toReturn = Filter.useFilter(toReturn, numericToNominal);

        return toReturn;
    }

}
