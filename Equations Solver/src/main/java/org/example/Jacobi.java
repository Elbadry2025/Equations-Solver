package org.example;

import java.util.Arrays;
import java.lang.Math;
public class Jacobi {
    int n, significantDigits;
    int iterations;
    int iterationsDone;
    double[][] coef;
    double[] ans;
    double[] Guess;
    double relativeE;
    precisionFinder precisionFinder = new precisionFinder();
    
    public Jacobi(double[][] ceof , int iterations, int digits, double[] Guess, double relativeE) {
        this.coef = ceof;
        this.n = coef.length;
        this.significantDigits = digits;
        this.iterations = iterations;
        this.ans = new double[n];
        this.Guess = Guess;
        this.relativeE = relativeE;
    }
    
    private void significantDigits() {
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < n; ++j) {
				coef[i][j] = precisionFinder.precision(coef[i][j], significantDigits);
			}
		}
	}
    
  
    public double[] solve() {
    	double[] temp = new double[n];
    	significantDigits();
    	for(int i=0 ; i<n ;i++) {
    		ans[i]= Guess[i];
    		ans[i] =  precisionFinder.precision(ans[i], significantDigits);
    	}
    	for(int i=0; i<iterations ; i++) {
    		if(i != 0) {
    			boolean LessThanRError = true;
    			for(int e=0 ; e<n ; e++) {
    				if(Math.abs((ans[e]-temp[e])/ans[e]) < relativeE ){
    					LessThanRError = true;
    				}else {
    					LessThanRError = false;
    					break;
    				}
    			}
    			if(LessThanRError == true) {
    				iterationsDone = i;
    				break;
    			}
    		}
    		
    		for(int f=0 ; f<n ; f++) {
    			temp[f] = ans[f];
    		}
    		for(int j=0 ; j<n ;j++) {
    			double sum = coef[j][n];
    			for(int k=0 ; k<n ; k++) {
    				if(k!=j) {
    					sum-= coef[j][k]*temp[k];
    					sum = precisionFinder.precision(sum, significantDigits);
    				}
    			}
    			ans[j]= sum/coef[j][j];
    			ans[j] = precisionFinder.precision(ans[j], significantDigits);
    		}
    		iterationsDone = i+1;
    	}
    	return ans;
    }
    public int itDone() {
    	return iterationsDone;
    }
}
