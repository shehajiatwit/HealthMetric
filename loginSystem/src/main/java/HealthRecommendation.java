/*
 * Author: Abia Hasan
 * This program looks at one health metric at a time, and compares the metric to the normal/healthy range.
 * Based on how much the health metric value deviates from the normal range, a rating is provied.
 */

public class HealthRecommendation {
	private static final float[] NORMAL_SYSTOLIC = {90f,120f}; // represents the normal systolic range in mm Hg
    private static final float[] NORMAL_DYSTOLIC = {60f,80f}; // represents the normal dystolic range in mm Hg
    private static final float[] NORMAL_HEART_RATE = {60f,100f}; // in beats per minute
    private static final float[] NORMAL_GLUCOSE_LEVELS = {70f,99f}; // represents the fasting blood glucose levels in mg/dL

    /*
     * Returns a rating from 1-5 of user's blood pressure.  
     * 1 = unhealthy (should seek immediate medical attention)
     * 5 = healthy   
     */
    public int compareBloodPressure(float s, float d) {
    	int rating = 5;
        if (s > NORMAL_SYSTOLIC[1] || d > NORMAL_DYSTOLIC[1]) { // Systolic or dystolic blood pressure is higher than normal
           if (s >= 180 || d >= 120) {
                rating = 1;
           }
           else if (s >= 140 || d >= 90) {
                rating = 2;
           }
           else if (s >= 130 || d >= 80) {
                rating = 3;
           }
           else if (s >= 120 && d < 80) {
                rating = 4;
           }
        } 
        else if (s < NORMAL_SYSTOLIC[0] && d < NORMAL_DYSTOLIC[0]) { // Systolic and dysolic blood pressure is lower than normal
            if (s <= 70 || d <= 40) {
            	rating = 1;
            }
            else {
            	rating = 4;
            }
        }
        return rating;
    }

    /*
     * Returns a rating from 1-5 of the user's resting heart rate.  
     * 1 = unhealthy (should seek immediate medical attention)
     * 5 = healthy
     */
    public int compareHeartRate(float hr) {
    	int rating = 5;
        if (hr > NORMAL_HEART_RATE[1]) {
        	if (hr > 140) {
        		rating = 1;
        	}
        	else if (hr > 130) {
        		rating = 2;
        	}
        	else if (hr > 120) {
        		rating = 3;
        	}
        	else if (hr > 110) {
        		rating = 4;
        	}
        } else if (hr < NORMAL_HEART_RATE[0]) {
        	if (hr < 20) {
        		rating = 1;
        	} else if (hr < 30) {
        		rating = 2;
        	} else if (hr < 40) {
        		rating = 3;
        	} else if (hr < 50) {
        		rating = 4;
        	}
        }
        return rating;
    }
    
    /*
     * Returns a rating from 1-5 of user's fasting glucose levels. 
     * 1 = unhealthy (should seek immediate medical attention)
     * 5 = healthy 
     */
    public int compareGlucose(float g) {
    	int rating = 5;
    	if (g < NORMAL_GLUCOSE_LEVELS[0]) { // Glucose levels are lower than normal
    	    if (g < 35) {
    	        rating = 1;
    	    }
    	    else if (g < 40) {
    	        rating = 2;
    	    }
    	    else if (g < 50) {
    	        rating = 3;
    	   } 
    	   else if (g < 70) {
    	        rating = 4;
    	  }
         }
      else if (g > NORMAL_GLUCOSE_LEVELS[1]) { // Glucose levels are higher than normal
    	   if (g >= 250) {
    	      rating = 1;
    	   }
    	   else if (g >= 200) {
    	        rating = 2;
    	        	}
    	   else if (g >= 150) {
    	        rating = 3;
    	        	}
    	   else if (g >= 100) {
    	        rating = 4;
    	   }
    }
        return rating;
    }

}

