package org.example;


public class Solve2 {

    private String method = "Gauss Elimination";
    String expression;
    double ans;
    double Xo;
    double Xi;
    double Xl;
    double Xu;
    int significantDigits;
    int it;
    double er;
    int itDone;
    private boolean converge = true;
    private Expression_Evaluator expression_evaluator;

    public Solve2(String method, String expression, int digits, double Xo, double Xi, double Xl, double Xu,  int it, double er) {
        this.method = method;
        this.expression = expression;
        this.significantDigits = digits;
        this.Xo = Xo;
        this.Xi = Xi;
        this.Xl = Xl;
        this.Xu = Xu;
        this.it = it;
        this.er = er;
        this.expression_evaluator = new Expression_Evaluator(expression);
    }

    public double chooseMethod() {
        switch(method) {
            case "Bisection" : {
                bisection obj = new bisection(it, er, Xl, Xu, expression, significantDigits);
                ans = obj.Solve();
                itDone = obj.getItDone();
                checkConvergence();
                break;
            }
            case "False-Position" : {
                falsePosition obj = new falsePosition(it, er, Xl, Xu, expression, significantDigits);
                ans = obj.Solve();
                itDone = obj.getItDone();
                checkConvergence();
                break;
            }
            case "Fixed Point" : {
                FixedPoint obj = new FixedPoint(expression, Xo, it, er, significantDigits);
                ans = obj.fixedPoint();
                itDone = obj.getItDone();
                break;
            }
            case "Newton-Raphson" : {
                NewtonRaphson obj = new NewtonRaphson(expression, er, it, Xo, significantDigits);
                ans = obj.Solve();
                itDone = obj.getItDone();
                checkConvergence();
                break;
            }
            case "Secant" : {
                Secant obj = new Secant(expression, er, it, Xi, Xo, significantDigits);
                ans = obj.Solve();
                itDone = obj.getItDone();
                checkConvergence();
                break;
            }
        }
        System.out.println(ans + " solve2");
        return ans;
    }

    public void checkConvergence() {
        double result = this.expression_evaluator.evaluate(this.ans);
        System.out.println(result + " converge");
        if(Math.abs(result) >= this.er)
            this.converge = false;
    }
    public int Iterations() {
        return itDone;
    }

    public boolean getConverge() {
        return converge;
    }

}


//x = expression
//f = x - expression
