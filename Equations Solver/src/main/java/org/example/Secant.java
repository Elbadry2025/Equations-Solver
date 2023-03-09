package org.example;

public class Secant {

    private double error;
    private int iterations;
    private int itDone;
    private double Xo;
    private double Xi;
    private double ans;
    private int significantDigits;
    private precisionFinder precisionFinder = new precisionFinder();
    private Expression_Evaluator expression_evaluator;

    public Secant(String expression,double error,int iterations,double Xi, double Xo,int significantDigits) {
        this.expression_evaluator = new Expression_Evaluator(expression);
        this.error = error;
        this.iterations = iterations;
        this.Xi = Xi;
        this.Xo = Xo;
        this.significantDigits = significantDigits;
    }

    public void MainMethod() {
        /* double[] x = new double[this.iterations];
        double[] f = new double[this.iterations];
        x[0] = Xi;
        x[1] = Xo;
        f[0] = expression_evaluator.evaluate(x[0])
        for(int i = 1; i <= 10; i++) {
            f[i] = precision(f(x[i]), 5);
            x[i+1] = x[i] - f[i] * (x[i] - x[i-1]) / (f[i] - f[i-1]);
            x[i+1] = precision(x[i+1], 5);
            er = Math.abs(100*(x[i+1] - x[i])/x[i+1]);
            er = precision(er, 5);
            System.out.println(i + ": " + x[i-1] + " " + x[i] + " " + f[i-1] + " " + f[i] + " " + x[i+1] + " " + er);
        }
         */
        double er=100;
        int cnt=0;
        double xn,xi=Xi,xp=Xo;
        while( cnt<iterations && er>this.error ){
            double fxi=precisionFinder.precision(expression_evaluator.evaluate(xi), significantDigits);
            double fx=precisionFinder.precision(expression_evaluator.evaluate(xp), significantDigits);
            xn=xp - fx * (xp - xi) / (fx - fxi);
            xn= precisionFinder.precision(xn, significantDigits);
            er=Math.abs((xn-xp)/xn);
            xi=xp;
            xp=xn;
            this.ans=xn;
            System.out.println(cnt + ":\t" + xi + " " + xp + " " + fxi + " " + fx + " " + xn + " " + er);
            cnt++;
        }
        this.itDone = cnt;
    }
    public int getItDone() {
        return this.itDone;
    }
    public double Solve() {
        MainMethod();
        return this.ans;
    }
//    public double[][] getSteps() {
//        double[][] steps = new double[2][this.iterations];
//        steps[0] = x;
//        steps[1] = f;
//        return steps;
//    }
}