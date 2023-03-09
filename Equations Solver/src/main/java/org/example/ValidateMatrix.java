package org.example;

public class ValidateMatrix {

	String[][] mat;
	boolean isIterative;
	int n;
	
	public ValidateMatrix(String[][] validateMat, boolean isIterative) {
		this.mat = validateMat;
		this.n = mat.length;
		this.isIterative = isIterative;
	}
	
	public String[][] validate() {
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < n + 1; ++j) {
				//check for blank entries
				if(mat[i][j].isBlank()) {
					mat[i][j] = "0";
					continue;
				}
				//check for non-numeric entries
				try {
					double temp = Double.parseDouble(mat[i][j]);
				} catch (Exception e) {
					mat[0][0] = "Error";
					return mat;
				}
			}
		}
		
		if(isIterative) {
			for(int i=0 ; i<n ; i++) {
				if(Double.parseDouble(mat[i][i]) == 0) {
					mat[0][0] = "Error1";
					return mat;
					}
			}
		}
			
		else {
			precisionFinder precisionFinder = new precisionFinder(); 
			for(int k = 0; k < n - 1; k++) {
	            for(int i = k + 1; i < n; i++) {
	                double multiplier = Double.parseDouble(mat[i][k]) / Double.parseDouble(mat[k][k]);
	                multiplier = precisionFinder.precision(multiplier, 10);
	                for(int j = k; j < n + 1; j++) {
	                	mat[i][j] = Double.toString(Double.parseDouble(mat[i][j]) - multiplier * Double.parseDouble(mat[k][j]));
	                	mat[i][j] = Double.toString(precisionFinder.precision(Double.parseDouble(mat[i][j]), 10));
	                }
	            }
	        }
			//
			for(int i=0 ; i<n ; i++) {
				for(int j=0 ; j<n+1 ; j++) {
					System.out.print(mat[i][j]+" ");
				}
				System.out.println();
			}
			//
			for(int i=0 ; i<n ; i++) {
				boolean isZero = false;
				boolean BsNotZero = false;
				for(int j=0 ; j<n ; j++) {
					if(Double.parseDouble(mat[i][j]) == 0){
						isZero = true;
					}else {
						isZero = false;
						break;
					}
				}
				if(Double.parseDouble(mat[i][n]) != 0) {
					BsNotZero = true;
				}
				if(isZero && BsNotZero) {
					mat[0][0] = "Error1";
					return mat;
				}
			}
		}
		return mat;
	
	}
	

	
}
