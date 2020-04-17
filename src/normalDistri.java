import java.util.ArrayList;
import java.util.List;

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
	double total = -temp/(2*sd*sd);
	
	value = (1/(sd* Math.sqrt(2*3.14159)))* (Math.pow(2.71828, total));
	return value;
}
public double mean (List<Double> list) {
	double sum = 0.0;
	double mean = 0.0;
	for(int i = 0; i<list.size();i++) {
		sum += (list.get(i));	
		}
	
	mean = sum/list.size();
	return mean;
}

public double standardDeviation(List<Double> list, double mean) {
	double sd = 0.0;
	double sqrDiff = 0.0;
	for(int i = 0; i<list.size();i++) {
	sqrDiff += ((list.get(i) - mean) * (list.get(i) - mean));	
	}
	double variance = sqrDiff/(list.size());
	sd = Math.sqrt(variance);
	return sd;
}


}
