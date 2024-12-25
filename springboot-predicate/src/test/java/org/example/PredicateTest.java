package org.example;

import org.example.exception.PredicateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/***
 * @Title PredicateTest
 * @Description:
 * @author: hang.hu
 * @date: 2024/12/23 16:02
 * @version: 1.0.0
 **/
public class PredicateTest {
    private Map<String, Object> queryMap;

    @BeforeEach
    public void setup() {
        queryMap = new HashMap<>();
        queryMap.put("packageName", "org.example");
        queryMap.put("className", "test");
        queryMap.put("version", "1.0.0.1");
        queryMap.put("status", "active");
        queryMap.put("enabled", "true");
        queryMap.put("message", "This is a broker message");
        queryMap.put("timestamp", "2024-12-23T12:00:00Z");
        queryMap.put("name", "jross");
        queryMap.put("age", 30);
        queryMap.put("address", "China");
        queryMap.put("town", "Wuhan");
        queryMap.put("phone", "123456789");
        queryMap.put("phone2", "123456789");
        queryMap.put("skills", "Java");
    }

    @Test
    public void test() throws PredicateException {
//        String predicate = "['ne','phone','phone2']";
//        String predicate = "['in', 'skills', ['Java', 'Spring', 'AWS']]";
//        String predicate = "['between', 'age', 10 ,29]";
        String predicate = "['re_match', 'message', ['quote', '.*broker.*']]";
        Expression expression = Expression.createExpression(predicate);
        boolean rest = expression.evaluate(queryMap);
        System.out.printf("rest: %s%n", rest);
    }

    @Test
    public void boolenTest() throws PredicateException {
        String predicate = "['ne','phone','phone2']";
        String predicate2 = "['ne','phone2','phone1']";
        String predicate3 = "['ne','phone',['quote', 'phone2']]";
        String predicate4 = "['ne',['quote', 'phone2'],'phone']";
        Expression expression = Expression.createExpression(predicate);
        Expression expression2 = Expression.createExpression(predicate2);
        Expression expression3 = Expression.createExpression(predicate3);
        Expression expression4 = Expression.createExpression(predicate4);
        boolean rest = expression.evaluate(queryMap);
        boolean rest2 = expression2.evaluate(queryMap);
        boolean rest3 = expression3.evaluate(queryMap);
        boolean rest4 = expression4.evaluate(queryMap);
        System.out.printf("rest: %s rest2: %s rest3: %s rest4: %s%n", rest, rest2,rest3,rest4);
    }

    @Test
    public void multBoolenTest() throws PredicateException {
        String predicate = "['undefined'," +
                "['eq','packageName',['quote','org.example']]," +
                "['eq','className',['quote','test']]," +
                "['ge', 'version', ['quote', '1.0.0']], " +
                "['ne', 'status', ['quote', 'inactive']], " +
                "['false', 'enabled'], " +
                "['re_match', 'message', ['quote', '.*broker.*']]," +
                "['gt', 'age', 20]," +
                "['exists', 'timestamp']," +
                "['in', 'skills', ['Java', 'Spring', 'AWS']]," +
                "['and',['exists','age']," +
                "['or',['gt','age',27],['lt','age',12]]" +
                "]" +
                "]";

        UndefinedExpression expression = (UndefinedExpression) Expression.createExpression(predicate);
        for (Map.Entry<String, Expression> entry : expression.getVariables().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().evaluate(queryMap));
        }
//        for (Expression exp : expression.getAllExpressions()){
//            System.out.printf("exp: %s %n", exp.evaluate(queryMap));
//        }
    }

    @Test
    public void logicalTest() throws PredicateException {
        String predicate = "['and'," +
                "['eq','packageName',['quote','org.example']]," +
                "['eq','className',['quote','test']]," +
                "['ge', 'version', ['quote', '1.0.0']], " +
                "['ne', 'status', ['quote', 'inactive']], " +
                "['true', 'enabled'], " +
                "['re_match', 'message', ['quote', '.*broker.*']]," +
                "['gt', 'age', 18]," +
                "['exists', 'timestamp']" +
                "]";
        Expression expression = Expression.createExpression(predicate);
        boolean rest = expression.evaluate(queryMap);
        Assertions.assertTrue(rest);
    }

    @Test
    public void mutiLogicTest() throws PredicateException {
        String predocate = "['or'," +
                "['eq','name',['quote','tross']]," +
                "['and',['eq','name',['quote','jross']]," +
                "['eq','address',['quote','China']]," +
                "['eq',['quote','Wuhan'],'town']" +
                "]" +
                "]";

        String predicate2 = "['and'," +
                "['re_match','name',['quote','[a-zA-Z]?ross']]," +
                "['and',['exists','age']," +
                "['or',['gt','age',27],['lt','age',12]]" +
                "]" +
                "]";
        Expression expression = Expression.createExpression(predocate);
        boolean rest = expression.evaluate(queryMap);
        Assertions.assertTrue(rest);
    }


}
