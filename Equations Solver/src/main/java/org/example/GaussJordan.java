package org.example;

public class GaussJordan {

    int n, significantDigits;
    boolean enableScaling;
    double[][] coef;
    double[] scale;
    double[] ans;
    precisionFinder precisionFinder = new precisionFinder();

    public GaussJordan(double[][] ceof, boolean enableScaling, int digits) {
        this.coef = ceof;
        this.enableScaling = enableScaling;
        this.n = coef.length;
        this.significantDigits = digits;
        this.scale = new double[n];
        this.ans = new double[n];
    }
    
    private void significantDigits() {
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < n; ++j) {
				coef[i][j] = precisionFinder.precision(coef[i][j], significantDigits);
			}
		}
	}
    private void scaling() {
        if(!enableScaling) {
            for(int i = 0; i < n; i++) {
                scale[i] = 1;
            }
            return;
        }
        for(int i = 0; i < n; i++) {
            scale[i] = Math.abs(coef[i][0]);
            for(int j = 1; j < n; j++) {
                if(Math.abs(coef[i][j]) > scale[i]) {
                    scale[i] = Math.abs(coef[i][j]);
                }
            }
        }
    }

    private void partialPivoting(int k) {
        double pivot = Math.abs(coef[k][k] / scale[k]);
        pivot = precisionFinder.precision(pivot, significantDigits);
        int pivotRow = k;
        for(int i = k + 1; i < n; i++) {
            if(Math.abs(coef[i][k] / scale[i]) > pivot) {
                pivot = Math.abs(coef[i][k] / scale[i]);
                pivot = precisionFinder.precision(pivot, significantDigits);
                pivotRow = i;
            }
        }
        if(pivotRow == k) return;
        for(int j = k; j < n + 1; j++) {
            double tmp = coef[k][j];
            coef[k][j] = coef[pivotRow][j];
            coef[pivotRow][j] = tmp;
        }
    }

    private void elimination() {
        for(int k = 0; k < n; k++) {
            partialPivoting(k);
            for(int i = 0; i < n; i++) {
            	if(i == k) continue;
                double multiplier = coef[i][k] / coef[k][k];
                multiplier = precisionFinder.precision(multiplier, significantDigits);
                for(int j = k; j < n + 1; j++) {
                    coef[i][j] = coef[i][j] - multiplier * coef[k][j];
                    coef[i][j] = precisionFinder.precision(coef[i][j], significantDigits);
                }
            }
        }
    }

    private void substitution() {
        for(int i = 0; i < n; i++) {
            ans[i] = coef[i][n] / coef[i][i];
            ans[i] = precisionFinder.precision(ans[i], significantDigits);
        }
    }

    public double[] solve() {
    	significantDigits();
        scaling();
        elimination();
        substitution();
        return ans;
    }

}
