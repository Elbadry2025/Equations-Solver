package org.example;


public class Solve {
	
	private String method = "Gauss Elimination";
	private String LUType = "Dolittle";
	private double coef[][], specialCoef[][];
	private double b[];
	double ans[];
	int significantDigits;
	double guess[];
	int it;
	double er;
	boolean enableScaling;
	int itDone;
	
	public Solve(String method, String LUType, double[][] coef, double[][] specialCoef, double[] b, int digits, double guess[], int it, double er,boolean enableScaling) {
		this.method = method;
		this.LUType = LUType;
		this.coef = coef;
		this.specialCoef = specialCoef;
		this.b = b;
		this.significantDigits = digits;
		this.guess = guess;
		this.it = it;
		this.er = er;
		this.enableScaling = enableScaling;
	}
	
	public double[] chooseMethod() {
		switch(method) {
			case "Gauss Elimination" : {
				GaussElimination obj = new GaussElimination(coef, enableScaling, significantDigits);
				ans = obj.solve();
				break;
			}
			case "LU Decomposition" : {
				switch (LUType) {
					case "Dolittle" : {
						DoolittleLU obj = new DoolittleLU(specialCoef, b, enableScaling, significantDigits);
						ans = obj.Solve();
						break;
					}
					case "Crout" : {
						CroutDecomposition obj = new CroutDecomposition(coef, enableScaling, significantDigits);
						ans = obj.Solve();
						break;
					}
					case "Cholesky" : {
						Cholesky obj = new Cholesky(specialCoef, b, significantDigits);
						ans = obj.Solve();
						break;
					}
				}
				break;
			}
			case "Gauss Jordan" : {
				GaussJordan obj = new GaussJordan(coef, enableScaling, significantDigits);
				ans = obj.solve();
				break;
			}
			case "Gauss Seidel" : {
				GaussSeidel obj = new GaussSeidel(coef, it, significantDigits,guess, er);
				ans = obj.solve();
				itDone = obj.itDone();
				break;
			}
			case "Jacobi Iteration" : {
				Jacobi obj = new Jacobi(coef, it, significantDigits,guess, er);
				ans = obj.solve();
				itDone = obj.itDone();
				break;
			}
		}
		return ans;
	}
	public int Iterations() {
		return itDone;
	}

}