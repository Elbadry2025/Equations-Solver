package org.example;

import java.awt.*;

public class falsePosition {
    int significantDigits;
    double xl , xu ;      //xl -> lower bound -- xu -> upper bound
    double xr;
    double yl , yu, yr;
    int k;                //k -> no of iterations.
    int itDone;
    double error, prev;
    precisionFinder precisionFinder = new precisionFinder();
    Expression_Evaluator exp;

    public falsePosition(int iterations, double eps, double lower, double upper, String expression, int digits){
        this.xl = lower;
        this.xu = upper;
        this.k = iterations;
        this.error = eps;
        this.exp = new Expression_Evaluator(expression);
        this.significantDigits =  digits;
    }

    private void significantDigits() {
        xl = precisionFinder.precision(xl, significantDigits);
        xu = precisionFinder.precision(xu, significantDigits);
        xr = precisionFinder.precision(xr, significantDigits);
        yl = precisionFinder.precision(yl, significantDigits);
        yu = precisionFinder.precision(yu, significantDigits);
        yr = precisionFinder.precision(yr, significantDigits);
        error = precisionFinder.precision(error, significantDigits);
    }
    double Solve(){
        significantDigits();
        xr = 0;
        int i;
        yl = exp.evaluate(xl);
        yu = exp.evaluate(xu);
        if(yl*yu > 0){
            return -1;
        }
        for(i=0; i<k; i++){
            if(Math.abs((xr-prev)/xr) <= error){
                System.out.println(xr);
                break;
            }
            prev = xr;
            yl = exp.evaluate(xl);
            yu = exp.evaluate(xu);
            xr = (xl*yu-xu*yl)/(yu-yl);
            yr = exp.evaluate(xr);
            if(yr * yl <0){
                xu = xr;
                yu = yr;
            }else if(yr * yl >0){
                xl = xr;
                yl = yr;
            }else{
                System.out.println(xr);
                break;
            }
            significantDigits();
            System.out.println(i + ":\t" + xl + " " + xu + " " + xr + " " + yl + " " + yu + " " + yr + " " + error);
        }
        itDone = i;
        return xr;
    }
    int getItDone() {
        return itDone;
    }
}