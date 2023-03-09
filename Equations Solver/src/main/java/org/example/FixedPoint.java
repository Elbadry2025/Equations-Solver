package org.example;

import java.awt.*;

public class FixedPoint {
    private String functionG = "";
    private int iterations;
    private int itDone;
    private double error;
    private int significantDigits;
    private Expression_Evaluator evaluator;
    private double x;


    public FixedPoint(String functionG,double x, int iterations, double error, int significantDigits){
        this.functionG = functionG;
        this.iterations = iterations;
        this.error = error;
        this.significantDigits = significantDigits;
        this.x = x;
        evaluator = new Expression_Evaluator(functionG);
    }
    precisionFinder precisionFinder = new precisionFinder();

    public double fixedPoint(){
        double e =0;
        double temp = 0;
        // System.out.println(functionG);
        int i = 0;
        for(i = 0; i<iterations ; i++){
            if(e < error && i!= 0){
                break;
            }
            temp = x ;
            x = evaluator.evaluate(x) ;
            x= precisionFinder.precision(x, significantDigits);
            e = Math.abs((x-temp)/x);
            System.out.println(i + ":\t" + temp + " " + x + " " + e);
        }
        itDone = i;
        return x;
    }
    public int getItDone() {
        return itDone;
    }
}