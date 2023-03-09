package org.example;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.awt.*;

public class Expression_Evaluator {
    String expression = "";

    public Expression_Evaluator(String expression) {
        this.expression = expression;
    }

    public double evaluate(double val) {
        Expression e = new ExpressionBuilder(this.expression)
                .variables("x")
                .build()
                .setVariable("x", val);
        double result = e.evaluate();
        return result;
    }
    public double der(double val) {
       double result = (this.evaluate(val + 10e-10) - this.evaluate(val)) / 10e-10;
       return result;
    }

}
