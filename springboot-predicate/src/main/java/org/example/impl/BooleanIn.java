package org.example.impl;

import org.example.BooleanExpression;
import org.example.Expression;
import org.example.exception.PredicateException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/***
 * @Title BoolenIn
 * @Description:
 * @author: hang.hu
 * @date: 2024/12/24 18:00
 * @version: 1.0.0
 **/
public class BooleanIn extends BooleanExpression {

    private List<String> inList;

    public BooleanIn() {
    }

    public BooleanIn(List expr) throws PredicateException {
        super(2, expr);
    }

    @Override
    public Expression create(List expr) throws PredicateException {
        return new BooleanIn(expr);
    }

    @Override
    public boolean evaluate(Map<String, Object> data) {
        populateOperands(data);
        if (_operands[0] == null || _operands[1] == null) {
            return false;
        }
        if (inList == null) {
            String[] ins = _operands[1].replaceAll("[\\[\\] ]", "").split(",");
            inList = Arrays.asList(ins);
        }
        return inList.contains(_operands[0]);
    }
}
