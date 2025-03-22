package SensitiveLoginandData;
import java.time.LocalTime;

/*
 * Author: Abia Hasan
 */

public class HealthMetric {
    private float heartRate;
    private float systolic;
    private float dystolic;
    private float glucoseLevel;

    public HealthMetric(float value, String type) {
        String metric = type.toLowerCase();
        if (metric.equals("heartrate")) {
            heartRate = value;
            systolic = 0;
            dystolic = 0;
            glucoseLevel = 0;
        } else if (metric.equals("systolicbp")) {
            systolic = value;
            heartRate = 0;
            dystolic = 0;
            glucoseLevel = 0;
        } else if (metric.equals("dystolicbp")) {
            dystolic = value;
            heartRate = 0;
            systolic = 0;
            glucoseLevel = 0;
        } else if (metric.equals("glucose")) {
            glucoseLevel = value;
            heartRate = 0;
            systolic = 0;
            dystolic = 0;
        }

    }

    public void setTime() {
       LocalTime timeNow = LocalTime.now();
       System.out.println(timeNow);
    }
    
    public void setHeartRate(float newHeartRate) {
        this.heartRate = newHeartRate;
    }

    public void setBloodPressure(float newSystolic, float newDystolic) {
        this.systolic = newSystolic;
        this.dystolic = newDystolic;
    }

    public void setGlucoseLevel(float newGlucose) {
        this.glucoseLevel = newGlucose;
    }

    public float getHeartRate() {
       return this.heartRate;
    }

    public float getSystolic() {
        return this.systolic;
    }

    public float getDystolic() {
        return this.dystolic;
    }

    public float getGlucose() {
        return this.glucoseLevel;
    }


}