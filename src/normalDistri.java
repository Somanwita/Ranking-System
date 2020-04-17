import java.util.ArrayList;

public class normalDistri {
//private double mean;
//private double sd;
//private double normalVariable;

public normalDistri() {
//	super();
//	this.mean = mean;
//	this.sd = sd;
//	this.normalVariable = normalVariable;
}

public double calculateND(double normalVari, double mean, double sd) {
	double value = 0;
	double temp = Math.pow((normalVari-mean), 2);
	double total = temp/(2*sd*sd);
	
	value = (1/(sd* Math.sqrt(2*3.14159)))* (Math.pow(2.71828, total));
	return value;
}

public double standardDeviation(double array[], double mean) {
	double sd = 0;
	double sqrDiff = 0;
	for(int i = 0; i<array.length;i++) {
	sqrDiff += (array[i] - mean)* (array[i] -mean);	
	}
	double variance = sqrDiff/(array.length+1);
	sd = Math.sqrt(variance);
	return sd;
}
}
