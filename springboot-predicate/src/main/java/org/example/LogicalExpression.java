
package org.example;


import org.example.exception.PredicateException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 逻辑表达式（与、或、非）的基类
 * 短路操作，一旦表达式的真值已经确定，评估就会停止
 */

public abstract class LogicalExpression extends Expression {
    protected List<Expression> _subExpressions = new ArrayList<Expression>();

    /**
     * 通过迭代收集逻辑表达式的子表达式
     *
     * @param expr 过解析查询谓词提取的子表达式列表，第一个元素应为逻辑表达式的操作符名称。
     */
    public LogicalExpression(final List expr) throws PredicateException {
        Iterator iter = expr.listIterator();
        String op = (String) iter.next();

        // Collect sub-expressions
        while (iter.hasNext()) {
            Object object = iter.next();
            if (object instanceof List) {
                _subExpressions.add(createExpression((List) object));
            } else {
                throw new PredicateException("Operands of " + op + " must be Lists");
            }
        }
    }

}

