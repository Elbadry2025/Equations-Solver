package org.example;

public class Cholesky {

    private int n, significantDigits;
    private double[][] coef;
    private double[][] L;
    private double[][] U;
    private double[] ans;
    private double[] b;
    precisionFinder precisionFinder = new precisionFinder();

    public Cholesky(double[][] coef,double[] b, int digits) {
        this.coef = coef;
        this.b = b;
        this.n = coef.length;
        this.significantDigits = digits;
        this.U = new double[n][n];
        this.L = new double[n][n];
        this.ans = new double[n];
    }


    private void significantDigits() {
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < n; ++j) {
				coef[i][j] = precisionFinder.precision(coef[i][j], significantDigits);
			}
			b[i] = precisionFinder.precision(b[i], significantDigits);
		}
	}
    
    //check symmetric
    public static boolean CS(double[][] Arr, int n){
        double[][] Transpose=new double[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                Transpose[i][j]=Arr[j][i];
            }
        }
        boolean sym=true;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(Transpose[i][j]!=Arr[j][i]){
                    sym=false;
                }
                if(sym==false)break;
            }
        }
        return sym;
    }

    private void MainMethod() {
        for(int j=0;j<n;j++){
            for(int i=j;i<n;i++){
                if(i==j){
                    double sum=0;
                    for(int k=0;k<j;k++){
                        sum+=Math.pow(L[i][k],2);
                        sum = precisionFinder.precision(sum, significantDigits);
                    }
                    L[i][j]=Math.sqrt(coef[i][j]-sum);
                    L[i][j] = precisionFinder.precision(L[i][j], significantDigits);
                }else{
                    double sum=0;
                    for(int k=0;k<j;k++){
                        sum+=(L[i][k]*L[j][k]);
                        sum = precisionFinder.precision(sum, significantDigits);
                    }
                    L[i][j]=(coef[i][j]-sum)/L[j][j];
                    L[i][j] = precisionFinder.precision(L[i][j], significantDigits);
                }
            }
        }

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                U[i][j]=L[j][i];
                U[i][j] = precisionFinder.precision(U[i][j], significantDigits);
            }
        }
    }

    private void Substitution() {
        double[] y = new double[n];
        //forward substitute
        for(int i=0;i<n;i++){
            double sum=0;
            for(int j=0;j<i;j++){
            	sum+=(L[i][j]*y[j]);
                sum = precisionFinder.precision(sum, significantDigits);
            }
            y[i]=(1/L[i][i])*(b[i]-sum);
            y[i] = precisionFinder.precision(y[i], significantDigits);
        }
        //backword substitute
        for(int i=n-1;i>=0;i--){
            double sum=0;
            for(int j=i+1;j<n;j++){
                sum+=(U[i][j]*ans[j]);
                sum = precisionFinder.precision(sum, significantDigits);
            }
            ans[i]=(1/U[i][i])*(y[i]-sum);
            ans[i] = precisionFinder.precision(ans[i], significantDigits);
        }
    }

    public double[] Solve() {
        //return ans ={0} if matrix not symmetic
        if(!CS(coef,n))return ans;
        significantDigits();
        MainMethod();
        Substitution();
        return ans;
    }

}