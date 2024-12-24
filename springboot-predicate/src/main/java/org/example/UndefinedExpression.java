package org.example;

import org.example.exception.PredicateException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/***
 * @Title StrictBoolenExpression
 * @Description:
 * @author: hang.hu
 * @date: 2024/12/24 17:26
 * @version: 1.0.0
 **/
public class UndefinedExpression extends Expression {

    private final List<Expression> _subExpressions = new ArrayList<>();


    /**
     * 通过迭代收集逻辑表达式的子表达式
     *
     * @param expr 过解析查询谓词提取的子表达式列表，第一个元素应为逻辑表达式的操作符名称。
     */
    public UndefinedExpression(final List expr) throws PredicateException {
        Iterator iter = expr.listIterator();
        Object op = iter.next();

        while (iter.hasNext()) {
            Object object = iter.next();
            if (object instanceof List) {
                _subExpressions.add(createExpression((List) object));
            } else {
                throw new PredicateException("Operands of " + op + " must be Lists");
            }
        }
    }

    public List<Expression> getAllExpressions() {
        return _subExpressions;
    }

    @Override
    public boolean evaluate(Map<String, Object> data) {
        //nothing to do
        return false;
    }
}
