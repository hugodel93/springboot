package org.example.impl;


import org.example.Expression;
import org.example.LogicalExpression;
import org.example.exception.PredicateException;

import java.util.List;
import java.util.Map;

/**
 * 逻辑与表达式
 */

public final class LogicalAnd extends LogicalExpression {
    /**
     * This method iterates through collecting the sub-expressions of the Logical Expression
     *
     * @param expr the List of sub-expressions extracted by parsing the Query predicate, the first one should be
     *             the Logical Expression's operator name
     */
    public LogicalAnd(final List expr) throws PredicateException {
        super(expr);
    }

    /**
     * 如果有一个匹配失败 则整体失败 后续匹配不执行
     *
     * @return false if any of the sub-expressions is false otherwise returns true
     */
    public boolean evaluate(final Map<String, Object> data) {
        for (Expression e : _subExpressions) {
            if (!e.evaluate(data)) {
                return false;
            }
        }
        return true;
    }
}


