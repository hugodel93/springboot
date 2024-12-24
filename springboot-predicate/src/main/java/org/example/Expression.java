
package org.example;


import org.example.exception.PredicateException;
import org.example.impl.LogicalAnd;
import org.example.impl.LogicalNot;
import org.example.impl.LogicalOr;
import org.example.util.AddressParser;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 表达式的基类
 */
public abstract class Expression {

    public static Expression createExpression(final String predicate) throws PredicateException {
        if (predicate.charAt(0) == '[') {
            Map<Object, Object> predicateMap = new AddressParser("{'_where': " + predicate + "}").map();
            List predicateList = (List) predicateMap.get("_where");
            return createExpression(predicateList);
        } else {
            throw new PredicateException("Invalid predicate format");
        }

    }

    /**
     * 工厂方法，根据从表达式列表中提取的运算符名称创建具体的表达式实例。
     * 此方法将根据运算符名称创建一个逻辑表达式（LogicalExpression），
     * 如果运算符是 "and"、"or" 或 "not"；否则，它将创建一个布尔表达式（BooleanExpression）
     *
     * @param expr 通过解析查询谓词提取的表达式列表
     */
    public static Expression createExpression(final List expr) throws PredicateException {
        Iterator iter = expr.listIterator();
        if (!iter.hasNext()) {
            throw new PredicateException("Missing operator in predicate expression");
        }

        String op = (String) iter.next();
        switch (op) {
            case "not":
                return new LogicalNot(expr);
            case "and":
                return new LogicalAnd(expr);
            case "or":
                return new LogicalOr(expr);
            case "undefined":
                return new UndefinedExpression(expr);

        }
        return BooleanExpression.createExpression(expr);
    }

    /**
     * 评估
     *
     * @param data 用于评估表达式的对象
     * @return 匹配，则返回 true；否则返回 false
     */
    public abstract boolean evaluate(final Map<String, Object> data);
}

