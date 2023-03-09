package org.example;

public class DoolittleLU {
    private int n, significantDigits;
    private boolean enableScaling, error;
    private double[][] coef;
    private double[] b;
    private double[] scale;
    private int[] o;
    private double[] ans;
    precisionFinder precisionFinder = new precisionFinder();

    DoolittleLU(double[][] coef, double[] b, boolean enableScaling, int digits){
        this.coef = coef;
        this.b = b;
        this.enableScaling = enableScaling;
        this.n = coef.length;
        this.significantDigits = digits;
        this.scale = new double[n];
        this.o = new int[n];
        this.ans = new double[n];
        this.error = false;
    }

    private void significantDigits() {
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < n; ++j) {
				coef[i][j] = precisionFinder.precision(coef[i][j], significantDigits);
			}
			b[i] = precisionFinder.precision(b[i], significantDigits);
		}
	}
    
    public void Decompose(){
        // scaling
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

        for(int k = 0; k < n - 1; ++k){
            //pivoting and checking for singularity
            pivot(k);
            if(Math.abs(coef[o[k]][k] / scale[o[k]]) < 1e-9){
                error = true;
                return;
            }
            //if not singular, continue
            for(int i = k + 1; i < n; ++i){
                double factor = coef[o[i]][k] / coef[o[k]][k];
                factor = precisionFinder.precision(factor, significantDigits);
                coef[o[i]][k] = factor;
                //elimination
                for(int j = k + 1; j < n; ++j){
                    coef[o[i]][j] = coef[o[i]][j] - factor * coef[o[k]][j];
                    coef[o[i]][j] = precisionFinder.precision(coef[o[i]][j], significantDigits);
                }
            }
        }
        //re-checking for singularity
        if(Math.abs(coef[o[n - 1]][n - 1]) / scale[o[n - 1]] < 1e-9){
            error = true;
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
    }

    public void Substitute(){
        double[] y = new double[n];
        //forward substitution
        y[o[0]] = b[o[0]];
        for(int i = 1; i < n; ++i){
            double sum = b[o[i]];
            for(int j = 0; j < i; ++j){
                sum = sum - coef[o[i]][j] * b[o[j]];
                sum = precisionFinder.precision(sum, significantDigits);
            }
            y[o[i]] = sum;
        }
        //backward substitution
        ans[n - 1] = y[o[n - 1]] / coef[o[n - 1]][n - 1];
        ans[n - 1] = precisionFinder.precision(ans[n - 1], significantDigits);
        for(int i = n - 2; i >= 0; --i){
            double sum = 0;
            for(int j = i + 1; j < n; ++j){
                sum = sum + coef[o[i]][j] * ans[j];
                sum = precisionFinder.precision(sum, significantDigits);
            }
            ans[i] = (y[o[i]] - sum) / coef[o[i]][i];
            ans[i] = precisionFinder.precision(ans[i], significantDigits);
        }
    }

    public double[] Solve(){
    	significantDigits();
        Decompose();
        if(!error) Substitute();
        return ans;
    }

    

}
