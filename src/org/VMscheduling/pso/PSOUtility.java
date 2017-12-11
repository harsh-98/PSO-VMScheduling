package org.VMscheduling.pso;

/* author: harsh jain */

// just a simple utility class to find a minimum position on a list

public class PSOUtility {
	public static double[] ratio(double x, double xl, double xg){
		double p1 = x/(x + xl + xg);
		double p2 = xl/(x + xl + xg);
		double p3 = xg/(x + xl + xg);
		double a[] = {p1, p2, p3};
		return a;
	}
}
