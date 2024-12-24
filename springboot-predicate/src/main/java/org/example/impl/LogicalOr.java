package org.example.impl;

import org.example.Expression;
import org.example.LogicalExpression;
import org.example.exception.PredicateException;

import java.util.List;
import java.util.Map;

/**
 * 逻辑或表达式
 */

public final class LogicalOr extends LogicalExpression {
    /**
     * This method iterates through collecting the sub-expressions of the Logical Expression
     *
     * @param expr the List of sub-expressions extracted by parsing the Query predicate, the first one should be
     *             the Logical Expression's operator name
     */
    public LogicalOr(final List expr) throws PredicateException {
        super(expr);
    }

    /**
     * 如果有一个匹配成功 则整体成功 后续不匹配
     *
     * @return true if any of the sub-expressions is true otherwise returns false
     */
    public boolean evaluate(final Map<String, Object> data) {
        for (Expression e : _subExpressions) {
            if (e.evaluate(data)) {
                return true;
            }
        }
        return false;
    }
}


