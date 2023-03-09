package org.example;

public class NewtonRaphson {

    private double error;
    private int iterations;
    private int itDone;
    private double Xo;
    private double ans;
    private int significantDigits;
    private precisionFinder precisionFinder = new precisionFinder();
    private Expression_Evaluator expression_evaluator;

    public NewtonRaphson(String expression,double error,int iterations,double Xo,int significantDigits) {
        this.expression_evaluator = new Expression_Evaluator(expression);
        this.error=error;
        this.iterations=iterations;
        this.Xo=Xo;
        this.significantDigits=significantDigits;
    }

    public void MainMethod(){
        double er=100;
        int cnt=0;
        double xn,xp=Xo;
        while( cnt<iterations && er>this.error ){
            double fx=precisionFinder.precision(expression_evaluator.evaluate(xp), significantDigits);
            double fxd=precisionFinder.precision(expression_evaluator.der(xp), significantDigits);
            xn=xp-(fx)/(fxd);
            xn= precisionFinder.precision(xn, significantDigits);
            er=Math.abs((xn-xp)/xn);
            xp=xn;
            this.ans=xn;
            System.out.println(cnt + ":\t" + " " + xp + " " + fx + " " + xn + " " + er);
            cnt++;
        }
        this.itDone = cnt;
    }
    public int getItDone() {
        return this.itDone;
    }
    public double Solve() {
        MainMethod();
        System.out.println(ans + " newton");
        return this.ans;
    }
}