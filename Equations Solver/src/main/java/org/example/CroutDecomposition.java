package org.example;

public class CroutDecomposition {
    private int n, significantDigits;
    private boolean enableScaling, error;
    private double[][] coef, lower, upper;

    private double[] b;
    private double[] scale;
    private int[] o;
    private double[] ans;
    precisionFinder precisionFinder = new precisionFinder();

    CroutDecomposition(double[][] coef, boolean enableScaling, int digits){
        this.coef = coef;
        this.enableScaling = enableScaling;
        this.n = coef.length;
        this.significantDigits = digits;
        this.b = new double[n];
        this.scale = new double[n];
        this.o = new int[n];
        this.ans = new double[n];
        this.error = false;
        lower = new double[n][n];
        upper = new double[n][n];
    }

    private void significantDigits() {
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < n + 1; ++j) {
				coef[i][j] = precisionFinder.precision(coef[i][j], significantDigits);
			}
		}
	}
    
    public void initiate(){
    	for(int i=0; i<n; i++) {
    		b[i] = coef[i][n];
    	}
        for(int i =0; i <n; i++){
            for(int j=0; j<n; j++){
                lower[i][j] = 0;
                upper[i][j] = 0;
            }
        }
        for(int i=0; i<n; i++){
            upper[i][i] = 1;
        }
    }

    public void pivot(int k){
        int p = k;
        double big = Math.abs(coef[o[k]][k] / scale[o[k]]);
        big = precisionFinder.precision(big, significantDigits);
        for(int i = k + 1; i < n; ++i){
            double dummy1 = Math.abs(coef[o[i]][k] / scale[o[i]]);
            dummy1 = precisionFinder.precision(dummy1, significantDigits);
            if(dummy1 > big){
                big = dummy1;
                p = i;
            }
        }
        int dummy2 = o[p];
        o[p] = o[k];
        o[k] = dummy2;
        double[] dummy3 = coef[p];
        coef[p] = coef[k];
        coef[k] = dummy3;
    }

    private void getLower_Upper(){
        initiate();
        if(!enableScaling){
            for(int i = 0; i < n; ++i) {
                o[i] = i;
                scale[i] = 1;
            }
        }else{
            for(int i = 0; i < n; ++i){
                o[i] = i;
                scale[i] = Math.abs(coef[i][0]);
                for(int j = 1; j < n; ++j){
                    if(Math.abs(coef[i][j]) > scale[i]){
                        scale[i] = Math.abs(coef[i][j]);
                    }
                }
            }
        }
        pivot(0);
        for (int j = 0; j < n; j++) {
            double sum;
            for (int i = j; i < n; i++) {
                sum = 0;
                for (int k = 0; k < j; k++) {
                    sum += lower[i][k] * upper[k][j];
                    sum = precisionFinder.precision(sum, significantDigits);
                }
                lower[i][j] = coef[i][j] - sum;
                lower[i][j] = precisionFinder.precision(lower[i][j], significantDigits);
            }
            for (int i = j; i < n; i++) {
                sum = 0;
                for (int k = 0; k < j; k++) {
                    sum += lower[j][k] * upper[k][i];
                    sum = precisionFinder.precision(sum, significantDigits);
                }
                if (lower[j][j] != 0) {
                    upper[j][i] = (coef[j][i] - sum) / lower[j][j];
                    upper[j][i] = precisionFinder.precision(upper[j][i], significantDigits);
                }else
                    System.out.println(0);
            }
        }
        if(Math.abs(coef[o[n - 1]][n - 1]) / scale[o[n - 1]] < 1e-9){
            error = true;
        }
    }
    public void Substitute(){
        double[] y = new double[n];
        //forward substitution
        for(int i = 0; i < n; ++i){
            double sum = b[o[i]];
            for(int j = 0; j < i; ++j){
                sum = sum - lower[i][j] * y[j];
                sum = precisionFinder.precision(sum, significantDigits);
            }
            y[i] = sum/lower[i][i];
            y[i] = precisionFinder.precision(y[i], significantDigits);
        }
        //backward substitution
        ans[n - 1] = y[n - 1] ;
        for(int i = n - 2; i >= 0; --i){
            double sum = 0;
            for(int j = i + 1; j < n; ++j){
                sum = sum + upper[i][j] * ans[j];
                sum = precisionFinder.precision(sum, significantDigits);
            }
            ans[i] = (y[i] - sum);
            ans[i] = precisionFinder.precision(ans[i], significantDigits);
        }
    }
    public double[] Solve(){
    	significantDigits();
        getLower_Upper();
        Substitute();
        return ans;
    }

   
}
