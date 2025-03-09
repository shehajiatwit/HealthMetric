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

    /*
     * Returns false if the values of each health metric are nonsensical.
     * Returns true if the values are realistic.
     */
    public boolean validateInput() {
        if (heartRate < 20 || heartRate > 200) { // Represents unrealistic range for heart rate
            return false;
        }
        if (systolic < 60 || systolic > 200) { // Represents unrealistic range for systolic bp
            return false;
        }
        if (dystolic < 30 || dystolic > 150) { // Represents unrealistic range for dystolic bp
            return false;
        }
        if (glucoseLevel < 30 || glucoseLevel > 300) { // Represents unrealistic range for fasting blood glucose levels
            return false;
        }
        return true;
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