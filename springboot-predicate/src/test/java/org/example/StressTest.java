package org.example;

import org.example.exception.PredicateException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/***
 * @Title StressTest
 * @Description:
 * @author: hang.hu
 * @date: 2024/12/24 18:01
 * @version: 1.0.0
 **/
public class StressTest {

    private static String predicate;

    private static Map<String, Object> queryMap;

    /**
     * 1W 1111ms
     * 10W 9240ms
     */
    @Test
    public void test() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            try {
                UndefinedExpression expression = (UndefinedExpression) Expression.createExpression(predicate);
                for (Expression exp : expression.getAllExpressions()){
                    exp.evaluate(queryMap);
                }
//                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time: " + (end - start) + "ms");
    }

    /**
     * 1W 295ms
     * 10W 2478ms
     * @throws PredicateException
     */
    @Test
    public void test2() throws PredicateException {
        long start = System.currentTimeMillis();
        UndefinedExpression expression = (UndefinedExpression) Expression.createExpression(predicate);
//        for (int i = 0; i < 100000; i++) {
            for (Expression exp : expression.getAllExpressions()){
                boolean res = exp.evaluate(queryMap);
                System.out.println(res);
            }
//        }
        long end = System.currentTimeMillis();
        System.out.println("Total time: " + (end - start) + "ms");
    }

    @BeforeAll
    public static void setUp() throws Exception {
        queryMap = new HashMap<>();
        queryMap.put("packageName", "com.example");
        queryMap.put("className", "MyClass");
        queryMap.put("version", "2.1.0");
        queryMap.put("status", "active");
        queryMap.put("enabled", "false");
        queryMap.put("message", "This is a premium service message");
        queryMap.put("timestamp", "2024-12-24T09:00:00Z");
        queryMap.put("name", "Alice");
        queryMap.put("age", 28);
        queryMap.put("address", "1234 Elm Street");
        queryMap.put("town", "San Francisco");
        queryMap.put("phone", "9876543210");
        queryMap.put("price", 89.99);
        queryMap.put("country", "US");
        queryMap.put("tags", Arrays.asList("premium", "new"));
        queryMap.put("location", "Beijing");
        queryMap.put("email", "alice@example.com");
        queryMap.put("height", 170);
        queryMap.put("weight", 55);
        queryMap.put("gender", "female");
        queryMap.put("subscription", "premium");
        queryMap.put("score", 95);
        queryMap.put("hobbies", Arrays.asList("reading", "travelling", "coding"));
        queryMap.put("birthDate", "1995-12-25");
        queryMap.put("city", "Shanghai");
        queryMap.put("isActive", "true");
        queryMap.put("accountType", "VIP");
        queryMap.put("loginCount", 20);
        queryMap.put("lastLogin", "2024-11-15");
        queryMap.put("paymentStatus", "completed");
        queryMap.put("language", "English");
        queryMap.put("phone2", "9876543211");
        queryMap.put("countryCode", "US");
        queryMap.put("postalCode", "94101");
        queryMap.put("company", "Tech Corp");
        queryMap.put("position", "Software Engineer");
        queryMap.put("department", "Engineering");
        queryMap.put("manager", "John Doe");
        queryMap.put("project", "Project X");
        queryMap.put("team", "Development");
        queryMap.put("skills", Arrays.asList("Java", "Spring", "AWS"));
        queryMap.put("education", "Bachelor's Degree");
        queryMap.put("degree", "Computer Science");
        queryMap.put("university", "Stanford University");
        queryMap.put("gpa", 3.9);
        queryMap.put("maritalStatus", "single");
        queryMap.put("children", 0);
        queryMap.put("pets", "dog");
        queryMap.put("vehicle", "Tesla");
        queryMap.put("favoriteFood", "Pizza");
        queryMap.put("favoriteColor", "Blue");
        queryMap.put("favoriteMovie", "Inception");
        queryMap.put("favoriteBook", "1984");
        queryMap.put("travelHistory", Arrays.asList("France", "Japan", "Australia"));
        queryMap.put("socialMedia", Arrays.asList("Facebook", "Twitter"));
        queryMap.put("website", "https://aliceportfolio.com");
        queryMap.put("socialSecurity", "123-45-6789");
        queryMap.put("creditScore", 750);
        queryMap.put("bankAccountNumber", "9876543210001234");
        queryMap.put("bankName", "Bank of America");
        queryMap.put("accountBalance", 5000.75);
        queryMap.put("creditLimit", 10000);
        queryMap.put("loanStatus", "approved");
        queryMap.put("investment", "stocks");
        queryMap.put("stockPortfolio", Arrays.asList("AAPL", "GOOG", "AMZN"));
        queryMap.put("realEstate", "own");
        queryMap.put("propertyValue", 200000);
        queryMap.put("insurance", "health");
        queryMap.put("insuranceNumber", "HI123456");
        queryMap.put("healthStatus", "good");
        queryMap.put("allergies", "none");
        queryMap.put("medications", "none");
        queryMap.put("doctor", "Dr. Smith");
        queryMap.put("hospital", "City Hospital");
        queryMap.put("emergencyContact", "Bob");
        queryMap.put("emergencyPhone", "9876543212");
        queryMap.put("bloodType", "O+");
        queryMap.put("lastCheckup", "2024-08-15");
        queryMap.put("physicalActivity", "running");
        queryMap.put("sleepHours", 8);
        queryMap.put("diet", "vegetarian");
        queryMap.put("smokingStatus", "non-smoker");
        queryMap.put("alcoholConsumption", "moderate");
        queryMap.put("exerciseFrequency", "3 times a week");
        queryMap.put("mentalHealth", "stable");
        queryMap.put("stressLevel", "low");
        queryMap.put("happinessIndex", 8.5);
        queryMap.put("volunteerWork", "Red Cross");
        queryMap.put("charityDonations", 500);
        queryMap.put("timeZone", "PST");
        queryMap.put("preferredContactMethod", "email");
        queryMap.put("contactPreference", "morning");
        queryMap.put("availability", "weekdays");
        queryMap.put("workFromHome", true);
        queryMap.put("companyWebsite", "https://techcorp.com");
        queryMap.put("employeeSince", "2020-06-01");
        queryMap.put("retirementPlan", "401k");
        queryMap.put("stockOptions", 100);
        queryMap.put("bonusEligibility", true);
        queryMap.put("sickLeave", 10);
        queryMap.put("vacationDays", 20);
        queryMap.put("workLocation", "San Francisco");
        queryMap.put("remoteLocation", "Los Angeles");
        queryMap.put("teamSize", 10);
        queryMap.put("projectsCompleted", 15);
        queryMap.put("meetingsAttended", 50);
        queryMap.put("presentationsGiven", 5);
        queryMap.put("teamBudget", 500000);
        queryMap.put("performanceRating", 4.8);
        queryMap.put("feedbackReceived", "positive");
        queryMap.put("peerReviews", Arrays.asList("excellent", "hardworking"));
        queryMap.put("personalGoals", Arrays.asList("promoted to Senior", "learn Python"));
        queryMap.put("careerGoals", Arrays.asList("CTO", "own a startup"));
        queryMap.put("skillsImprovement", Arrays.asList("machine learning", "cloud computing"));
        queryMap.put("workAnniversary", "2024-06-01");


        predicate = "['undefined'," +
                "['eq', 'packageName', ['quote', 'com.example']]," +
                "['eq', 'className', ['quote', 'MyClass']]," +
                "['ge', 'version', ['quote', '2.0.0']], " +
                "['ne', 'status', ['quote', 'suspended']], " +
                "['false', 'enabled'], " +
                "['re_match', 'message', ['quote', '.*service.*']]," +
                "['gt', 'age', 25]," +
                "['exists', 'timestamp']," +
                "['lt', 'price', 100.00]," +
//                "['in', 'country', ['quote', 'US', 'CA', 'UK']]," +
                "['eq', 'tags', ['quote', 'premium']]," +
                "['lt', 'height', 180]," +
                "['ge', 'weight', 50]," +
                "['eq', 'gender', ['quote', 'female']]," +
                "['eq', 'subscription', ['quote', 'premium']]," +
//                "['between', 'score', 80, 100]," +
//                "['in', 'hobbies', ['quote', 'reading', 'travelling', 'coding']]," +
                "['eq', 'birthDate', ['quote', '1995-12-25']]," +
                "['exists', 'email'], " +
                " ['eq', 'phone', ['quote', '1234567890']]," +
//                "['in', 'languages', ['quote', 'English', 'Chinese']]," +
                "['ne', 'isActive', ['quote', 'false']]," +
                "['eq', 'accountType', ['quote', 'VIP']]," +
                "['ge', 'loginCount', 10]," +
                "['le', 'lastLogin', ['quote', '2024-12-01']]," +
                "['eq', 'address', ['quote', 'Street']]," +
                "]";

    }
}
