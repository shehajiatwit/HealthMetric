/*
 * Author: Abia Hasan
 * This program stores contains and stores health metric values. It serves for other classes to store health metric values and access them. 
 */

public class HealthMetric {
	
	private float metricValue;

    public HealthMetric(float value, String type) {
        String metric = type.toLowerCase();
        if (metric.equals("heartrate")) {
        	metricValue = value;
        } else if (metric.equals("systolicbp")) {
        	metricValue = value;	
        } else if (metric.equals("dystolicbp")) {
        	metricValue = value;
        } else if (metric.equals("glucose")) {
        	metricValue = value;
        }

    }

    public float getHeartRate() {
       return metricValue;
    }

    public float getSystolic() {
    	return metricValue;
    }

    public float getDystolic() {
    	return metricValue;
    }

    public float getGlucose() {
    	return metricValue;
    }


}